import java.util.ArrayList;

class Epic extends Task{

    protected ArrayList<Integer> subTasksInEpic = new ArrayList<>();
    public Epic(String taskName, String status, int epicId, String description) {
        super(taskName, epicId, description);
    }

    public void addTaskToEpic(int taskId){
        subTasksInEpic.add(taskId);
    }
}
