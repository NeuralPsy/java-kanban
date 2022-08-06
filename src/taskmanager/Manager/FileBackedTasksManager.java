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


    }

    public FileBackedTasksManager(){

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


    public void save() throws IOException {
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
