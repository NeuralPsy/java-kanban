package taskmanager.Manager.Managers;

import org.junit.jupiter.api.*;
import taskmanager.HttpServer.HttpTaskServer;
import taskmanager.HttpServer.KVServer;
import taskmanager.Manager.TasksManagerTest;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class HTTPTaskManagerTest extends TasksManagerTest<HTTPTaskManager> {



    @AfterEach
    void afterEach(){
        taskManager.stopKV();
    }


    @Test
    void shouldSaveAndLoadFromServer() throws IOException, InterruptedException {
        HttpTaskServer httpTaskServer = new HttpTaskServer();

        int t1 = taskManager.addTask("Task","Description");
        taskManager.setTime(taskManager.getTask(t1), LocalDateTime.now(), Duration.ofMinutes(300));
        int t2 = taskManager.addTask("Task","Description");
        taskManager.setTime(taskManager.getTask(t2), LocalDateTime.now().plusDays(1), Duration.ofMinutes(300));
        int e1 = taskManager.addEpic("Epic", "Description");
        int st1 = taskManager.addSubTask("Subtask", "Description", e1);
        taskManager.setTime(taskManager.getTask(st1), LocalDateTime.now().plusDays(4), Duration.ofMinutes(1000));
        int st2 = taskManager.addSubTask("Subtask", "Description", e1);
        taskManager.setTime(taskManager.getTask(st2), LocalDateTime.now().plusDays(1).plusMinutes(1001),
                Duration.ofMinutes(300));
        taskManager.setTime(taskManager.getTask(e1), LocalDateTime.now().plusDays(1).plusMinutes(1001),
                Duration.ofMinutes(300));


        taskManager.save("tm1");

        String tm1Load = new String(taskManager.load("tm1"));

        httpTaskServer.stop();
        taskManager.stopKV();
        httpTaskServer = new HttpTaskServer();


        HTTPTaskManager newTM = new HTTPTaskManager(tm1Load);

        newTM.save("tm2");

        String tm2Load = new String(newTM.load("tm2"));

        assertEquals(tm1Load, tm2Load, "Загруженные данные таскменеджера не сообветствуют ранее сохраненным");
    }



    @Override
    public HTTPTaskManager createTaskManager() throws IOException, InterruptedException {
        return new HTTPTaskManager();
    }
}