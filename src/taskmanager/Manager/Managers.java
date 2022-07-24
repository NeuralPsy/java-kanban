package taskmanager.Manager;

public class Managers <T extends TaskManager> {

    public static TaskManager getDefault(){
        return new InMemoryTaskManager();
        }

    public InMemoryHistoryManager getDefaultHistory(){
        return new InMemoryHistoryManager();
    }
}

//Пока здесь ничего не делаю. Это лишь заготовка для следующих работ