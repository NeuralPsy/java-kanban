package taskmanager.Manager;

import taskmanager.TaskTypes.Epic;
import taskmanager.TaskTypes.Subtask;
import taskmanager.TaskTypes.Task;

import java.util.HashMap;

public interface TaskManager {

    void newTask(String taskName, String description);

    public HashMap<Integer, Task> getTasksList();

    public HashMap<Integer, Subtask> getSubTasksList();

    void newSubTask(String taskName, String description, int epicId);

    void newEpic(String taskName, String description);

    void removeTask(HashMap tasks, int taskId);

    void destroySubTask(HashMap<Integer, Subtask> subtasks, int taskId, int epicId);

    public HashMap<Integer, Epic> getEpicsList();

    public Task getTask(HashMap tasks, int newTaskId);
}
