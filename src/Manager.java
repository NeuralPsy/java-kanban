import java.util.HashMap;

public class Manager {
    private static HashMap<Integer, Task> tasksList = new HashMap<>();
    private static HashMap<Integer, Subtask> subTasksList = new HashMap<>();
    private static HashMap<Integer, Epic> epicsList = new HashMap<>();
    private static int newTaskId = 0;

    public void newTask(String taskName, String description){
        newTaskId++;
        tasksList.put(newTaskId, new Task(taskName, newTaskId, description));
    }

    public HashMap<Integer, Task> getTasksList() {
        return tasksList;
    }

    public HashMap<Integer, Subtask> getSubTasksList() {
        return subTasksList;
    }


    public void createSubTask(String taskName, String description, int epicId){  // Метод для создания подзадач в эпике
        newTaskId++;
        subTasksList.put(newTaskId, new Subtask(taskName, newTaskId, description, epicId));
        epicsList.get(epicId).addSubtaskToEpic(newTaskId);
        epicsList.get(epicId).setEpicStatus(subTasksList);

    }

    public void createEpic(String taskName, String description){
        newTaskId++;
        epicsList.put(newTaskId, new Epic(taskName, newTaskId, description));
    }

    public void removeTask(HashMap tasks, int taskId){
        tasks.remove(taskId);
    }

    public void destroySubTask(HashMap<Integer, Subtask> subtasks, int taskId, int epicId){
        subtasks.remove(taskId);
        getEpicsList().get(epicId).removeSubTask(taskId);
    }

    public HashMap<Integer, Epic> getEpicsList() {
        return epicsList;
    }


    public Task getTask(HashMap tasks, int newTaskId){ // Возвращает задачу по ID
        return (Task) tasks.get(newTaskId);
    }



}
