import taskmanager.Manager.InMemoryTaskManager;

import static taskmanager.Manager.InMemoryTaskManager.*;

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

        String epic1 = "Эпик №1";
        String epic1Description = "Описание эпика №1";

        String epic2 = "Эпик №2";
        String epic2Description = "Описание эпика №2 без подзадач";

        String subTask11 = "Первая подзадача эпика №1";
        String subTask11Description = "Описание первой подзадачи эпика №1";

        String subTask12 = "Вторая подзадача эпика №1";
        String subTask12Description = "Описание второй подзадачи эпика №4";

        String subTask13 = "Вторая подзадача эпика №1";
        String subTask13Description = "Описание второй подзадачи эпика №4";

        inMemoryTaskManager.addTask(taskName1, task1Description);
        inMemoryTaskManager.addTask(taskName2, task2Description);
        inMemoryTaskManager.addEpic(epic1, epic1Description);
        inMemoryTaskManager.addEpic(epic2, epic2Description);
        inMemoryTaskManager.addSubTask(subTask11, subTask11Description, 3);
        inMemoryTaskManager.addSubTask(subTask12, subTask12Description, 3);
        inMemoryTaskManager.addSubTask(subTask13, subTask13Description, 3);


        System.out.println("\nТестируем вывод истории просмотренных задач");

        System.out.println(inMemoryTaskManager.getTask(1));
        System.out.println(inMemoryTaskManager.getTask(2));
        System.out.println(inMemoryTaskManager.getTask(3));
        System.out.println(inMemoryTaskManager.getTask( 4));
        System.out.println(inMemoryTaskManager.getTask(5));
        System.out.println(inMemoryTaskManager.getTask(6));
        System.out.println(inMemoryTaskManager.getTask(7));

        System.out.println("\nТестируем вывод истории просмотренных задач");
        System.out.println("*********ИСТОРИЯ ПРОСМОТРЕННЫХ ЗАДАЧ**********");
        System.out.println(InMemoryTaskManager.getHistory());
        System.out.println("**********************************************");

        System.out.println(inMemoryTaskManager.getTask(2));
        System.out.println(inMemoryTaskManager.getTask(3));
        System.out.println(inMemoryTaskManager.getTask(1));
        System.out.println(inMemoryTaskManager.getTask(4));
        System.out.println(inMemoryTaskManager.getTask(6));
        System.out.println(inMemoryTaskManager.getTask(5));
        System.out.println(inMemoryTaskManager.getTask(7));
        System.out.println(inMemoryTaskManager.getTask( 1));
        System.out.println(inMemoryTaskManager.getTask(4));

        System.out.println("\nТестируем вывод истории просмотренных задач");
        System.out.println("*********ИСТОРИЯ ПРОСМОТРЕННЫХ ЗАДАЧ**********");
        System.out.println(InMemoryTaskManager.getHistory());
        System.out.println("**********************************************");




    }
}
