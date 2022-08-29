package taskmanager.Manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import taskmanager.Manager.Managers.HistoryManager;
import taskmanager.Manager.Managers.InMemoryHistoryManager;
import taskmanager.TaskTypes.Epic;
import taskmanager.TaskTypes.Subtask;
import taskmanager.TaskTypes.Task;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class HistoryManagerTest {

    HistoryManager historyManager;

    void setUp() {
        historyManager = new InMemoryHistoryManager();
        Task task = new Task("Задача", 0, "Без названия");
        Task task2 = new Task("Задача", 1, "Без названия");
        Task task3 = new Task("Задача", 2, "Без названия");
        Task task4 = new Task("Задача", 3, "Без названия");
        Epic epic1 = new Epic("Задача", 4,"Без названия");
        Epic epic2 = new Epic("Задача", 5, "Без названия");
        Subtask subtask1 = new Subtask("Подзадача", 6, "Без названия", 4);
        Subtask subtask2 = new Subtask("Подзадача", 7, "Без названия", 5);
        historyManager.add(task);
        historyManager.add(task2);
        historyManager.add(task3);
        historyManager.add(task4);
        historyManager.add(epic1);
        historyManager.add(epic2);
        historyManager.add(subtask1);
        historyManager.add(subtask2);
    }

    @BeforeEach
    void setHistoryManager() {
        historyManager = new InMemoryHistoryManager();
    }

    @Test
    void shouldAddTaskToHistory() {
        Task task = new Task("Задача", 0, "Без названия");
        Task task2 = new Task("Задача", 1, "Без названия");
        historyManager.add(task);
        ArrayList<Integer> history = new ArrayList<>(historyManager.getHistory());
        assertEquals(0, history.get(0));
        assertEquals(1, history.size());
        historyManager.add(task2);
        history = new ArrayList<>(historyManager.getHistory());
        assertEquals(2, history.size());
    }

    @Test
    void getHistory() {
        setUp();
        ArrayList<Integer> history = new ArrayList<>(historyManager.getHistory());
        assertEquals(8, history.size());

        assertEquals("[0, 1, 2, 3, 4, 5, 6, 7]", history.toString());

    }

    @Test
    void remove() {
        Task task = new Task("Задача", 0, "Без названия");
        Task task2 = new Task("Задача", 1, "Без названия");
        Task task3 = new Task("Задача", 2, "Без названия");
        historyManager.add(task);
        historyManager.add(task2);
        historyManager.add(task3);
        assertEquals(3, historyManager.getHistory().size());
        historyManager.remove(task2);
        assertEquals(2, historyManager.getHistory().size());


    }

    @Test
    void getTasks() {
        Task task = new Task("Задача", 0, "Без названия");
        Task task2 = new Task("Задача", 1, "Без названия");
        Task task3 = new Task("Задача", 2, "Без названия");
        Task task4 = new Task("Задача", 3, "Без названия");
        Epic epic1 = new Epic("Задача", 4,"Без названия");
        Epic epic2 = new Epic("Задача", 5, "Без названия");
        Subtask subtask1 = new Subtask("Подзадача", 6, "Без названия", 4);
        Subtask subtask2 = new Subtask("Подзадача", 7, "Без названия", 5);
        historyManager.add(task);
        historyManager.add(task2);
        historyManager.add(task3);
        historyManager.add(task4);
        historyManager.add(epic1);
        historyManager.add(epic2);
        historyManager.add(subtask1);
        historyManager.add(subtask2);

        ArrayList<Task> history = new ArrayList<>(historyManager.getTasks());
        assertEquals(task, historyManager.getTasks().get(0));
        assertEquals(task2, historyManager.getTasks().get(1));
        assertEquals(task3, historyManager.getTasks().get(2));
        assertEquals(task4, historyManager.getTasks().get(3));
        assertEquals(epic1, historyManager.getTasks().get(4));
        assertEquals(epic2, historyManager.getTasks().get(5));
        assertEquals(8, history.size());
    }

    @Test
    void getStringHistory() {
        Task task = new Task("Задача", 0, "Без названия");
        Task task2 = new Task("Задача", 1, "Без названия");
        Task task3 = new Task("Задача", 2, "Без названия");
        Task task4 = new Task("Задача", 3, "Без названия");
        Epic epic1 = new Epic("Задача", 4,"Без названия");
        Epic epic2 = new Epic("Задача", 5, "Без названия");
        historyManager.add(task);
        historyManager.add(task2);
        historyManager.add(task3);
        historyManager.add(task4);
        historyManager.add(epic1);
        historyManager.add(epic2);

        String expectation = task.getId()+","
                + task2.getId()
                +","+ task3.getId()
                +","+ task4.getId()
                +","+ epic1.getId()
                +","+ epic2.getId();

        String reality = historyManager.getStringHistory();

        assertEquals(expectation, reality);

    }
}