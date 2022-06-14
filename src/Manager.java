import java.util.HashMap;

public class Manager {
    private static HashMap<Integer, Task> tasksList = new HashMap<>();
    private static HashMap<Integer, Subtask> subTasksList = new HashMap<>();
    private static HashMap<Integer, Epic> epicsList = new HashMap<>();
    private static int newTaskId = 0;
    protected String[] taskStatuses = {"NEW", "IN_PROGRESS", "DONE"};

    public void newTask(String taskName, String description){
        newTaskId++;
        tasksList.put(newTaskId, new Task(taskName, newTaskId, description));
    }

    public void createSubTask(String taskName, String description){        // Метод для задач
        newTaskId++;
        subTasksList.put(newTaskId, new Subtask(taskName, newTaskId, description));
    }

    public void createSubTask(String taskName, String description, int epicId){        // Метод для подзадач в эпике
        newTaskId++;
        subTasksList.put(newTaskId, new Subtask(taskName, newTaskId, description, epicId));
    }

    public void createEpic(String taskName, String description){
        newTaskId++;
        epicsList.put(newTaskId, new Epic(taskName, taskStatuses[0], newTaskId, description));
    }

    public void removeTask(HashMap tasks, int taskId){
        tasks.remove(taskId);

    }

    public String getTaskStatus(int status) {
        return taskStatuses[status];
    }

    public Object getTask(HashMap tasks, int newTaskId){ // Возвращает задачу по ID
        return tasks.get(newTaskId);
    }




}
