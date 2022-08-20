package taskmanager.Manager;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import taskmanager.TaskTypes.Epic;
import taskmanager.TaskTypes.Subtask;
import taskmanager.TaskTypes.Task;
import taskmanager.TaskTypes.TaskStatus;

import java.util.ArrayList;

class FileBackedTasksManagerTest extends TasksManagerTest {

    @Override
    public TaskManager createTaskManager() {
        return new FileBackedTasksManager();
    }


    @Test
    void testToString() {
    }

    @Test
    void fromString() {
    }

    @Test
    void loadFromFile() {
    }

    @Test
    void save() {
    }
}