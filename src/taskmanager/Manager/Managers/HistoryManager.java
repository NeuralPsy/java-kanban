package taskmanager.Manager.Managers;

import taskmanager.TaskTypes.Task;

import java.util.List;

public interface HistoryManager {

    public void add(Task task);

    public List<Integer> getHistory();

    void remove(Task task);

    public List<Task> getTasks();

    public String getStringHistory();

}
