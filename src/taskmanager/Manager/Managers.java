package taskmanager.Manager;

import java.io.File;
import java.io.IOException;

public class Managers <T extends TaskManager> {

    public static TaskManager getDefault() {
        return new FileBackedTasksManager();
        }

    public InMemoryHistoryManager getDefaultHistory(){
        return new InMemoryHistoryManager();
    }
}

//Пока здесь ничего не делаю. Это лишь заготовка для следующих работ