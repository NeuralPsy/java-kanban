package taskmanager.Manager;

import taskmanager.TaskTypes.Task;

import java.util.ArrayList;
import java.util.HashMap;

public interface TaskManager {

    void addTask(String taskName, String description);

    public ArrayList<Integer> getTasksList();

    public ArrayList<Integer> getSubTasksList();

    void addSubTask(String taskName, String description, int epicId);

    void addEpic(String taskName, String description);

    void removeTask(int taskId);

    void removeSubTask(int subTaskId);

    public ArrayList<Integer> getEpicsList();

    public Task getTask(int newTaskId);
}
