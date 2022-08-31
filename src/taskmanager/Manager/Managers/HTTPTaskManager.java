package taskmanager.Manager.Managers;

import com.google.gson.Gson;
import taskmanager.HttpServer.KVTaskClient;
import taskmanager.Manager.Exceptions.ManagerSaveException;
import taskmanager.TaskTypes.Task;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.NoSuchElementException;

public class HTTPTaskManager extends FileBackedTasksManager{
    KVTaskClient kvTaskClient;

    public HTTPTaskManager(String url) throws IOException, InterruptedException {
        kvTaskClient = new KVTaskClient(url);
    }

    public String load(String key) throws NoSuchElementException, IOException, InterruptedException {
        return kvTaskClient.load(key);

    }

    @Override
    public void save() throws IOException, InterruptedException {
        StringBuilder stringToSave = new StringBuilder();
        // id,type,name,status,description,epic + startTime, duration, endTime

        for (int taskId : getTasksList()) {
            Task task = recoverTask(taskId);
            stringToSave.append(convertTaskToString(task) + "\n");
        }
        for (int taskId : getSubTasksList()) {
            Task task = recoverTask(taskId);
            stringToSave.append(convertTaskToString(task) + "\n");
        }
        for (int taskId : getEpicsList()) {
            Task task = recoverTask(taskId);
            stringToSave.append(convertTaskToString(task) + "\n");
        }

        stringToSave.append("HISTORY ");
        stringToSave.append(getHistory());

        kvTaskClient.put(String.valueOf(1), stringToSave.toString());
    }

}
