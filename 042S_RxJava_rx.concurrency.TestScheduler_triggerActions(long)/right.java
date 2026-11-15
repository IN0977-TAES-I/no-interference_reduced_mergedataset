class TestScheduler {
    Queue<TimedAction<?>> queue;
    long time;

    @SuppressWarnings("unchecked")
    private void triggerActions(long targetTimeInNanos) {
        while (!queue.isEmpty()) {
            TimedAction<?> current = queue.peek();
            if (current.time > targetTimeInNanos) {
                time = targetTimeInNanos;
                break;
            }
            time = current.time;
            queue.remove();

            if (!current.isCancelled.get()) {
                ((Func2<Scheduler, Object, Subscription>) current.action).call(current.scheduler, current.state);
            }
        }
    }
}
