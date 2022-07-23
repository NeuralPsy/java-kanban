package taskmanager.Manager;

public class Managers <T extends TaskManager> {

    public static TaskManager getDefault(TaskManager taskManager){
        return taskManager;
        }

    public InMemoryHistoryManager getDefaultHistory(){
        return new InMemoryHistoryManager();
    }
}

//Пока здесь ничего не делаю. Это лишь заготовка для следующих работ