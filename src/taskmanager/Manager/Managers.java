package taskmanager.Manager;

import java.io.File;
import java.io.IOException;

public class Managers <T extends TaskManager> {

    public static TaskManager getDefault() throws IOException {
        File file = new File("src/taskmanager/Manager/BackedData/DefaultFileBackedTasksManager.csv");
        return new FileBackedTasksManager(file);
        }

    public InMemoryHistoryManager getDefaultHistory(){
        return new InMemoryHistoryManager();
    }
}

//Пока здесь ничего не делаю. Это лишь заготовка для следующих работ