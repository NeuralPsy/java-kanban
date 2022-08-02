package taskmanager.Manager;

import taskmanager.TaskTypes.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileBackedTasksManager extends InMemoryTasksManager {

    private static int newTaskId = 0;


    public String toString(Task task) throws IOException {
        String taskType = String.valueOf(task.getClass()).toUpperCase();
        Writer backedTasks = new FileWriter("FileBackedTasksManager.csv", true);

            String taskString = task.getTaskId()+","
                    +TaskTypes.valueOf(taskType)+","
                    +task.getTaskName()+","+
                    task.getStatus()+","
                    +task.getDescription()+",";

            if (taskType.equals("SUBTASK")){
                taskString += ((Subtask) task).getEpicId();
            }

            backedTasks.write(taskString+"\n");
            backedTasks.close();

            return taskString;


    }


    public Task fromString(String stringTask){
        String[] taskArray = stringTask.split(",");

        if (taskArray[1].equals("TASK")){
            Task task = new Task(taskArray[2], Integer.parseInt(taskArray[0]), taskArray[4]);
            task.setStatus(TaskStatus.valueOf(taskArray[1]));
            return task;
        }
        if (taskArray[1].equals("EPIC")){
            Epic epic = new Epic(taskArray[2], Integer.parseInt(taskArray[0]), taskArray[4]);
            epic.setStatus(TaskStatus.valueOf(taskArray[1]));
            return epic;
        }
        if (taskArray[1].equals("SUBTASK")){
            Subtask subtask = new Subtask(taskArray[2], Integer.parseInt(taskArray[0]),
                    taskArray[4], Integer.parseInt(taskArray[5]));
            subtask.setStatus(TaskStatus.valueOf(taskArray[1]));
            return subtask;
        }

    }


    void save(){

    }

}
