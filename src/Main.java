import taskmanager.HttpServer.HttpTaskServer;
import taskmanager.HttpServer.KVServer;
import taskmanager.Manager.Managers.FileBackedTasksManager;
import taskmanager.Manager.Managers.HTTPTaskManager;
import taskmanager.TaskTypes.Subtask;
import taskmanager.TaskTypes.Task;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        //new KVServer().start();
        HttpTaskServer httpTaskServer = new HttpTaskServer();
        HTTPTaskManager taskManager = new HTTPTaskManager();

        int t1 = taskManager.addTask("Task","Description");
        int t2 = taskManager.addTask("Task","Description");
        int e1 = taskManager.addEpic("Epic", "Description");
        int st1 = taskManager.addSubTask("Subtask", "Description", e1);
        int st2 = taskManager.addSubTask("Subtask", "Description", e1);
//        Task task = taskManager.getTask(t1);

        taskManager.save("tm1");

        String l = taskManager.load("tm1");
        System.out.println(l);

        httpTaskServer.stop();
        taskManager.stopKV();

        HTTPTaskManager newTM = new HTTPTaskManager(l);
        int t22 = newTM.addTask("Task","Description");
        int e11 = taskManager.addEpic("Epic", "Description");
        int st22 = taskManager.addSubTask("Subtask", "Description", e11);
        newTM.save("tm2");

        String l2 = newTM.load("tm2");
        System.out.println(l2);

    }
}
