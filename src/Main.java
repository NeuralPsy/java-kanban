import taskmanager.HttpServer.HttpTaskServer;
import taskmanager.HttpServer.KVServer;
import taskmanager.Manager.Managers.FileBackedTasksManager;
import taskmanager.Manager.Managers.HTTPTaskManager;
import taskmanager.TaskTypes.Subtask;
import taskmanager.TaskTypes.Task;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        HttpTaskServer httpTaskServer = new HttpTaskServer();
        HTTPTaskManager taskManager = new HTTPTaskManager();

        int t1 = taskManager.addTask("Task","Description");
        taskManager.setTime(taskManager.getTask(t1), LocalDateTime.now(), Duration.ofMinutes(300));
        int t2 = taskManager.addTask("Task","Description");
        taskManager.setTime(taskManager.getTask(t2), LocalDateTime.now().plusDays(1), Duration.ofMinutes(300));
        int e1 = taskManager.addEpic("Epic", "Description");
        int st1 = taskManager.addSubTask("Subtask", "Description", e1);
        taskManager.setTime(taskManager.getTask(st1), LocalDateTime.now().plusDays(4), Duration.ofMinutes(1000));
        int st2 = taskManager.addSubTask("Subtask", "Description", e1);
        taskManager.setTime(taskManager.getTask(st2), LocalDateTime.now().plusDays(1).plusMinutes(1001), Duration.ofMinutes(300));
        taskManager.setTime(taskManager.getTask(e1), LocalDateTime.now().plusDays(1).plusMinutes(1001), Duration.ofMinutes(300));

        Task task = taskManager.getTask(t1);

        taskManager.save("tm1");

        String l = taskManager.load("tm1");
        System.out.println(l);

        httpTaskServer.stop();
        taskManager.stopKV();

        HTTPTaskManager newTM = new HTTPTaskManager(l);
        int t22 = newTM.addTask("Task","Description");
        newTM.setTime(newTM.getTask(t22), LocalDateTime.now().plusDays(10), Duration.ofMinutes(300));
        int e11 = newTM.addEpic("Epic", "Description");
        int st22 = newTM.addSubTask("Subtask", "Description", e11);
        newTM.setTime(newTM.getTask(st22), LocalDateTime.now().plusDays(11), Duration.ofMinutes(300));
        newTM.setTime(newTM.getTask(e11), LocalDateTime.now(), Duration.ofMinutes(1));
        newTM.save("tm2");

        String l2 = newTM.load("tm2");
        System.out.println(l2);

        httpTaskServer.stop();

    }
}
