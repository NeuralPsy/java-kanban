class Subtask extends Task{

    protected int epicId;

    public Subtask(String taskName, int taskId, String description) {
        super(taskName, taskId, description);
    }

    public Subtask(String taskName, int taskId, String description, int epicId) {
        super(taskName, taskId, description);
        this.epicId = epicId;
    }
}
