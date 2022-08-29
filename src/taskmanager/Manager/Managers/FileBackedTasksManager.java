package taskmanager.Manager.Managers;

import taskmanager.Manager.Exceptions.ManagerSaveException;
import taskmanager.TaskTypes.*;

import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class FileBackedTasksManager extends InMemoryTasksManager {

    private File file = new File("src/taskmanager/Manager/BackedData/DefaultFileBackedTasksManager.csv");
    private static int newTaskId = 0;
    private Writer backedTasks;

    public FileBackedTasksManager(){

    }

    public FileBackedTasksManager(File autoSaveFile) {

        try {
            this.file = autoSaveFile;
            this.backedTasks = new FileWriter(this.file, true);
            Scanner reader = new Scanner(file);
            loadHistory(reader);
        } catch (NoSuchElementException e){
            System.out.println(e.getMessage());
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    private void loadHistory(Scanner reader) {

        while (reader.hasNextLine()){
            String line = reader.nextLine();

            if (line.contains("HISTORY")){
                String[] arrayToSplit = line.split(" ");

                String stringHistory = arrayToSplit[1];
                for (String x : stringHistory.split(",")) {
                    addToHistory(Integer.parseInt(x));
                }
                break;
            }

            Task task = fromString(line);
            addTaskToMap(task);
        }
        reader.close();
    }

    public String convertTaskToString(Task task) {
        String taskType = task.getType().name();

        // id,type,name,status,description,+ startTime,duration,endTime,epic

        String taskString = task.getId()+","+
                    taskType+","+
                    task.getName()+","+
                    task.getStatusAsString()+","+
                    task.getDescription()+","+task.getStartTime().format(getFormatter()) +","+
                    task.getDuration()+","+task.getEndTime().format(getFormatter())+",";


            if (taskType.equals("SUBTASK")){
                taskString += ((Subtask) task).getEpicId();
            }
            return taskString;

    }

    private void parseString(Task task, String[] strings){
        if (strings.length == 8){
            task.setStatus(TaskStatus.valueOf(strings[3]));
            LocalDateTime startTime = LocalDateTime.parse(strings[5], getFormatter());
            Duration duration = Duration.parse(strings[6]);

            setTime(task, startTime, duration);
        }
        if (strings.length == 9){
            task.setStatus(TaskStatus.valueOf(strings[3]));
            LocalDateTime startTime = LocalDateTime.parse(strings[5], getFormatter());
            Duration duration = Duration.parse(strings[6]);
            setTime(task, startTime, duration);
        }
    }


    public Task fromString(String stringTask){
        String[] taskArray = stringTask.split(",");
        Task task = null;

        // id,type,name,status,description, + startTime,duration,endTime,epic

        try {
            if (taskArray[1].equals("TASK")) {
                task = new Task(taskArray[2], Integer.parseInt(taskArray[0]), taskArray[4]);
                parseString(task, taskArray);
                addTaskToMap(task);
            }
            if (taskArray[1].equals("EPIC")) {
                task = new Epic(taskArray[2], Integer.parseInt(taskArray[0]), taskArray[4]);
                parseString(task, taskArray);

                addTaskToMap(task);
            }
            if (taskArray[1].equals("SUBTASK")) {
                task = new Subtask(taskArray[2], Integer.parseInt(taskArray[0]),
                        taskArray[4], Integer.parseInt(taskArray[8]));
                parseString(task, taskArray);

                addTaskToMap(task);
            }

            if (task.getId() > newTaskId) newTaskId = task.getId();

        } catch (NullPointerException e){
            System.out.println(e.getMessage());
        } return task;

    }


    public static FileBackedTasksManager loadFromFile(File file) throws NoSuchElementException{
        FileBackedTasksManager tasksManager = null;
        try {
            tasksManager = new FileBackedTasksManager(file);
        } catch (NoSuchElementException e){
            System.out.println(e.getMessage());
        } return tasksManager;

    }


    public void save() throws ManagerSaveException {
        try {
            backedTasks = new FileWriter(this.file);
            // id,type,name,status,description,epic + startTime, duration, endTime

            for (int taskId : getTasksList()) {
                Task task = recoverTask(taskId);
                backedTasks.write(convertTaskToString(task) + "\n");
            }
            for (int taskId : getSubTasksList()) {
                Task task = recoverTask(taskId);
                backedTasks.write(convertTaskToString(task) + "\n");
            }
            for (int taskId : getEpicsList()) {
                Task task = recoverTask(taskId);
                backedTasks.write(convertTaskToString(task) + "\n");
            }

            backedTasks.write("HISTORY ");
            backedTasks.write(getHistory());
            backedTasks.close();
        } catch (IOException e){
            throw new ManagerSaveException();
        }
    }


}
