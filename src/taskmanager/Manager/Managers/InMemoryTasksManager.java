package taskmanager.Manager.Managers;

import taskmanager.Manager.Exceptions.TasksTimeIntersectionException;
import taskmanager.Manager.Exceptions.WrongTaskTypeException;
import taskmanager.TaskTypes.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class InMemoryTasksManager implements TaskManager {
    private HashMap<Integer, Task> tasksList = new HashMap<>();
    private HashMap<Integer, Subtask> subTasksList = new HashMap<>();
    private HashMap<Integer, Epic> epicsList = new HashMap<>();
    private final InMemoryHistoryManager history = new InMemoryHistoryManager();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    private TreeSet<Task> busyTimeSchedule = new TreeSet<>();

    public DateTimeFormatter getFormatter() {
        return formatter;
    }


    private static int newTaskId = 0;

    public void addTaskToMap(Task task){
        String taskClass = task.getType().name();

        try {
            switch (taskClass) {
                case "TASK":
                    tasksList.put(task.getId(), task);
                    break;
                case "EPIC":
                    epicsList.put(task.getId(), (Epic) task);
                    break;
                case "SUBTASK":
                    subTasksList.put(task.getId(), (Subtask) task);
                    break;
                default:
                    throw new WrongTaskTypeException("Wrong task type");
            }
        } catch (WrongTaskTypeException e){
            System.out.println(e.getMessage());
        }

    }

    @Override
    public int addTask(String taskName, String description){
        newTaskId++;
        Task newTask = new Task(taskName, newTaskId, description);
        tasksList.put(newTaskId, newTask);
        return newTaskId;
    }

    public int addTask(Task task){
        Task newTask = new Task(task.getName(), task.getId(), task.getDescription());
        tasksList.put(newTask.getId(), newTask);
        return newTask.getId();
    }

    @Override
    public ArrayList<Integer> getTasksList() {
        return new ArrayList<>(tasksList.keySet());
    }

    @Override
    public ArrayList<Integer> getSubTasksList() {
        return new ArrayList<>(subTasksList.keySet());
    }

    @Override
    public int addSubTask(String taskName, String description, int epicId){
        newTaskId++;
        Subtask newSubTask = new Subtask(taskName, newTaskId, description, epicId);
        subTasksList.put(newTaskId, newSubTask);
        epicsList.get(epicId).addSubtaskToEpic(newSubTask);
        epicsList.get(epicId).setEpicStatus();
        return newTaskId;

    }

    @Override
    public int addSubTask(Subtask subtask){
        Subtask newSubTask = new Subtask(subtask.getName(), subtask.getId(),
                subtask.getDescription(), subtask.getEpicIdOfSubtask());
        subTasksList.put(newSubTask.getId(), newSubTask);
        epicsList.get(newSubTask.getEpicIdOfSubtask()).addSubtaskToEpic(newSubTask);
        epicsList.get(newSubTask.getEpicIdOfSubtask()).setEpicStatus();
        return newSubTask.getId();

    }

    public int convertTaskToSubtask(Task task, int epicId){
        Subtask newSubTask = new Subtask(task, epicId);
        subTasksList.put(newSubTask.getId(), newSubTask);
        epicsList.get(epicId).addSubtaskToEpic(newSubTask);
        epicsList.get(epicId).setEpicStatus();
        return newSubTask.getId();

    }

    @Override
    public int addEpic(String epicName, String epicDescription){
        newTaskId++;
        Epic newEpic = new Epic(epicName, newTaskId, epicDescription);
        epicsList.put(newTaskId, newEpic);
        return newTaskId;
    }

    public int addEpic(Epic epic){
        epicsList.put(epic.getId(), epic);
        return epic.getId();
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
                subTasksList.remove(subTaskId);
                epicsList.get(epicId).removeSubTask(subTaskId);
                break;
            }
        }
    }

    public void removeEpic(int epicId){
        epicsList.remove(epicId);
        for (int id : subTasksList.keySet()){
            if (subTasksList.get(id).getEpicIdOfSubtask() == epicId) subTasksList.remove(id);
            }
        }

    @Override
    public ArrayList<Integer> getEpicsList() {
        return new ArrayList<>(this.epicsList.keySet());
    }

    @Override
    public Task getTask(int newTaskId){
        Task taskToReturn = null;
        if (tasksList.containsKey(newTaskId)) {
            history.add(tasksList.get(newTaskId));
            taskToReturn = tasksList.get(newTaskId);
        }
        if (subTasksList.containsKey(newTaskId)){
            history.add(subTasksList.get(newTaskId));
            taskToReturn = (Subtask) subTasksList.get(newTaskId);
        }
        if (epicsList.containsKey(newTaskId)){
            history.add((epicsList.get(newTaskId)));
            taskToReturn = (Epic) epicsList.get(newTaskId);
        } return taskToReturn;
    }

    public Task recoverTask(int newTaskId){
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

    public void setSubtaskStatus(int subtaskId, TaskStatus taskStatus){
        if (getTask(subtaskId).hasEpicOrNo()) {
            getTask(subtaskId).setStatus(taskStatus);
            Subtask subtask = (Subtask) getTask(subtaskId);
            epicsList.get(subtask.getEpicIdOfSubtask()).setEpicStatus();
        }
    }


    public TreeSet<Task> getBusyTimeSchedule() {
        return busyTimeSchedule;
    }

    @Override
    public TreeSet<Task> getPrioritizedTasks() {
        return new TreeSet(this.busyTimeSchedule);
    }

    public void setTime(Task task, LocalDateTime startDateTime, Duration duration) {
        boolean isInvalidTime = false;
        if (task.getType() != TaskTypes.EPIC) {
            isInvalidTime = busyTimeSchedule.stream()
                    .anyMatch(time -> (time.getStartTime().isBefore(startDateTime) || time.getStartTime().equals(startDateTime))
                            && time.getEndTime().isAfter(startDateTime));
        }

        if (isInvalidTime) {
            throw new TasksTimeIntersectionException("Это время уже занято другой задачей");
        }

        task.setStartTime(startDateTime);

        if (task.getType() == TaskTypes.EPIC) {
            Duration duration1 = subTasksList.values()
                    .stream().map(subtask -> subtask.getDuration())
                    .reduce(Duration.ZERO, Duration::plus);

            task.setDuration(duration1);
            task.setEndTime(task.getStartTime().plusMinutes(duration1.toMinutes()));
            busyTimeSchedule.add(task);
            return;
        }
        task.setDuration(Duration.ofMinutes(duration.toMinutes()));
        task.setEndTime(task.getStartTime().plusMinutes(duration.toMinutes()));
        busyTimeSchedule.add(task);


    }



}
