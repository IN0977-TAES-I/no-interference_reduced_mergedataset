// class name : rx.concurrency.TestScheduler
// method name: triggerActions(long)
// side       : left
// region     : lines 95-100 (affected lines: [95, 96, 97, 100])
class TestScheduler {
    void triggerActions_long_() {
        return schedule(state, action, 0, TimeUnit.MILLISECONDS);
    }

    @Override
    public <T> Subscription schedule(T state, Func2<? super Scheduler, ? super T, ? extends Subscription> action, long delayTime, TimeUnit unit) {
        queue.add(new TimedAction<T>(this, time + unit.toNanos(delayTime), action, state));
    }
}
