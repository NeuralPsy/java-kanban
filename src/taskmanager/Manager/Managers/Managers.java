package taskmanager.Manager.Managers;

import java.io.IOException;

public class Managers <T extends TaskManager> {


    public static TaskManager getDefault() throws IOException {
        return new FileBackedTasksManager();
        }

    public InMemoryHistoryManager getDefaultHistory(){
        return new InMemoryHistoryManager();
    }
}

//Пока здесь ничего не делаю. Это лишь заготовка для следующих работ