import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import taskmanager.Manager.InMemoryTasksManager;
import taskmanager.Manager.TaskManager;
import taskmanager.TaskTypes.Epic;



import java.util.ArrayList;

public class EpicTest {


    @Test
    public void addNewEpic() {
        TaskManager taskManager = new InMemoryTasksManager();
        final Epic epic = new Epic("Эпик без названия", 0, "Вот оно! Настоящее описание эпика");
        final int epicId = taskManager.addEpic(epic);
        final Epic savedEpic = (Epic) taskManager.getTask(epicId);

        assertNotNull(savedEpic, "Эпик не создан. Список эпиков пуст.");
        assertEquals(epic, savedEpic, "Эпики не совпадают.");
        final ArrayList<Integer> epics = taskManager.getEpicsList();

        assertNotNull(epics, "Задачи на возвращаются.");
        assertEquals(1, epics.size(), "Количество эпиков неверное");
        assertEquals(epic, savedEpic, "Задачи не совпадают.");

    }


}
