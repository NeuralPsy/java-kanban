package taskmanager.TaskTypes;

public class Subtask extends Task {

    protected final int epicId;

    public Subtask(String taskName, int taskId, String description, int epicId) {
        super(taskName, taskId, description);
        this.epicId = epicId;
        this.status = TaskStatus.NEW;
        this.type = TaskTypes.SUBTASK;
    }

    public Subtask(Task task, int epicId) {
        super(task.getTaskName(), task.getTaskId(), task.getDescription());
        this.epicId = epicId;
        this.status = TaskStatus.NEW;
        this.type = TaskTypes.SUBTASK;
    }

    public Subtask(Subtask subtask) {
        super(subtask.getTaskName(), subtask.getTaskId(), subtask.getDescription());
        this.epicId = subtask.getEpicIdOfSubtask();
        this.status = TaskStatus.NEW;
        this.type = TaskTypes.SUBTASK;
    }

    public int getEpicIdOfSubtask(){
        return epicId;
    }


    @Override
    public String toString(){
        return "Название подзадачи: " + taskName+"\n"
                +"ID подзадачи: " + taskId+"\n"
                +"Статус подзадачи: " + status+"\n"
                +"Описание: " + description+"\n\n";
    }

    public int getEpicId() {
        return this.epicId;
    }
}
