package taskmanager.Manager;

import taskmanager.TaskTypes.Task;

import java.util.ArrayList;

public interface TaskManager {

    int addTask(String taskName, String description);

    public ArrayList<Integer> getTasksList();

    public ArrayList<Integer> getSubTasksList();

    int addSubTask(String taskName, String description, int epicId);

    int addEpic(String taskName, String description);

    void removeTask(int taskId);

    void removeSubTask(int subTaskId);

    public ArrayList<Integer> getEpicsList();

    public Task getTask(int newTaskId);
}
