package taskmanager.Manager;

import taskmanager.History.InMemoryHistoryManager;

public class Managers <T extends TaskManager> {

    public TaskManager getDefault(TaskManager taskManager){
        return taskManager;
        }

    public InMemoryHistoryManager getDefaultHistory(InMemoryHistoryManager history){
        return history;
    }
}

//Пока здесь ничего не делаю. Это лишь заготовка для следующих работ