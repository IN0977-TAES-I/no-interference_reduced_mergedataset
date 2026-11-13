// class name : rx.concurrency.TestScheduler
// method name: triggerActions(long)
// side       : merge
// region     : lines 95-100 (affected lines: [95, 96, 97, 100])
class TestScheduler {
    void triggerActions_long_() {
            if (!current.isCancelled.get()) {
                // because the queue can have wildcards we have to ignore the type T for the state
                ((Func2<Scheduler, Object, Subscription>) current.action).call(current.scheduler, current.state);
            }
        }
        time = targetTimeInNanos;
    }
}
