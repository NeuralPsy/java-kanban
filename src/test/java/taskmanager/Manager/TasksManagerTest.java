package taskmanager.Manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import taskmanager.TaskTypes.Epic;
import taskmanager.TaskTypes.Subtask;
import taskmanager.TaskTypes.Task;
import taskmanager.TaskTypes.TaskStatus;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

abstract class TasksManagerTest {
    private TaskManager taskManager;

    abstract public TaskManager createTaskManager();

    @BeforeEach
    void setTaskManager() {
        taskManager = createTaskManager();
    }



    @Test
    void shouldAddTaskToMap() {
    }

    @Test
    void shouldAddTask() {
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
    void shouldGetTasksList() {
        final int task1Id = taskManager.addTask("Задача №1",
                "Эта задача для проверки метода, возвращающего список задач");
        final int task2Id = taskManager.addTask("Задача №2",
                "Эта задача для проверки метода, возвращающего список задач");
        final int task3Id = taskManager.addTask("Задача №3",
                "Эта задача для проверки метода, возвращающего список задач");
        Integer[] expectedTasksList = {task3Id,task1Id,task2Id};
        Integer[] realTasksList = taskManager.getTasksList().toArray(new Integer[0]);
        assertArrayEquals(expectedTasksList, realTasksList);
    }

    @Test
    void shouldGetSubTasksList() {
        int epic1Id = taskManager.addEpic("Эпик №1", "Это эпик для проверки метода, " +
                "возвращающего список его подзадач");
        int epic2Id = taskManager.addEpic("Эпик №2", "Это эпик для проверки метода, " +
                "возвращающего список его подзадач");
        int subtask1Id = taskManager.addSubTask("Подзадача №1 Эпика №1", "Эта задача для проверки метода, " +
                "возвращающего список задач", epic1Id);
        int subtask2Id = taskManager.addSubTask("Подзадача №2 Эпика №1", "Эта задача для проверки метода, " +
                "возвращающего список задач", epic1Id);
        int subtask3Id = taskManager.addSubTask("Подзадача №1 Эпика №2", "Эта задача для проверки метода, " +
                "возвращающего список задач", epic2Id);
        int subtask4Id = taskManager.addSubTask("Подзадача №2 Эпика №2", "Эта задача для проверки метода, " +
                "возвращающего список задач", epic2Id);

        Integer[] expectedSubtasksList = {subtask1Id, subtask2Id, subtask3Id, subtask4Id};
        Integer[] realSubtasksList = taskManager.getSubTasksList().toArray(new Integer[0]);
        assertArrayEquals(expectedSubtasksList, realSubtasksList);

    }

    @Test
    void shouldAddSubTask() {
        int epicId = taskManager.addEpic("Эпик №1", "Это эпик для проверки метода, " +
                "добавляющего подзадачу");

        int subtaskId = taskManager.addSubTask("Подзадача №1 Эпика №1",
                "Эта задача для проверки метода, добавляющего подзадачу", epicId);

        Epic epic = (Epic) taskManager.getTask(epicId);

        assertTrue(epic.getSubTasksInEpic().contains(subtaskId));

    }

    @Test
    void shouldAddEpic() {
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
     void shouldChangeEpicStatusWhenSubtasksAreAdded(){
        int epicId = taskManager.addEpic("Эпик", "Это эпик для проверки изменения его статуса " +
                "при добавлении или изменении его подзадач");
        assertEquals(TaskStatus.EMPTY_EPIC, taskManager.getTask(epicId).getStatus());
        int subtask1Id = taskManager.addSubTask("Подзадача №1", "Без описания", epicId);
        int subtask2Id = taskManager.addSubTask("Подзадача №2", "Без описания", epicId);
        assertEquals(TaskStatus.NEW, taskManager.getTask(epicId).getStatus());
        taskManager.setSubtaskStatus(subtask1Id, TaskStatus.DONE);
        assertEquals(TaskStatus.IN_PROGRESS, taskManager.getTask(epicId).getStatus());
        taskManager.setSubtaskStatus(subtask2Id, TaskStatus.DONE);
        assertEquals(TaskStatus.DONE, taskManager.getTask(epicId).getStatus());
        taskManager.setSubtaskStatus(subtask1Id, TaskStatus.IN_PROGRESS);
        taskManager.setSubtaskStatus(subtask2Id, TaskStatus.IN_PROGRESS);
        assertEquals(TaskStatus.IN_PROGRESS, taskManager.getTask(epicId).getStatus());
    }


    @Test
    void shouldRemoveTask() {
        Task task1 = new Task("Test addNewTask", 0, "Test addNewTask description");
        final int taskId1= taskManager.addTask(task1);
        Task task2 = new Task("Test addNewTask", 0, "Test addNewTask description");
        final int taskId2= taskManager.addTask(task2);

        taskManager.removeTask(0);
        assertEquals(1, taskManager.getTasksList().size(), "Не удалось удалить задачу");


    }

    @Test
    void shouldRemoveSubTask() {
        int epicId = taskManager.addEpic("Эпик №1", "Это эпик для проверки метода, " +
                "возвращающего список его подзадач");
        int subtask1Id = taskManager.addSubTask("Подзадача №1 Эпика №1",
                "Эта задача для проверки метода, удаляющего подзадачу", epicId);
        int subtask2Id = taskManager.addSubTask("Подзадача №2 Эпика №1",
                "Эта задача для проверки метода, удаляющего подзадачу", epicId);
        assertEquals(2, taskManager.getSubTasksList().size());
        taskManager.removeSubTask(subtask1Id);
        assertEquals(1, taskManager.getSubTasksList().size(), "Не удалось удалить подзадачу");
    }

    @Test
    void shouldGetEpicsList() {
        int e1 = taskManager.addEpic("Эпик №1", "Это эпик для проверки метода, " +
                "возвращающего список эпиков");
        int e2 = taskManager.addEpic("Эпик №2", "Это эпик для проверки метода, " +
                "возвращающего список эпиков");
        int e3 = taskManager.addEpic("Эпик №3", "Это эпик для проверки метода, " +
                "возвращающего список эпиков");
        Integer[] expectedArray = {e1,e2,e3};
        Integer[] realArray = taskManager.getEpicsList().toArray(new Integer[0]);
        assertArrayEquals(expectedArray, realArray);

    }

    @Test
    void shouldGetTask() {
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
    void shouldRecoverTask() {
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
    void shouldGetHistory() {
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
        int task1Id = taskManager.addTask("Задача №1", "Это первая задача для проверки метода, " +
                "возвращающего историю просмотров");
        int task2Id = taskManager.addTask("Задача №2", "Это вторая задача для проверки метода, " +
                "возвращающего историю просмотров");
        int task3Id = taskManager.addEpic("Это эпик", "Это эпик для проверки метода, " +
                "возвращающего историю просмотров");


        Task task1 = taskManager.getTask(task1Id);
        Task task2 = taskManager.getTask(task2Id);
        Epic epic = (Epic) taskManager.getTask(task3Id);
        String expectedHistory = task1Id+","+task2Id+","+task3Id+",";
        String realHistory = taskManager.getHistory();

        assertEquals(expectedHistory, realHistory);
    }

    @Test
    void shouldGetEpicIdFromSubtask(){
        int epicId = taskManager.addEpic("Эпик", "Это эпик для проверки метода, " +
                "возвращающего id из его подзадачи");
        int subtaskId = taskManager.addSubTask("Подзадача", "Эта задача для проверки метода, " +
                "возвращающего id эпика из его подзадачи", epicId);
        Subtask subtask = (Subtask) taskManager.getTask(subtaskId);
        assertEquals(epicId, subtask.getEpicIdOfSubtask(), "Id эпика не возвращается из под задачи");
    }
}