public class Main {

    public static void main(String[] args) {
        Manager manager = new Manager();

        String taskName1 = "Задача №1";
        String task1Description = "Здесь может быть описание задачи, но для этого нужно что-то придумать";

        String taskName2 = "Задача №2";
        String task2Description = "Пока еще никто ничего не придумал";

        manager.newTask(taskName1, task1Description);
        Task test1 = manager.getTask(manager.getTasksList(), 1);


        manager.newTask(taskName2, task2Description);
        Task test2 = manager.getTask(manager.getTasksList(), 2);


        System.out.println("Список задач до удаления: \n");
        System.out.println(manager.getTasksList());

        System.out.println("Список эпиков до удаления из них задач: \n");

        String epicName1 = "Эпик №1";
        String epic1Description = "Здесь может быть описание эпика, но для этого нужно что-то придумать";
        manager.createEpic(epicName1, epic1Description);

        String epic1SubTask1 = "Подзадача №1 в первом эпике";
        String epic1SubTask1Description = "Здесь может быть описание подзадачи первого эпика, " +
                "но для этого нужно что-то придумать";
        String epic1SubTask2 = "Подзадача №2 во первом эпике";
        String epic1SubTask2Description = "Здесь может быть описание подзадачи первого эпика, " +
                "но для этого нужно что-то придумать";

        manager.createSubTask(epic1SubTask1, epic1SubTask1Description, 3);
        manager.createSubTask(epic1SubTask2, epic1SubTask2Description, 3);


        String epicName2 = "Эпик №2";
        String epic2Description = "Здесь может быть описание еще одного эпика, но для этого нужно что-то придумать";

        manager.createEpic(epicName2, epic2Description);

        String epic2SubTask = "Подзадача №1 во втором эпике";
        String epic2SubTaskDescription = "Здесь может быть описание подзадачи второго эпика, " +
                "но для этого тоже нужно что-то придумать";

        manager.createSubTask(epic2SubTask, epic2SubTaskDescription, 6);


//      Печатаем список подзадач в двух эпиках
        System.out.println(manager.getTask(manager.getEpicsList(), 3));
        System.out.println(manager.getTask(manager.getSubTasksList(), 4));
        System.out.println(manager.getTask(manager.getSubTasksList(), 5));
        System.out.println(manager.getTask(manager.getSubTasksList(), 7));



        System.out.println("Удаляем вторую задачу и пробуем вывести ее на печать");
        manager.removeTask(manager.getTasksList(), 2);
        System.out.println(manager.getTask(manager.getTasksList(), 2));

        System.out.println("Изменяем первую подзадачу из первого эпика и пробуем вывести ее на печать\n");
        manager.getTask(manager.getSubTasksList(), 4).updateTask("НОВОЕ_НАЗВАНИЕ",
                "Здесь тоже могло быть что-то новое", "IN_PROGRESS");


        System.out.println(manager.getTask(manager.getSubTasksList(), 4));

        manager.getEpicsList().get(3).setEpicStatus(manager.getSubTasksList());

        System.out.println("Првоеряем новый статус эпика");
        System.out.println(manager.getTask(manager.getEpicsList(), 3).getStatus());

        System.out.println("\nУдаляем второй эпик и удостоверимся, что его нет");
        manager.removeTask(manager.getEpicsList(), 3);
        System.out.println(manager.getTask(manager.getEpicsList(), 3));









    }
}
