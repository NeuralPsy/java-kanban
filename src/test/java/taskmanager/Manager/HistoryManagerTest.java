package taskmanager.Manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import taskmanager.TaskTypes.Epic;
import taskmanager.TaskTypes.Task;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class HistoryManagerTest {

    HistoryManager historyManager = new InMemoryHistoryManager();

    @BeforeEach
    void setUp() {
    }

    @Test
    void shouldAddTaskToHistory() {
        Task task = new Task("Задача", 0, "Без названия");
        Task task2 = new Task("Задача", 1, "Без названия");
        historyManager.add(task);
        ArrayList<Integer> history = historyManager.getHistory();
        assertEquals(0, history.get(0));
        assertEquals(1, history.size());
        historyManager.add(task2);
        history = historyManager.getHistory();
        assertEquals(2, history.size());
    }

    @Test
    void getHistory() {
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

        ArrayList<Integer> history = historyManager.getHistory();
        assertEquals(6, history.size());

        assertEquals("[0, 1, 2, 3, 4, 5]", history.toString());

    }

    @Test
    void remove() {
    }

    @Test
    void getTasks() {
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

        assertEquals(task, historyManager.getTasks().get(0));
        assertEquals(task2, historyManager.getTasks().get(1));
        assertEquals(task3, historyManager.getTasks().get(2));
        assertEquals(task4, historyManager.getTasks().get(3));
        assertEquals(epic1, historyManager.getTasks().get(4));
        assertEquals(epic2, historyManager.getTasks().get(5));
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

        String expectation = task.getTaskId()+","
                + task2.getTaskId()
                +","+ task3.getTaskId()
                +","+ task4.getTaskId()
                +","+ epic1.getTaskId()
                +","+ epic2.getTaskId();

        String reality = historyManager.getStringHistory();

        assertEquals(expectation, reality);

    }
}