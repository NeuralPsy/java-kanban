package taskmanager.TaskTypes;

public class Subtask extends Task {

    protected final int epicId;

    public Subtask(String taskName, int taskId, String description, int epicId) {
        super(taskName, taskId, description);
        this.epicId = epicId;
        this.status = TaskStatus.NEW;
        this.type = TaskTypes.SUBTASK;
        this.hasEpic = true;
    }

    public Subtask(Task task, int epicId) {
        super(task.getName(), task.getId(), task.getDescription());
        this.epicId = epicId;
        this.status = TaskStatus.NEW;
        this.type = TaskTypes.SUBTASK;
        this.hasEpic = true;
    }

    public Subtask(Subtask subtask) {
        super(subtask.getName(), subtask.getId(), subtask.getDescription());
        this.epicId = subtask.getEpicIdOfSubtask();
        this.status = TaskStatus.NEW;
        this.type = TaskTypes.SUBTASK;
        this.hasEpic = true;
    }

    public int getEpicIdOfSubtask(){
        return epicId;
    }


    @Override
    public String toString(){
        return "Название подзадачи: " + taskName+"\n"
                +"ID подзадачи: " + taskId+"\n"
                +"Дата начала: " + startTime+"\n"
                +"Дата окончания: " + endTime+"\n"
                +"Статус задачи: " + status+"\n"
                +"Описание: " + description+"\n\n";
    }

    public int getEpicId() {
        return this.epicId;
    }

}
