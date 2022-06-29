package taskmanager.History;

import taskmanager.Manager.TaskManager;
import taskmanager.TaskTypes.Epic;
import taskmanager.TaskTypes.Subtask;
import taskmanager.TaskTypes.Task;

import java.util.HashMap;

public class InMemoryTaskManager implements TaskManager {
    private static HashMap<Integer, Task> tasksList = new HashMap<>();
    private static HashMap<Integer, Subtask> subTasksList = new HashMap<>();
    private static HashMap<Integer, Epic> epicsList = new HashMap<>();
    private static InMemoryHistoryManager history = new InMemoryHistoryManager();
    private static int newTaskId = 0;

    @Override
    public void newTask(String taskName, String description){
        newTaskId++;
        tasksList.put(newTaskId, new Task(taskName, newTaskId, description));
    }

    @Override
    public HashMap<Integer, Task> getTasksList() {
        return tasksList;
    }

    @Override
    public HashMap<Integer, Subtask> getSubTasksList() {
        return subTasksList;
    }

    @Override
    public void newSubTask(String taskName, String description, int epicId){  // Метод для создания подзадач в эпике
        newTaskId++;
        subTasksList.put(newTaskId, new Subtask(taskName, newTaskId, description, epicId));
        epicsList.get(epicId).addSubtaskToEpic(newTaskId);
        epicsList.get(epicId).setEpicStatus(subTasksList);

    }

    @Override
    public void newEpic(String epicName, String epicDescription){
        newTaskId++;
        epicsList.put(newTaskId, new Epic(epicName, newTaskId, epicDescription));
    }

    @Override
    public void removeTask(HashMap tasks, int taskId){
        tasks.remove(taskId);
    }

    @Override
    public void destroySubTask(HashMap<Integer, Subtask> subtasks, int taskId, int epicId){
        subtasks.remove(taskId);
        getEpicsList().get(epicId).removeSubTask(taskId);
    }

    @Override
    public HashMap<Integer, Epic> getEpicsList() {
        return epicsList;
    }

    @Override
    public Task getTask(HashMap tasks, int newTaskId){ // Возвращает задачу по ID
        history.add((Task) tasks.get(newTaskId));
        return (Task) tasks.get(newTaskId);
    }

    public static InMemoryHistoryManager getHistory(){
        for (Task task : history.getHistory()){
            System.out.println(task);
        }
        return history;
    }


}
