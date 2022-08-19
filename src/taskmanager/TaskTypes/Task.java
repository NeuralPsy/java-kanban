package taskmanager.TaskTypes;

import java.util.Objects;

public class Task {


    protected String taskName;
    protected String description;
    protected final int taskId;
    protected TaskStatus status;
    protected TaskTypes type = TaskTypes.TASK;
    protected boolean hasEpic = false;

    public TaskTypes getType() {
        return type;
    }



    public String getDescription() {
        return description;
    }

    public int getTaskId() {
        return taskId;
    }

    public String getTaskName() {
        return taskName;
    }



    public String getStatusAsString() {
        return status.name();
    }

    public TaskStatus getStatus() {
        return status;
    }




    public void setStatus(String string) {
        this.status = TaskStatus.valueOf(string);
    }


    public Task(String taskName, int taskId, String description){
        this.taskName = taskName;
        this.status = TaskStatus.NEW;
        this.taskId = taskId;
        this.description = description;

    }

    public Task(Task task){
        this.taskName = task.getTaskName();
        this.status = task.getStatus();
        this.taskId = task.getTaskId();
        this.description = task.getDescription();

    }


    public void changeStatus(TaskStatus status){ // Оставил этот метод, хоть он и не нужен был по ТЗ.
        this.status = status;               // Мне кажется, что он может понадобиться
    }

    public void updateTask(String taskName, String description, TaskStatus status){
        this.taskName = taskName;
        this.description = description;
        this.status = status;
    }

    @Override
    public String toString(){
        return "Название задачи: " + taskName+"\n"
                +"ID задачи: " + taskId+"\n"
                +"Статус задачи: " + status+"\n"
                +"Описание: " + description+"\n\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task task = (Task) o;
        return taskId == task.taskId && Objects.equals(taskName, task.taskName) && Objects.equals(description, task.description) && status == task.status && type == task.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskName, description, taskId, status, type);
    }
}
