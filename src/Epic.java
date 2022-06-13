import java.util.ArrayList;

class Epic extends Task{

    protected ArrayList<Integer> subTasksInEpic = new ArrayList<>();
    public Epic(String taskName, String status, int taskId, String description) {
        super(taskName, status, taskId, description);
    }
}
