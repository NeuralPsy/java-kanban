package taskmanager.Manager;

import taskmanager.TaskTypes.Epic;
import taskmanager.TaskTypes.Subtask;
import taskmanager.TaskTypes.Task;
import taskmanager.TaskTypes.TaskStatus;

import java.util.ArrayList;
import java.util.HashMap;

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

    ArrayList<Task> getPrioritizedTasks(HashMap tasks);
}
