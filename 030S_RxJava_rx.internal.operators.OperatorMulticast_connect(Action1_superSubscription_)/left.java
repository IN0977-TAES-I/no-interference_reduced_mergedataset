// class name : rx.internal.operators.OperatorMulticast
// method name: connect(Action1<?superSubscription>)
// side       : left
// region     : lines 94-131 (affected lines: [94, 117, 118, 119, 120, 121, 122, 124, 125, 128, 129, 131])
class OperatorMulticast {
    void connect_Action1__superSubscription__() {
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
                
                // register any subscribers that are waiting with this new subject
                for(Subscriber<? super R> s : waitingForConnect) {
                    subject.unsafeSubscribe(s);
                }
                // clear the waiting list as any new ones that come in after leaving this synchronized block will go direct to the Subject
                waitingForConnect.clear();
                // record the Subject so OnSubscribe can see it
                connectedSubject.set(subject);
            }
            
        }

        // in the lock above we determined we should subscribe, do it now outside the lock
        // register a subscription that will shut this down
        connection.call(guardedSubscription);

    }
}
