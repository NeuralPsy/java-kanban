package taskmanager.Manager;

import taskmanager.TaskTypes.LinkedTasksList;
import taskmanager.TaskTypes.Task;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryHistoryManager implements HistoryManager {


    private LinkedTasksList history = new LinkedTasksList();

    @Override
    public void add(Task task) {
        if (history.size() < 10 ) {
            if (history.containsTask(task)) {
                remove(task);
                history.linkLast(task);
            } else {
                history.linkLast(task);
            }
        } else {
            if (history.containsTask(task)) {
                remove(task);
                history.linkLast(task);
            } else {
                history.removeFirst();
                history.linkLast(task);
            }

    }

    }

    public void remove(Task task){
        history.remove(task);
    }

    @Override
    public ArrayList<Integer> getHistory() {
        return new ArrayList<>(history.getTaskIdList());
    }

    public ArrayList<Task> getTasks(){
        return new ArrayList<>(history.getTaskIdMap().values());
    }

    public String getStringHistory(){
       String listString = "";

       for(int x = 0; x < history.getTaskIdList().size(); x++){
           if (x == getHistory().size()-1) listString += history.getTaskIdList().get(x);
           listString += history.getTaskIdList().get(x)+",";
       }

       return listString;
    }




}
