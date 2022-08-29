import taskmanager.Manager.Managers.FileBackedTasksManager;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        File file = new File("src/taskmanager/Manager/BackedData/FileBackedTasksManager.csv");
        FileBackedTasksManager fileBackedTasksManager = FileBackedTasksManager.loadFromFile(file);

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

        System.out.println("Создаем задачи, эпики и подзадачи: ");

        System.out.println(fileBackedTasksManager.addTask(taskName1, task1Description));
        System.out.println(fileBackedTasksManager.addTask(taskName2, task2Description));
        System.out.println(fileBackedTasksManager.addEpic(epic1, epic1Description));
        System.out.println(fileBackedTasksManager.addEpic(epic2, epic2Description));
        System.out.println(fileBackedTasksManager.addSubTask(subTask11, subTask11Description, 3));
        System.out.println(fileBackedTasksManager.addSubTask(subTask12, subTask12Description, 3));
        System.out.println(fileBackedTasksManager.addSubTask(subTask13, subTask13Description, 3));

        System.out.println(fileBackedTasksManager.getTask(1));
        System.out.println(fileBackedTasksManager.getTask(2));
        System.out.println(fileBackedTasksManager.getTask(3));
        System.out.println(fileBackedTasksManager.getTask( 4));
        System.out.println(fileBackedTasksManager.getTask(5));
        System.out.println(fileBackedTasksManager.getTask(6));
        System.out.println(fileBackedTasksManager.getTask(7));

        System.out.println("Сохраняем историю в файл");
        fileBackedTasksManager.save();

        System.out.println("Перемешиваем историю просмотров");


        System.out.println(fileBackedTasksManager.getTask(2));
        System.out.println(fileBackedTasksManager.getTask(3));
        System.out.println(fileBackedTasksManager.getTask(1));
        System.out.println(fileBackedTasksManager.getTask(4));
        System.out.println(fileBackedTasksManager.getTask(6));
        System.out.println(fileBackedTasksManager.getTask(5));
        System.out.println(fileBackedTasksManager.getTask(7));
        System.out.println(fileBackedTasksManager.getTask( 1));
        System.out.println(fileBackedTasksManager.getTask(4));

        System.out.println("Сохраняем историю в файл");

        fileBackedTasksManager.save();
        System.out.println("***ИСТОРИЯ ПРОСМОТРОВ В ПЕРВОМ МЕНЕДЖЕРЕ***");

        System.out.println(fileBackedTasksManager.getHistory());

        System.out.println("Создаем второй менеджер");

        FileBackedTasksManager fileBackedTasksManager2 = FileBackedTasksManager.loadFromFile(file);

        System.out.println("Сохраняем историю в файл");
        fileBackedTasksManager2.save();

        System.out.println("***ИСТОРИЯ ПРОСМОТРОВ ВО ВТОРОМ МЕНЕДЖЕРЕ***");
        System.out.println(fileBackedTasksManager2.getHistory());

        System.out.println("***СРАВНИМ ИСТОРИЮ В ДВУХ МЕНЕДЖЕРАХ***");
        System.out.println("Первый: "+ fileBackedTasksManager.getHistory());
        System.out.println("Второй: "+ fileBackedTasksManager2.getHistory());

        System.out.println("Перемешиваем историю просмотров");

        System.out.println(fileBackedTasksManager2.getTask(2));
        System.out.println(fileBackedTasksManager2.getTask(4));
        System.out.println(fileBackedTasksManager2.getTask(1));
        System.out.println(fileBackedTasksManager2.getTask(3));
        System.out.println(fileBackedTasksManager2.getTask(4));
        System.out.println(fileBackedTasksManager2.getTask(7));
        System.out.println(fileBackedTasksManager2.getTask(3));
        System.out.println(fileBackedTasksManager2.getTask(1));

        System.out.println("Сохраняем историю в файл");
        fileBackedTasksManager2.save();


        System.out.println("***СРАВНИМ ИСТОРИЮ В ДВУХ МЕНЕДЖЕРАХ ПОСЛЕ ИЗМЕНЕНИЯ ЕЁ ВО ВТОРОМ***");
        System.out.println("Первый: "+ fileBackedTasksManager.getHistory());
        System.out.println("Второй: "+ fileBackedTasksManager2.getHistory());

    }
}
