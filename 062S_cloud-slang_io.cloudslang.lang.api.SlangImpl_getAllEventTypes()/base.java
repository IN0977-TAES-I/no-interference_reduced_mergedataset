class SlangImpl {
    private Set<String> getAllEventTypes() {
        Set<String> eventTypes = new HashSet<>();
        eventTypes.add(EventConstants.SCORE_FINISHED_EVENT);
        eventTypes.add(EventConstants.SCORE_BRANCH_FAILURE_EVENT);
        eventTypes.add(EventConstants.SCORE_FINISHED_BRANCH_EVENT);
        eventTypes.add(EventConstants.SCORE_NO_WORKER_FAILURE_EVENT);
        eventTypes.add(EventConstants.SCORE_PAUSED_EVENT);
        eventTypes.add(EventConstants.SCORE_ERROR_EVENT);
        eventTypes.add(EventConstants.SCORE_FAILURE_EVENT);
        eventTypes.add(ScoreLangConstants.SLANG_EXECUTION_EXCEPTION);
        eventTypes.add(ScoreLangConstants.EVENT_ACTION_START);
        eventTypes.add(ScoreLangConstants.EVENT_ACTION_END);
        eventTypes.add(ScoreLangConstants.EVENT_ACTION_ERROR);
        eventTypes.add(ScoreLangConstants.EVENT_INPUT_START);
        eventTypes.add(ScoreLangConstants.EVENT_INPUT_END);
        eventTypes.add(ScoreLangConstants.EVENT_OUTPUT_START);
        eventTypes.add(ScoreLangConstants.EVENT_OUTPUT_END);
        eventTypes.add(ScoreLangConstants.EVENT_BRANCH_START);
        eventTypes.add(ScoreLangConstants.EVENT_BRANCH_END);
        eventTypes.add(ScoreLangConstants.EVENT_ASYNC_LOOP_EXPRESSION_START);
        eventTypes.add(ScoreLangConstants.EVENT_ASYNC_LOOP_EXPRESSION_END);
        eventTypes.add(ScoreLangConstants.EVENT_ASYNC_LOOP_OUTPUT_START);
        eventTypes.add(ScoreLangConstants.EVENT_ASYNC_LOOP_OUTPUT_END);
        eventTypes.add(ScoreLangConstants.EVENT_EXECUTION_FINISHED);
        return eventTypes;
    }
}
