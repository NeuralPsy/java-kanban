package taskmanager.TaskTypes;

import taskmanager.Manager.TaskStatus;

public class Task {
    protected String taskName;
    protected String description;
    protected final int taskId; //Присвоил final
    protected TaskStatus status;

    public TaskStatus getStatus() {
        return this.status;
    }

    public Task(String taskName, int taskId, String description){
        this.taskName = taskName;
        this.status = TaskStatus.NEW;
        this.taskId = taskId;
        this.description = description;

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

}
