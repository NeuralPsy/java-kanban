package taskmanager.Manager;

import taskmanager.Manager.Managers.InMemoryTasksManager;

class InMemoryTasksManagerTest extends TasksManagerTest<InMemoryTasksManager> {

    @Override
    public InMemoryTasksManager createTaskManager() {
        return new InMemoryTasksManager();
    }

}