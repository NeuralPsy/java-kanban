class Subtask extends Task{
    protected int epicId;

    public Subtask(String taskName, String status, int taskId, String description) {
        super(taskName, status, taskId, description);
    }
}
