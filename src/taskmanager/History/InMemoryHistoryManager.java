package taskmanager.History;

import taskmanager.TaskTypes.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager{


    Map<Integer, Task> history = new LinkedHashMap<>();

    // Если честно, подсказки в ТЗ меня запутали совсем.
    // Можно ведь просто применить LinkedHashMap

    @Override
    public void add(Task task) {
        ArrayList<Integer> tasks = new ArrayList<>(history.keySet());
        if (history.size() < 10 ) {
            if (history.containsKey(task.getTaskId())) {
                remove(task);
                history.put(task.getTaskId(), task);
            } else {
                history.put(task.getTaskId(), task);
            }
        } else {
            if (history.containsKey(task.getTaskId())) {
                remove(task);
                history.put(task.getTaskId(), task);
            } else {
                history.remove(tasks.get(0));
                history.put(task.getTaskId(), task);
            }

    }

    }

    public void remove(Task task){
        history.remove(task.getTaskId());
    }

    @Override
    public LinkedHashMap<Integer, Task> getHistory() {
        return new LinkedHashMap<>(this.history);
    }

    public ArrayList<Integer> getTasks(){
        return new ArrayList<>(history.keySet());
    }


}
