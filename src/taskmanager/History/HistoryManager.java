package taskmanager.History;

import taskmanager.TaskTypes.Task;

import java.util.LinkedHashMap;

public interface HistoryManager {

    public void add(Task task);

    public LinkedHashMap<Integer, Task> getHistory();

    void remove(Task task);
}
