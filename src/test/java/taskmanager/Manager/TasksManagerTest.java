package taskmanager.Manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import taskmanager.TaskTypes.Epic;
import taskmanager.TaskTypes.Subtask;
import taskmanager.TaskTypes.Task;
import taskmanager.TaskTypes.TaskStatus;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

abstract class TasksManagerTest<T extends TaskManager> {
    T taskManager;

    @BeforeEach
    void setUp(T taskManager) {
        this.taskManager = taskManager;
    }

    @Test
    void addTaskToMap() {
    }

    @Test
    void addTask() {
        Task task = new Task("Test addNewTask", 0, "Test addNewTask description");
        final int taskId = taskManager.addTask(task);

        final Task savedTask = taskManager.getTask(taskId);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        final ArrayList<Integer> tasks = taskManager.getTasksList();

        assertNotNull(tasks, "Задачи на возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.get(0), "Задачи не совпадают.");

    }

    @Test
    void getTasksList() {
        taskManager.addTask("Задача №1", "Эта задача для проверки метода, возвращающего список задач");
        taskManager.addTask("Задача №2", "Эта задача для проверки метода, возвращающего список задач");
        taskManager.addTask("Задача №3", "Эта задача для проверки метода, возвращающего список задач");
        ArrayList<Integer> tasksList = taskManager.getTasksList();
        Integer[] arrayToCompare = {0, 1, 2};
        Integer[] tasksArray = tasksList.toArray(tasksList.toArray(new Integer[0]));
        assertArrayEquals(arrayToCompare, tasksArray);
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
        Integer[] subtasksInEpic1ToCompare = {2, 3};
        Integer[] subtasksInEpic2ToCompare = {4, 5};
        Integer[] allSubtasksToCompare = {2, 3, 4, 5};
        Integer[] allSubtasks = taskManager.getSubTasksList().toArray(new Integer[0]);
        Integer[] subtasksInEpic1 = taskManager
                .getSubTasksList()
                .stream()
                .filter(c -> {
                    Subtask subtask = (Subtask) taskManager.getTask(c);
                    return subtask.getEpicIdOfSubtask() == 0;})
                .collect(Collectors.toList())
                .toArray(new Integer[0]);

        Integer[] subtasksInEpic2 = taskManager
                .getSubTasksList()
                .stream()
                .filter(c -> {
                    Subtask subtask = (Subtask) taskManager.getTask(c);
                    return subtask.getEpicIdOfSubtask() == 1;})
                .collect(Collectors.toList())
                .toArray(new Integer[0]);
        assertArrayEquals(allSubtasks, allSubtasksToCompare);
        assertArrayEquals(subtasksInEpic1, subtasksInEpic1ToCompare);
        assertArrayEquals(subtasksInEpic2, subtasksInEpic2ToCompare);

    }

    @Test
    void addSubTask() {
        taskManager.addEpic("Эпик №1", "Это эпик для проверки метода, " +
                "добавляющего подзадачу");
        Task taskToSubtask = new Task("Подзадача №1 Эпика №1", 1,
                "Эта задача для проверки метода, добавляющего подзадачу");

        taskManager.convertTaskToSubtask(taskToSubtask, 0);

        final Subtask savedSubtask = (Subtask) taskManager.getTask(1);

        assertNotNull(savedSubtask, "Подзадача не найдена.");
        assertEquals(taskToSubtask, savedSubtask, "Подзадачи не совпадают.");

        final ArrayList<Integer> subtasks = taskManager.getSubTasksList();

        assertNotNull(subtasks, " Подзадачи на возвращаются.");
        assertEquals(1, taskManager.getSubTasksList().size(), "Неверное количество задач.");
        assertEquals(0, savedSubtask.getEpicIdOfSubtask(), "Эпика с id "+
                savedSubtask.getEpicIdOfSubtask()+" не существует");

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
        taskManager.addEpic("Эпик №1", "Это эпик для проверки метода, " +
                "возвращающего список его подзадач");
        taskManager.addSubTask("Подзадача №1 Эпика №1", "Эта задача для проверки метода, " +
                "удаляющего подзадачу", 0);
        taskManager.addSubTask("Подзадача №2 Эпика №1", "Эта задача для проверки метода, " +
                "удаляющего подзадачу", 0);
        taskManager.removeSubTask(1);
        assertEquals(1, taskManager.getTasksList().size(), "Не удалось удалить подзадачу");
    }

    @Test
    void getEpicsList() {
    }

    @Test
    void getTask() {
    }

    @Test
    void recoverTask() {
    }

    @Test
    void getHistory() {
    }

    @Test
    void addToHistory() {
    }
}