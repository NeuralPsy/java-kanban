package taskmanager.Manager.Managers;

import java.io.IOException;

public class Managers <T extends TaskManager> {


    public static TaskManager getDefault(String url) throws IOException, InterruptedException {
        return new HTTPTaskManager(url);
        }

    public InMemoryHistoryManager getDefaultHistory(){
        return new InMemoryHistoryManager();
    }
}

//Пока здесь ничего не делаю. Это лишь заготовка для следующих работ