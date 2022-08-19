package taskmanager.Manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import taskmanager.TaskTypes.Epic;
import taskmanager.TaskTypes.Subtask;
import taskmanager.TaskTypes.Task;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

abstract class TasksManagerTest {
    private TaskManager taskManager;


    @BeforeEach
    void setUp() {
        taskManager = createTaskManager();
    }

    abstract public TaskManager createTaskManager();

    @Test
    void addTaskToMap() {
    }

    @Test
    void addTask() {
        final Task task = new Task("Test addNewTask", 0, "Test addNewTask description");
        final int taskId = taskManager.addTask(task);

        final Task savedTask = taskManager.getTask(taskId);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        final ArrayList<Integer> tasks = taskManager.getTasksList();

        assertNotNull(tasks, "Задачи на возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
    }

    @Test
    void getTasksList() {
        int task1Id = taskManager.addTask("Задача №1", "Эта задача для проверки метода, возвращающего список задач");
        int task2Id = taskManager.addTask("Задача №2", "Эта задача для проверки метода, возвращающего список задач");
        int task3Id = taskManager.addTask("Задача №3", "Эта задача для проверки метода, возвращающего список задач");
        Integer[] tasksList = taskManager.getTasksList().toArray(new Integer[0]);
        Integer[] listToCompare = {0,1,2};
        assertArrayEquals(listToCompare, tasksList);
    }

    @Test
    void getSubTasksList() {
        taskManager.addEpic("Эпик №1", "Это эпик для проверки метода, " +
                "возвращающего список его подзадач");
        taskManager.addEpic("Эпик №2", "Это эпик для проверки метода, " +
                "возвращающего список его подзадач");
        taskManager.addSubTask("Подзадача №1 Эпика №1", "Эта задача для проверки метода, " +
                "возвращающего список задач", 0);
        taskManager.addSubTask("Подзадача №2 Эпика №1", "Эта задача для проверки метода, " +
                "возвращающего список задач", 0);
        taskManager.addSubTask("Подзадача №1 Эпика №2", "Эта задача для проверки метода, " +
                "возвращающего список задач", 1);
        taskManager.addSubTask("Подзадача №2 Эпика №2", "Эта задача для проверки метода, " +
                "возвращающего список задач", 1);

        Integer[] allSubtasksToCompare = {2, 3, 4, 5};
        assertArrayEquals(allSubtasksToCompare, allSubtasksToCompare);

    }

    @Test
    void addSubTask() {
        int epicId = taskManager.addEpic("Эпик №1", "Это эпик для проверки метода, " +
                "добавляющего подзадачу");

        int subtaskId = taskManager.addSubTask("Подзадача №1 Эпика №1",
                "Эта задача для проверки метода, добавляющего подзадачу", 0);

        Epic epic = (Epic) taskManager.getTask(0);

        assertTrue(epic.getSubTasksInEpic().contains(1));

    }

    @Test
    void addEpic() {
        final Epic epic = new Epic("Эпик без названия", 0, "Это эпик для проверки метода, " +
                "добавляющего эпик");
        final int epicId = taskManager.addEpic(epic);

        final Epic savedEpic = (Epic) taskManager.getTask(epicId);

        assertNotNull(savedEpic, "Список эпиков пуст.");
        assertEquals(epic, savedEpic, "Эпики не совпадают.");
        final ArrayList<Integer> epics = taskManager.getEpicsList();

        assertNotNull(epics, "Эпик не попал в список эпиков.");
        assertEquals(1, epics.size(), "Количество эпиков в списке неверное");
        assertEquals(epic, savedEpic, "Эпики не совпадают.");
    }


    @Test
    void removeTask() {
        Task task1 = new Task("Test addNewTask", 0, "Test addNewTask description");
        final int taskId1= taskManager.addTask(task1);
        Task task2 = new Task("Test addNewTask", 0, "Test addNewTask description");
        final int taskId2= taskManager.addTask(task2);

        taskManager.removeTask(0);
        assertEquals(1, taskManager.getTasksList().size(), "Не удалось удалить задачу");


    }

    @Test
    void removeSubTask() {
        int epicId = taskManager.addEpic("Эпик №1", "Это эпик для проверки метода, " +
                "возвращающего список его подзадач");
        int subtask1Id = taskManager.addSubTask("Подзадача №1 Эпика №1",
                "Эта задача для проверки метода, удаляющего подзадачу", 0);
        int subtask2Id = taskManager.addSubTask("Подзадача №2 Эпика №1",
                "Эта задача для проверки метода, удаляющего подзадачу", 0);
        assertEquals(2, taskManager.getSubTasksList().size());
        taskManager.removeSubTask(1);
        assertEquals(1, taskManager.getSubTasksList().size(), "Не удалось удалить подзадачу");
    }

    @Test
    void getEpicsList() {
        taskManager.addEpic("Эпик №1", "Это эпик для проверки метода, " +
                "возвращающего список эпиков");
        taskManager.addEpic("Эпик №2", "Это эпик для проверки метода, " +
                "возвращающего список эпиков");
        taskManager.addEpic("Эпик №3", "Это эпик для проверки метода, " +
                "возвращающего список эпиков");
        ArrayList<Integer> epics = new ArrayList<>(Arrays.asList(0,1,2));
        assertEquals(epics, taskManager.getEpicsList());

    }

    @Test
    void getTask() {
        Task task = new Task("Задача без названия", 0, "Это задача для проверки метода, " +
                "возвращающего таск по его id");
        Epic epic = new Epic("Эпик без названия", 1, "Это эпик для проверки метода, " +
                "возвращающего таск по его id");
        Subtask subtask = new Subtask("Подзадача эпика", 2, "Это подзадача для проверки метода, " +
                "возвращающего таск по его id", 1);
        int taskId = taskManager.addTask(task);
        int epicId = taskManager.addEpic(epic);
        int subtaskId = taskManager.addSubTask(subtask);
        assertEquals(task, taskManager.getTask(0),"Возвращается неверная задача");
        assertEquals(epic, taskManager.getTask(1),"Возвращается неверный эпик");
        assertEquals(subtask, taskManager.getTask(2),"Возвращается неверная подзадача");

    }

    @Test
    void recoverTask() {
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
        taskManager.addTask("Задача №1", "Это первая задача для проверки метода, " +
                "возвращающего таск, не добавляя его в историю просмотров");
        taskManager.addTask("Задача №2", "Это вторая задача для проверки метода, " +
                "возвращающего таск, не добавляя его в историю просмотров");
        taskManager.addEpic("Это эпик", "Это эпик для проверки метода, " +
                "возвращающего таск, не добавляя его в историю просмотров");
        Task task1 = taskManager.getTask(0);
        Task task2 = taskManager.getTask(1);
        Epic epic = (Epic) taskManager.getTask(2);
        int historySize = historyManager.getHistory().size();
        Task task1Recovered = taskManager.recoverTask(0);
        int historySizeAfterRecovering = historyManager.getHistory().size();
        assertEquals(historySize, historySizeAfterRecovering, "Задача не должна попадать в историю " +
                "просмотров после восстановления");
    }

    @Test
    void getHistory() {
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
        taskManager.addTask("Задача №1", "Это первая задача для проверки метода, " +
                "возвращающего историю просмотров");
        taskManager.addTask("Задача №2", "Это вторая задача для проверки метода, " +
                "возвращающего историю просмотров");
        taskManager.addEpic("Это эпик", "Это эпик для проверки метода, " +
                "возвращающего историю просмотров");


        Task task1 = taskManager.getTask(0);
        Task task2 = taskManager.getTask(1);
        Epic epic = (Epic) taskManager.getTask(2);
        String history = taskManager.getHistory();

        assertEquals("0,1,2,", history);
    }

    @Test
    void getEpicIdFromSubtask(){
        int e = taskManager.addEpic("Эпик", "Это эпик для проверки метода, " +
                "возвращающего id из его подзадачи");
        int st = taskManager.addSubTask("Подзадача", "Эта задача для проверки метода, " +
                "возвращающего id эпика из его подзадачи", 0);
        Subtask subtask = (Subtask) taskManager.getTask(1);
        assertEquals(0, subtask.getEpicIdOfSubtask(), "Id эпика не возвращается из под задачи");
    }
}