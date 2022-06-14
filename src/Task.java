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

    public Task(String taskName, int taskId, String description){
        this.taskName = taskName;
        this.status = "NEW";
        this.taskId = taskId;
        this.description = description;

    }

    public void changeStatus(String status){
        this.status = status;
    }

    @Override
    public String toString(){
        return "Название задачи: " + taskName+"\n"
                +"Статус задачи: " + status+"\n"
                +"Описание: " + description+"\n\n";
    }




}
