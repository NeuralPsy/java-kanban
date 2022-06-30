import taskmanager.History.InMemoryTaskManager;

public class Main {

//    Программа успешно прошла тестирование. Все задачи, эпики и подзадачи в них просматриваются в истории,
//    а так же соблюдается условие ограничения объема памяти истории десятью задачами. Полее поздняя задача удаляется
//    из истории при добавлении новой.

    public static void main(String[] args) {
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();

        String taskName1 = "Задача №1";
        String task1Description = "Описание задачи №1";

        String taskName2 = "Задача №2";
        String task2Description = "Описание задачи №2";

        String taskName3 = "Задача №3";
        String task3Description = "Описание задачи №3";

        String taskName4 = "Задача №4";
        String task4Description = "Описание задачи №4";

        String taskName5 = "Задача №5";
        String task5Description = "Описание задачи №5";

        String epic6 = "Эпик №1";
        String epic6Description = "Описание эпика №1";

        String epic7 = "Эпик №2";
        String epic7Description = "Описание эпика №2";

        String epic8 = "Эпик №3";
        String epic8Description = "Описание эпика №3";

        String epic9 = "Эпик №4";
        String epic9Description = "Описание эпика №4";

        String epic10 = "Эпик №5";
        String epic10Description = "Описание эпика №5";

        String epic11 = "Эпик №6";
        String epic11Description = "Описание эпика №6";

        String subTask12 = "Первая подзадача эпика №4";
        String subTask12Description = "Описание первой подзадачи эпика №4";

        String subTask13 = "Вторая подзадача эпика №4";
        String subTask13Description = "Описание второй подзадачи эпика №4";

        inMemoryTaskManager.addTask(taskName1, task1Description);
        inMemoryTaskManager.addTask(taskName2, task2Description);
        inMemoryTaskManager.addTask(taskName3, task3Description);
        inMemoryTaskManager.addTask(taskName4, task4Description);
        inMemoryTaskManager.addTask(taskName5, task5Description);
        inMemoryTaskManager.addEpic(epic6, epic6Description);
        inMemoryTaskManager.addEpic(epic7, epic7Description);
        inMemoryTaskManager.addEpic(epic8, epic8Description);
        inMemoryTaskManager.addEpic(epic9, epic9Description);
        inMemoryTaskManager.addEpic(epic10, epic10Description);
        inMemoryTaskManager.addEpic(epic11, epic11Description);
        inMemoryTaskManager.addSubTask(subTask12, subTask12Description, 11);
        inMemoryTaskManager.addSubTask(subTask13, subTask13Description, 11);


        System.out.println("\nТестируем вывод истории просмотренных задач");
        System.out.println("*********ИСТОРИЯ ПРОСМОТРЕННЫХ ЗАДАЧ**********");
        System.out.println(InMemoryTaskManager.getHistory());
        System.out.println("**********************************************");

        System.out.println(inMemoryTaskManager.getTask(inMemoryTaskManager.getTasksList(), 1));
        System.out.println(inMemoryTaskManager.getTask(inMemoryTaskManager.getTasksList(), 2));

        System.out.println("\nТестируем вывод истории просмотренных задач");
        System.out.println("*********ИСТОРИЯ ПРОСМОТРЕННЫХ ЗАДАЧ**********");
        System.out.println(InMemoryTaskManager.getHistory());
        System.out.println("**********************************************");

        System.out.println(inMemoryTaskManager.getTask(inMemoryTaskManager.getTasksList(), 3));
        System.out.println(inMemoryTaskManager.getTask(inMemoryTaskManager.getTasksList(), 4));
        System.out.println(inMemoryTaskManager.getTask(inMemoryTaskManager.getTasksList(), 5));

        System.out.println("\nТестируем вывод истории просмотренных задач");
        System.out.println("*********ИСТОРИЯ ПРОСМОТРЕННЫХ ЗАДАЧ**********");
        System.out.println(InMemoryTaskManager.getHistory());
        System.out.println("**********************************************");

        System.out.println(inMemoryTaskManager.getTask(inMemoryTaskManager.getEpicsList(), 6));
        System.out.println(inMemoryTaskManager.getTask(inMemoryTaskManager.getEpicsList(), 7));
        System.out.println(inMemoryTaskManager.getTask(inMemoryTaskManager.getEpicsList(), 8));

        System.out.println("\nТестируем вывод истории просмотренных задач");
        System.out.println("*********ИСТОРИЯ ПРОСМОТРЕННЫХ ЗАДАЧ**********");
        System.out.println(InMemoryTaskManager.getHistory());
        System.out.println("**********************************************");

        System.out.println(inMemoryTaskManager.getTask(inMemoryTaskManager.getEpicsList(), 9));
        System.out.println(inMemoryTaskManager.getTask(inMemoryTaskManager.getEpicsList(), 10));
        System.out.println(inMemoryTaskManager.getTask(inMemoryTaskManager.getEpicsList(), 11));
        System.out.println(inMemoryTaskManager.getTask(inMemoryTaskManager.getSubTasksList(), 12));

        System.out.println("\nТестируем вывод истории просмотренных задач");
        System.out.println("*********ИСТОРИЯ ПРОСМОТРЕННЫХ ЗАДАЧ**********");
        System.out.println(InMemoryTaskManager.getHistory());
        System.out.println("**********************************************");


    }
}
