package taskmanager.TaskTypes;

public class Subtask extends Task {

    protected final int epicId;

    public Subtask(String taskName, int taskId, String description, int epicId) {
        super(taskName, taskId, description);
        this.epicId = epicId;
        this.status = TaskStatus.NEW;
    }

    @Override
    public String toString(){
        return "Название подзадачи: " + taskName+"\n"
                +"ID подзадачи: " + taskId+"\n"
                +"Статус подзадачи: " + status+"\n"
                +"Описание: " + description+"\n\n";
    }
}
