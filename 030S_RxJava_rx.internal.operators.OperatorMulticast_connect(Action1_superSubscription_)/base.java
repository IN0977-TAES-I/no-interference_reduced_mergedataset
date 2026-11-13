// class name : rx.internal.operators.OperatorMulticast
// method name: connect(Action1<?superSubscription>)
// side       : base
// region     : lines 94-131 (affected lines: [94, 117, 118, 119, 120, 121, 122, 124, 125, 128, 129, 131])
class OperatorMulticast {
    void connect_Action1__superSubscription__() {
                    @Override
                    public void onCompleted() {
                        subject.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        subject.onError(e);
                    }

                    @Override
                    public void onNext(T args) {
                        subject.onNext(args);
                    }
                };
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
                
                // register any subscribers that are waiting with this new subject
                for(Subscriber<? super R> s : waitingForConnect) {
    }
}
