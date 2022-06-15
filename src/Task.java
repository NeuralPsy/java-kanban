import java.util.HashMap;

public class Task {
    protected String taskName;
    protected String description;
    protected int taskId;
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

    public void changeStatus(String status){
        this.status = status;
    }


    public void updateTask(String taskName, String description, String status){
        this.taskName = taskName;
        this.description = description;
        this.status = status;
    }

    public void updateStatus(String status){
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
