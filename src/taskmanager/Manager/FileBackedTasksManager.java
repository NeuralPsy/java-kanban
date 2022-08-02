package taskmanager.Manager;

import taskmanager.TaskTypes.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileBackedTasksManager extends InMemoryTasksManager {

    private static int newTaskId = 0;


    public String toString(Task task) throws IOException {
        Writer backedTasks = new FileWriter("FileBackedTasksManager.csv", true);
        String taskString = task.getTaskId()+","
                +TaskTypes.TASK+","
                +task.getTaskName()+","+
                task.getStatus()+","
                +task.getDescription()+",";

        backedTasks.write(taskString+"\n");
        backedTasks.close();

        return taskString;
    }

    public String toString(Subtask subtask) throws IOException {
        Writer backedTasks = new FileWriter("FileBackedTasksManager.csv", true);

        String subtaskString = subtask.getTaskId()+","
                +TaskTypes.SUBTASK+","
                +subtask.getTaskName()+","+
                subtask.getStatus()+","
                +subtask.getDescription()+","+
                subtask.getEpicId();

        backedTasks.write(subtaskString+"\n");
        backedTasks.close();

        return subtaskString;
    }

    public String toString(Epic epic) throws IOException {
        Writer backedTasks = new FileWriter("FileBackedTasksManager.csv", true);

        String epicString = epic.getTaskId()+","
                +TaskTypes.EPIC+","
                +epic.getTaskName()+","+
                epic.getStatus()+","
                +epic.getDescription()+",";

        backedTasks.write(epicString+"\n");
        backedTasks.close();

        return epicString;
    }

    public Task fromString(String stringTask){
        String[] taskArray = stringTask.split(",");
        if (taskArray[1].equals("TASK")){
            Task task = new Task(taskArray[2], Integer.parseInt(taskArray[0]), taskArray[4]);
            task.setStatus(TaskStatus.valueOf(taskArray[1]));
            return task;
        }

    }


    void save(){

    }

}
