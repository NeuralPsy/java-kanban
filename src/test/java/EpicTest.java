import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import taskmanager.Manager.InMemoryTasksManager;
import taskmanager.Manager.TaskManager;
import taskmanager.TaskTypes.Epic;
import taskmanager.TaskTypes.TaskStatus;


import java.util.ArrayList;

public class EpicTest {


    @Test
    void shouldAddNewEpicNormalEmptyNoSubtasks() {
        TaskManager taskManager = new InMemoryTasksManager();
        final Epic epic = new Epic("Эпик без названия", 0, "Вот оно! Настоящее описание эпика");
        final int epicId = taskManager.addEpic(epic);
        final Epic savedEpic = (Epic) taskManager.getTask(epicId);

        assertNotNull(savedEpic, "Эпик не создан. Список эпиков пуст.");
        assertEquals(epic, savedEpic, "Эпики не совпадают.");
        final ArrayList<Integer> epics = taskManager.getEpicsList();
        final ArrayList<Integer> subtasks = taskManager.getSubTasksList();

        assertNotNull(epics, "Эпик не попал в список эпиков.");
        assertEquals(1, epics.size(), "Количество эпиков в списке неверное");
        assertEquals(epic, savedEpic, "Эпики не совпадают.");
        assertEquals(TaskStatus.NEW, savedEpic.getStatus(), "В новом эпике без подзадач должен быть статус 'NEW' ");

    }

}
