package taskmanager.TaskTypes;

public class Task {
    protected String taskName;
    protected String description;
    protected final int taskId; //Присвоил final
    protected String status;

    public String getStatus() {
        return this.status;
    }

    public Task(String taskName, int taskId, String description){
        this.taskName = taskName;
        this.status = "NEW";
        this.taskId = taskId;
        this.description = description;

    }

    public void changeStatus(String status){ // Оставил этот метод, хоть он и не нужен был по ТЗ.
        this.status = status;               // Мне кажется, что он может понадобиться
    }


    public void updateTask(String taskName, String description, String status){
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
