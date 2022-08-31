import taskmanager.HttpServer.HttpTaskServer;
import taskmanager.HttpServer.KVServer;
import taskmanager.Manager.Managers.FileBackedTasksManager;
import taskmanager.Manager.Managers.HTTPTaskManager;
import taskmanager.TaskTypes.Subtask;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        new KVServer().start();
        File file = new File("src/taskmanager/Manager/BackedData/FileBackedTasksManager.csv");
        HttpTaskServer httpTaskServer = new HttpTaskServer();
        HTTPTaskManager taskManager = new HTTPTaskManager("http://localhost:8078/");

        int t1 = taskManager.addTask("Task","Description");
        int t2 = taskManager.addTask("Task","Description");
        int e1 = taskManager.addEpic("Epic", "Description");
        int st1 = taskManager.addSubTask("Subtask", "Description", e1);

        taskManager.save();

    }
}
