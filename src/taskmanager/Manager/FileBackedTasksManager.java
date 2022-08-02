package taskmanager.Manager;

import taskmanager.TaskTypes.*;

import java.io.*;

public class FileBackedTasksManager extends InMemoryTasksManager {

    // При сохранении методом save(), значения из всех хешмапов должны сохраняться в файл
    // При открытии менеджера данные из файла сохранения должны загружаться в соответствующие хешмапы

    private Writer backedTasks;

    public FileBackedTasksManager(String autoSaveFile) throws IOException {
        try {
            this.backedTasks = new FileWriter(autoSaveFile, true);
        } catch (IOException exception){
            System.out.println("ERROR occurred: " + exception.getMessage());
        }
    }


    public String toString(Task task) throws IOException {
        String taskType = String.valueOf(task.getClass()).toUpperCase();

        String taskString = task.getTaskId()+","+
                    TaskTypes.valueOf(taskType)+","+
                    task.getTaskName()+","+
                    task.getStatus()+","+
                    task.getDescription()+",";

            if (taskType.equals("SUBTASK")){
                taskString += ((Subtask) task).getEpicId();
            }

            backedTasks.write(taskString+"\n");

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
