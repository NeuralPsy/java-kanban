package taskmanager.Manager;

class InMemoryTasksManagerTest extends TasksManagerTest<InMemoryTasksManager> {

    @Override
    public InMemoryTasksManager createTaskManager() {
        return new InMemoryTasksManager();
    }

}