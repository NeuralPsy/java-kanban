import java.util.HashMap;

public class Manager {
    private static HashMap<Integer, Task> tasksList = new HashMap<>();
    private static HashMap<Integer, Subtask> subTasksList = new HashMap<>();
    private static HashMap<Integer, Epic> epicsList = new HashMap<>();
    private static int newTaskId = 0;
    protected String[] taskStatuses = {"NEW", "IN_PROGRESS", "DONE"};

    public void newTask(String taskName, String description){
        newTaskId++;
        tasksList.put(newTaskId, new Task(taskName, taskStatuses[0], newTaskId, description));
    }

    public void newSubTask(String taskName, String description){
        newTaskId++;
        subTasksList.put(newTaskId, new Subtask(taskName, taskStatuses[0], newTaskId, description));
    }

    public void newEpic(String taskName, String description){
        newTaskId++;
        epicsList.put(newTaskId, new Epic(taskName, taskStatuses[0], newTaskId, description));
    }


}
