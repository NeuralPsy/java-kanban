package taskmanager.HttpServer;

import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import taskmanager.Manager.Managers.FileBackedTasksManager;
import taskmanager.Manager.Managers.TaskManager;

import java.io.IOException;
import java.net.InetSocketAddress;

import static org.junit.jupiter.api.Assertions.*;

class HttpTaskServerTest {
    TaskManager tasksManager;
    HttpServer httpServer;

    @BeforeEach
    void createHttpServer() throws IOException {
        tasksManager = new FileBackedTasksManager();
        httpServer = HttpServer.create(new InetSocketAddress(8080), 0);
        httpServer.createContext("/tasks", new HttpTaskServer.TaskHandler());
        httpServer.start();
    }

    @Test
    void taskToJson() {
    }

    @Test
    void subtaskToJson() {
    }

    @Test
    void epicToJson() {
    }

    @Test
    void taskListToJson() {
    }

    @Test
    void getSubtaskListAsJson() {
    }

    @Test
    void getEpicsListAsJson() {
    }

    @Test
    void taskFromJson() {
    }

    @Test
    void subtaskFromJson() {
    }

    @Test
    void epicFromJson() {
    }

    @Test
    void getItemById() {
    }

    @Test
    void getItemList() {
    }

    @Test
    void getItemAsJson() {
    }

    @Test
    void deleteItemById() {
    }
}