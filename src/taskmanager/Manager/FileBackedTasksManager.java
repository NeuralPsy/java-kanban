package taskmanager.Manager;

import taskmanager.TaskTypes.*;

import java.io.*;
import java.util.*;

public class FileBackedTasksManager extends InMemoryTasksManager {

    private File file;
    private static int newTaskId = 0;
    private Writer backedTasks;

    private InMemoryHistoryManager historyManager = new InMemoryHistoryManager();

    public static void main(String[] args) throws IOException {
        File file = new File("src/taskmanager/Manager/BackedData/FileBackedTasksManager.csv");
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);

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

        FileBackedTasksManager fileBackedTasksManager2 = new FileBackedTasksManager(file);

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

    public FileBackedTasksManager(File autoSaveFile) throws IOException, RuntimeException {

        try {
            this.file = autoSaveFile;
            this.backedTasks = new FileWriter(this.file, true);
            Scanner reader = new Scanner(file);
            loadHistory(reader);
            System.out.println("Задачи загружены из файла автосохранения");
        } catch (NoSuchElementException exception){
            System.out.println("Список задач пуст. Создайте задачи и они появятся в файле автосохранения");
        }
    }

    private void loadHistory(Scanner reader) {

        while (reader.hasNextLine()){
            String line = reader.nextLine();

            if (line.contains(":")){
                String[] arrayToSplit = line.split(":");

                String stringHistory = arrayToSplit[1];
                for (String x : stringHistory.split(",")) {
                    addToHistory(Integer.parseInt(x));
                }
                System.out.println("История просмотров загружена");
                break;
            }

            Task task = fromString(line);
            addTaskToMap(task);
        }
        reader.close();
    }



    public String toString(Task task) throws IOException {
        String taskType = task.getType().name();

        // id,type,name,status,description,epic - чтобы не забыть

        String taskString = task.getTaskId()+","+
                    taskType+","+
                    task.getTaskName()+","+
                    task.getStatus()+","+
                    task.getDescription()+",";

            if (taskType.equals("SUBTASK")){
                taskString += ((Subtask) task).getEpicId();
            }
            return taskString;

    }



    public Task fromString(String stringTask){
        String[] taskArray = stringTask.split(",");
        Task task = null;

        if (taskArray[1].equals("TASK")){
            task = new Task(taskArray[2], Integer.parseInt(taskArray[0]), taskArray[4]);
            // TaskStatus status = TaskStatus.valueOf(taskArray[3]);
            task.setStatus(taskArray[3]);
            addTaskToMap(task);
        }
        if (taskArray[1].equals("EPIC")){
            task = new Epic(taskArray[2], Integer.parseInt(taskArray[0]), taskArray[4]);
            //TaskStatus status = TaskStatus.valueOf(taskArray[3]);
            task.setStatus(taskArray[3]);

            addTaskToMap(task);
        }
        if (taskArray[1].equals("SUBTASK")){
            task = new Subtask(taskArray[2], Integer.parseInt(taskArray[0]),
                    taskArray[4], Integer.parseInt(taskArray[5]));

           // TaskStatus status = TaskStatus.valueOf(taskArray[3]);

            task.setStatus(taskArray[3]);
            addTaskToMap(task);
        }

        if (task.getTaskId() > newTaskId) newTaskId = task.getTaskId(); // для того, чтобы можно было продолжить
                                                                        // присваивать новые айди
        return task;

    }


    static FileBackedTasksManager loadFromFile(File file) throws IOException {
        return new FileBackedTasksManager(file);
    }


    void save() throws IOException {
        backedTasks = new FileWriter(this.file);
       // id,type,name,status,description,epic

        for (int taskId : getTasksList()) {
            Task task = recoverTask(taskId);
            backedTasks.write(toString(task)+"\n");
        }
        for (int taskId : getSubTasksList()) {
            Task task = recoverTask(taskId);
            backedTasks.write(toString(task)+"\n");
        }
        for (int taskId : getEpicsList()) {
            Task task = recoverTask(taskId);
            backedTasks.write(toString(task)+"\n");
        }

        backedTasks.write("HISTORY:");
        backedTasks.write(getHistory());
        backedTasks.close();
    }


}
