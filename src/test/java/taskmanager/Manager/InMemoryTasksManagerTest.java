package taskmanager.Manager;

class InMemoryTasksManagerTest extends TasksManagerTest {

    @Override
    public TaskManager createTaskManager() {
        return new InMemoryTasksManager();
    }

}