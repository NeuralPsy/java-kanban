import java.util.ArrayList;

public class Task {
    protected String taskName;
    protected String description;
    protected int taskId;
    protected String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



    public Task(String taskName, String status, int taskId, String description){
        this.taskName = taskName;
        this.status = status;
        this.taskId = taskId;
        this.description = description;

    }

    public void getTaskList(){

    }

    public void removeTasks(){

    }

    public int getTask(int taskId){
        return this.taskId;
    }

}
