package taskmanager.Manager.Managers;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.TreeSet;
import taskmanager.TaskTypes.Epic;
import taskmanager.TaskTypes.Subtask;
import taskmanager.TaskTypes.Task;
import taskmanager.TaskTypes.TaskStatus;

import java.util.ArrayList;

public interface TaskManager {

    int addTask(String taskName, String description);

    public ArrayList<Integer> getTasksList();

    public ArrayList<Integer> getSubTasksList();

    int addSubTask(String taskName, String description, int epicId);

    int addEpic(String taskName, String description);

    int addEpic(Epic epic);

    void removeTask(int taskId);

    void removeSubTask(int subTaskId);

    public ArrayList<Integer> getEpicsList();

    public Task getTask(int newTaskId);

    int addTask(Task task);

    public int convertTaskToSubtask(Task task, int epicId);

    public int addSubTask(Subtask subtask);

    public Task recoverTask(int newTaskId);

    public String getHistory();

    public void setSubtaskStatus(int subtaskId, TaskStatus taskStatus);

    TreeSet<Task> getPrioritizedTasks();

    public TreeSet<Task> getBusyTimeSchedule();

    public void setTime(Task task, LocalDateTime startDateTime, Duration duration);

    public void removeEpic(int epicId);
}
