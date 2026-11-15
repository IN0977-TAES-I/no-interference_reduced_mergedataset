class OperatorMulticast {
    Observable<? extends T> source;
    Object guard;
    Func0<? extends Subject<? super T, ? extends R>> subjectFactory;
    AtomicReference<Subject<? super T, ? extends R>> connectedSubject;
    List<Subscriber<? super R>> waitingForConnect;
    Subscriber<T> subscription;
    Subscription guardedSubscription;

    @Override
    public void connect(Action1<? super Subscription> connection) {

        synchronized (guard) {
            if (subscription != null) {
                connection.call(guardedSubscription);
                return;
            } else {
                final Subject<? super T, ? extends R> subject = subjectFactory.call();
                subscription = Subscribers.from(subject);
                final AtomicReference<Subscription> gs = new AtomicReference<Subscription>();
                gs.set(Subscriptions.create(new Action0() {
                    @Override
                    public void call() {
                        Subscription s;
                        synchronized (guard) {
                            if ( guardedSubscription == gs.get()) {
                                s = subscription;
                                subscription = null;
                                guardedSubscription = null;
                                connectedSubject.set(null);
                            } else
                                return;
                        }
                        if (s != null) {
                            s.unsubscribe();
                        }
                    }
                }));
                guardedSubscription = gs.get();

                for(final Subscriber<? super R> s : waitingForConnect) {
                    subject.unsafeSubscribe(new Subscriber<R>(s) {
                        @Override
                        public void onNext(R t) {
                            s.onNext(t);
                        }
                        @Override
                        public void onError(Throwable e) {
                            s.onError(e);
                        }
                        @Override
                        public void onCompleted() {
                            s.onCompleted();
                        }
                    });
                }
                waitingForConnect.clear();
                connectedSubject.set(subject);
            }

        }

        connection.call(guardedSubscription);

        Subscriber<T> sub;
        synchronized (guard) {
            sub = subscription;
        }
        if (sub != null)
            source.subscribe(sub);
    }
}
