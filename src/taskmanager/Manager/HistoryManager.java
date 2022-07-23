package taskmanager.Manager;

import taskmanager.TaskTypes.Task;

import java.util.ArrayList;

public interface HistoryManager {

    public void add(Task task);

    public ArrayList<Integer> getHistory();

    void remove(Task task);

    public ArrayList<Task> getTasks();
}
