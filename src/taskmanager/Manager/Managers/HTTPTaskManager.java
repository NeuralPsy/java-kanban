package taskmanager.Manager.Managers;

import taskmanager.HttpServer.HttpTaskServer;
import taskmanager.HttpServer.KVServer;
import taskmanager.HttpServer.KVTaskClient;
import taskmanager.TaskTypes.Task;

import java.io.IOException;
import java.util.NoSuchElementException;


public class HTTPTaskManager extends FileBackedTasksManager{
    KVTaskClient kvTaskClient;
    private static int key = 0;
    KVServer kvServer;

    public HTTPTaskManager() throws IOException, InterruptedException {
        kvServer = new KVServer();
        kvServer.start();
        kvTaskClient = new KVTaskClient();
        key++;
    }

    public void stopKV(){
        kvServer.stop();
    }

    public String load(String key) throws NoSuchElementException, IOException, InterruptedException {
        return kvTaskClient.load(key);

    }

    public void save(String key) throws IOException, InterruptedException {
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

        kvTaskClient.put(key, stringToSave.toString());
    }

    public HTTPTaskManager(String backedTasks) throws NoSuchElementException, IOException, InterruptedException {
        new KVServer().start();
        kvTaskClient = new KVTaskClient();
        //httpTaskServer = new HttpTaskServer();
        key++;
        String[] backedTasksSplit = backedTasks.split("\n");
        loadHistory(backedTasksSplit);
        }



    private void loadHistory(String[] backedTasks) {

        for (String t : backedTasks) {
            if (t.contains("HISTORY")) {
                String[] arrayToSplit = t.split(" ");
                try {
                    String stringHistory = arrayToSplit[1];
                    for (String x : stringHistory.split(",")) {
                        addToHistory(Integer.parseInt(x));
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    break;
                }
                break;
            }
            Task task = fromString(t);
            addTaskToMap(task);
        }
    }
}


