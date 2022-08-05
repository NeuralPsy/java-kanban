package taskmanager.Manager;

import taskmanager.TaskTypes.Epic;
import taskmanager.TaskTypes.Subtask;
import taskmanager.TaskTypes.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryTasksManager implements TaskManager {
    private HashMap<Integer, Task> tasksList = new HashMap<>();
    private HashMap<Integer, Subtask> subTasksList = new HashMap<>();
    private HashMap<Integer, Epic> epicsList = new HashMap<>();
    private InMemoryHistoryManager history = new InMemoryHistoryManager();

    private static int newTaskId = 0;

    public void addTaskToMap(Task task){
        String taskClass = task.getType().name();

        switch (taskClass){
            case "TASK" : tasksList.put(task.getTaskId(), task);
                break;
            case "EPIC" :  epicsList.put(task.getTaskId(), (Epic) task);
                break;
            case "SUBTASK" : subTasksList.put(task.getTaskId(), (Subtask) task);
                break;
        }

    }

    @Override
    public int addTask(String taskName, String description){
        newTaskId++;
        Task newTask = new Task(taskName, newTaskId, description);
        tasksList.put(newTaskId, newTask);
        return newTaskId;
    }

    @Override
    public ArrayList<Integer> getTasksList() {
        return new ArrayList<>(this.tasksList.keySet());
    }

    @Override
    public ArrayList<Integer> getSubTasksList() {
        return new ArrayList<>(this.subTasksList.keySet());
    }

    @Override
    public int addSubTask(String taskName, String description, int epicId){
        newTaskId++;
        Subtask newSubTask = new Subtask(taskName, newTaskId, description, epicId);
        subTasksList.put(newTaskId, newSubTask);
        epicsList.get(epicId).addSubtaskToEpic(newTaskId);
        epicsList.get(epicId).setEpicStatus(subTasksList);
        return newTaskId;

    }

    @Override
    public int addEpic(String epicName, String epicDescription){
        newTaskId++;
        Epic newEpic = new Epic(epicName, newTaskId, epicDescription);
        epicsList.put(newTaskId, newEpic);
        return newTaskId;
    }

    @Override
    public void removeTask(int taskId){
        if (tasksList.containsKey(taskId)) {
            history.remove(tasksList.get(newTaskId));
            tasksList.remove(newTaskId);
        }

        if (subTasksList.containsKey(newTaskId)){
            history.remove(subTasksList.get(newTaskId));
            subTasksList.remove(newTaskId);
        }

        if (epicsList.containsKey(newTaskId)){
            history.remove(epicsList.get(newTaskId));
            subTasksList.remove(newTaskId);
        }


    }

    @Override
    public void removeSubTask(int subTaskId){
        for (int epicId : epicsList.keySet()){
            if (epicsList.get(epicId).
                    getSubTasksInEpic().
                    contains(subTaskId)){
                epicsList.get(epicId).
                        getSubTasksInEpic().
                        remove(subTaskId);
                subTasksList.remove(subTaskId);
                break;
            }
        }
    }

    @Override
    public ArrayList<Integer> getEpicsList() {
        return new ArrayList<>(this.epicsList.keySet());
    }

    @Override
    public Task getTask(int newTaskId){ // Возвращает задачу по ID
        Task taskToReturn = null;
        if (tasksList.containsKey(newTaskId)) {
            history.add(tasksList.get(newTaskId));
            taskToReturn = tasksList.get(newTaskId);
        }
        if (subTasksList.containsKey(newTaskId)){
            history.add(subTasksList.get(newTaskId));
            taskToReturn = subTasksList.get(newTaskId);
        }
        if (epicsList.containsKey(newTaskId)){
            history.add((epicsList.get(newTaskId)));
            taskToReturn = epicsList.get(newTaskId);
        } return taskToReturn;
    }

    public Task recoverTask(int newTaskId){ // Возвращает задачу по ID, но не передает сведения о просмотре в историю
        Task taskToReturn = null;
        if (tasksList.containsKey(newTaskId)) {
            taskToReturn = tasksList.get(newTaskId);
        }
        if (subTasksList.containsKey(newTaskId)){
            taskToReturn = subTasksList.get(newTaskId);
        }
        if (epicsList.containsKey(newTaskId)){
            taskToReturn = epicsList.get(newTaskId);
        } return taskToReturn;
    }


    public String getHistory(){
        StringBuilder stringHistory = new StringBuilder();
        for (Integer x : history.getHistory()) stringHistory.append(x+",");
       // stringHistory.deleteCharAt(-1);
        return stringHistory.toString();
    }

    public void addToHistory(int taskId){
        if (tasksList.containsKey(taskId)) {
            history.add(tasksList.get(taskId));
        }
        if (subTasksList.containsKey(taskId)){
           history.add(subTasksList.get(taskId));
        }
        if (epicsList.containsKey(taskId)){
           history.add((epicsList.get(taskId)));
        }

    }


}
