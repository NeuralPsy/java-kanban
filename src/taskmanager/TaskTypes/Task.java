package taskmanager.TaskTypes;

import java.time.*;
import java.util.Objects;

public class Task {


    protected String taskName;
    protected String description;
    protected final int taskId;
    protected TaskStatus status;
    protected TaskTypes type = TaskTypes.TASK;
    protected boolean hasEpic = false;
    protected Duration duration;
    protected LocalDateTime startTime;
    protected LocalDateTime endTime;

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Duration getDuration() {
        return duration;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }


    public TaskTypes getType() {
        return type;
    }

    public boolean hasEpicOrNo(){
        return hasEpic;
    }



    public String getDescription() {
        return description;
    }

    public int getId() {
        return taskId;
    }

    public String getName() {
        return taskName;
    }



    public String getStatusAsString() {
        return status.name();
    }

    public TaskStatus getStatus() {
        return status;
    }




    public void setStatus(TaskStatus taskStatus) {
        this.status = taskStatus;
    }


    public Task(String taskName, int taskId, String description){
        this.taskName = taskName;
        this.status = TaskStatus.NEW;
        this.taskId = taskId;
        this.description = description;

    }

    public Task(Task task){
        this.taskName = task.getName();
        this.status = task.getStatus();
        this.taskId = task.getId();
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
        return taskId == task.taskId && Objects.equals(taskName, task.taskName)
                && Objects.equals(description, task.description) && status == task.status && type == task.type
                && Objects.equals(startTime, task.startTime) && Objects.equals(endTime, task.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskName, description, taskId, status, type, hasEpic, duration, startTime, endTime);
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
}
