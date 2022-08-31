package taskmanager.HttpServer;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import taskmanager.Manager.Exceptions.TasksTimeIntersectionException;
import taskmanager.Manager.Managers.FileBackedTasksManager;
import taskmanager.Manager.Managers.Managers;
import taskmanager.Manager.Managers.TaskManager;
import taskmanager.TaskTypes.Epic;
import taskmanager.TaskTypes.Subtask;
import taskmanager.TaskTypes.Task;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class HttpTaskServer {
    private static final int PORT = 8080;
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private static Gson gson = new Gson();
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    HttpServer httpServer;

    private static TaskManager tasksManager;



    public HttpTaskServer() throws IOException {
        tasksManager = new FileBackedTasksManager();
        httpServer = HttpServer.create(new InetSocketAddress(8080), 0);
        httpServer.createContext("/tasks", new TaskHandler());
        httpServer.start();
        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");
    }

    static class TaskHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String method = httpExchange.getRequestMethod();
            URI uri = httpExchange.getRequestURI();
            String path = uri.toString();
            String[] splitPath = path.split("/");
            String typeOfTask = splitPath[2];


            InputStream inputStream = httpExchange.getRequestBody();
            String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);


            switch(method){
                case "GET":
                    if (typeOfTask.contains("id")){
                        int id = Integer.parseInt(splitPath[2].split("=")[1]);
                        getItemById(typeOfTask, id, httpExchange);
                    } else {
                        getItemList(typeOfTask, httpExchange);
                    }
                    break;
                case "POST":
                    getItemAsJson(typeOfTask, body, httpExchange);
                    break;
                case "DELETE":
                    if (typeOfTask.contains("id")) {
                        int id = Integer.parseInt(splitPath[2].split("=")[1]);
                        deleteItemById(typeOfTask, id, httpExchange);
                    }
                    break;
                default: httpExchange.sendResponseHeaders(404, 0);
                    }

            }

        }

    static class TaskAdapter extends TypeAdapter<Task> {

        @Override
        public void write(JsonWriter writer, Task task) throws IOException {
            writer.beginObject();
            writer.name("taskId");
            writer.value(task.getId());
            writer.name("taskName");
            writer.value(task.getName());
            writer.name("description");
            writer.value(task.getDescription());
            writer.name("startTime");
            writer.value(task.getStartTime().toString());
            writer.name("duration");
            writer.value(task.getDuration().toString());
            writer.name("endTime");
            writer.value(task.getEndTime().toString());
            writer.name("status");
            writer.value(task.getStatus().toString());
            writer.name("type");
            writer.value(task.getType().toString());
            writer.endObject();
        }

        @Override
        public Task read(JsonReader reader) throws IOException {
            Task task = new Task("Task", 1, "description");
            return task;
        }
    }

    static class SubtaskAdapter extends TypeAdapter<Subtask> {

        @Override
        public void write(JsonWriter writer, Subtask subtask) throws IOException {
            writer.beginObject();
            writer.name("taskId");
            writer.value(subtask.getId());
            writer.name("taskName");
            writer.value(subtask.getName());
            writer.name("description");
            writer.value(subtask.getDescription());
            writer.name("startTime");
            writer.value(subtask.getStartTime().toString());
            writer.name("duration");
            writer.value(subtask.getDuration().toString());
            writer.name("endTime");
            writer.value(subtask.getEndTime().toString());
            writer.name("status");
            writer.value(subtask.getStatus().toString());
            writer.name("type");
            writer.value(subtask.getType().toString());
            writer.name("hasEpic");
            writer.value(subtask.hasEpicOrNo());
            writer.name("epicId");
            writer.value(subtask.getEpicIdOfSubtask());
            writer.endObject();
        }


        @Override
        public Subtask read(JsonReader reader) throws IOException {
            Subtask subtask = new Subtask("Subtask", 3, "description", 2);
            return subtask;
        }
    }


    static class EpicAdapter extends TypeAdapter<Epic> {
        @Override
        public void write(JsonWriter writer, Epic epic) throws IOException {
            writer.beginObject();
            writer.name("taskId");
            writer.value(epic.getId());
            writer.name("taskName");
            writer.value(epic.getName());
            writer.name("description");
            writer.value(epic.getDescription());
            writer.name("startTime");
            writer.value(epic.getStartTime().toString());
            writer.name("duration");
            writer.value(epic.getDuration().toString());
            writer.name("endTime");
            writer.value(epic.getEndTime().toString());
            writer.name("status");
            writer.value(epic.getStatus().toString());
            writer.name("type");
            writer.value(epic.getType().toString());
            writer.name("subTasksInEpic");
            writer.value(epic.getSubTasksInEpic().toString());
            writer.endObject();
        }


        @Override
        public Epic read(JsonReader reader) throws IOException {
            Epic epic = new Epic("Epic", 3, "description");
            return epic;
        }
    }

    public static void taskToJson(int taskId, HttpExchange httpExchange) throws IOException {
        Task task = tasksManager.getTask(taskId);
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Task.class, new TaskAdapter());
        builder.serializeNulls();
        Gson gson = builder.create();
        String json = gson.toJson(task);
        try (OutputStream os = httpExchange.getResponseBody()) {
            httpExchange.sendResponseHeaders(200, 0);
            os.write(json.getBytes());
        }
    }

    public static void subtaskToJson(int subtaskId, HttpExchange httpExchange) throws IOException {
        Subtask subtask = (Subtask) tasksManager.getTask(subtaskId);
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Subtask.class, new SubtaskAdapter());
        builder.serializeNulls();
        Gson gson = builder.create();
        String json = gson.toJson(subtask);
        try (OutputStream os = httpExchange.getResponseBody()) {
            httpExchange.sendResponseHeaders(200, 0);
            os.write(json.getBytes());
        }
    }

    public static void epicToJson(int epicId, HttpExchange httpExchange) throws IOException {
        Epic epic = (Epic) tasksManager.getTask(epicId);
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Epic.class, new EpicAdapter());
        builder.serializeNulls();
        Gson gson = builder.create();
        String json = gson.toJson(epic);
        try (OutputStream os = httpExchange.getResponseBody()) {
            httpExchange.sendResponseHeaders(200, 0);
            os.write(json.getBytes());
        }
    }



    public static void taskListToJson(HttpExchange httpExchange) throws IOException {
        List<Integer> tasksList = tasksManager.getTasksList();
        try (OutputStream os = httpExchange.getResponseBody()) {
            httpExchange.sendResponseHeaders(200, 0);
            os.write(gson.toJson(tasksList).getBytes());
        }
    }

    public static void getSubtaskListAsJson(HttpExchange httpExchange) throws IOException {
        List<Integer> subtasksList = tasksManager.getSubTasksList();
        try (OutputStream os = httpExchange.getResponseBody()) {
            httpExchange.sendResponseHeaders(200, 0);
            os.write(gson.toJson(subtasksList).getBytes());
        }
    }

    public static void getEpicsListAsJson(HttpExchange httpExchange) throws IOException {
        List<Integer> epicsList = tasksManager.getEpicsList();
        try (OutputStream os = httpExchange.getResponseBody()) {
            httpExchange.sendResponseHeaders(200, 0);
            os.write(gson.toJson(epicsList).getBytes());
        }
    }

    public static void taskFromJson(String body, HttpExchange httpExchange) throws TasksTimeIntersectionException, IOException {

        JsonElement jsonElement = JsonParser.parseString(body);
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        String taskName = jsonObject.get("taskName").getAsString();
        String description = jsonObject.get("description").getAsString();
        LocalDateTime startTime = LocalDateTime.parse(jsonObject.get("startTime").getAsString(), formatter);
        Duration duration = Duration.parse(jsonObject.get("duration").getAsString());
        int taskId = tasksManager.addTask(taskName, description);

        try {
            tasksManager.setTime(tasksManager.getTask(taskId), startTime, duration);
            try (OutputStream os = httpExchange.getResponseBody()) {
                httpExchange.sendResponseHeaders(201, 0);
                os.write("Task is created".getBytes());
            }
        } catch (TasksTimeIntersectionException e){
            tasksManager.removeTask(taskId);
            try (OutputStream os = httpExchange.getResponseBody()) {
                httpExchange.sendResponseHeaders(400, 0);
                os.write(e.getMessage().getBytes());
            }
        }

    }

    public static void subtaskFromJson(String body, HttpExchange httpExchange) throws TasksTimeIntersectionException, IOException {

        JsonElement jsonElement = JsonParser.parseString(body);
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        String taskName = jsonObject.get("taskName").getAsString();
        String description = jsonObject.get("description").getAsString();
        LocalDateTime startTime = LocalDateTime.parse(jsonObject.get("startTime").getAsString(), formatter);
        Duration duration = Duration.parse(jsonObject.get("duration").getAsString());
        int epicId = jsonObject.get("epicId").getAsInt();
        int subtaskId = tasksManager.addSubTask(taskName, description, epicId);

        try {
            tasksManager.setTime(tasksManager.getTask(subtaskId), startTime, duration);
            tasksManager.setTime(tasksManager.getTask(epicId), startTime, duration);
            try (OutputStream os = httpExchange.getResponseBody()) {
                httpExchange.sendResponseHeaders(201, 0);
                os.write("Subtask is created".getBytes());
            }
        } catch (TasksTimeIntersectionException e){
            tasksManager.removeSubTask(subtaskId);
            try (OutputStream os = httpExchange.getResponseBody()) {
                httpExchange.sendResponseHeaders(400, 0);
                os.write(e.getMessage().getBytes());
            }
        }

    }

    public static void epicFromJson(String body, HttpExchange httpExchange) throws TasksTimeIntersectionException, IOException {

        JsonElement jsonElement = JsonParser.parseString(body);
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        String epicName = jsonObject.get("taskName").getAsString();
        String description = jsonObject.get("description").getAsString();
        int epicId = tasksManager.addEpic(epicName, description);

        try {
            try (OutputStream os = httpExchange.getResponseBody()) {
                httpExchange.sendResponseHeaders(201, 0);
                os.write("Epic is created".getBytes());
            }
        } catch (TasksTimeIntersectionException e){
            try (OutputStream os = httpExchange.getResponseBody()) {
                httpExchange.sendResponseHeaders(400, 0);
                os.write(e.getMessage().getBytes());
            }
        }

    }

    public static void getItemById(String itemName, int id, HttpExchange httpExchange) throws IOException {
        if (itemName.contains("task") && !itemName.contains("sub")) taskToJson(id, httpExchange);
        if (itemName.contains("subtask")) subtaskToJson(id, httpExchange);
        if (itemName.contains("epic")) epicToJson(id, httpExchange);
    }

    public static void getItemList(String typeOfTask, HttpExchange httpExchange) throws IOException {
        if (typeOfTask.equals("task")) taskListToJson(httpExchange);
        if (typeOfTask.equals("subtask")) getSubtaskListAsJson(httpExchange);
        if (typeOfTask.equals("epic")) getEpicsListAsJson(httpExchange);

    }

    public static void getItemAsJson(String typeOfTask, String body, HttpExchange httpExchange) throws IOException {
        if (typeOfTask.equals("task")) taskFromJson(body, httpExchange);
        if (typeOfTask.equals("subtask")) subtaskFromJson(body, httpExchange);
        if (typeOfTask.equals("epic")) epicFromJson(body, httpExchange);

    }

    public static void deleteItemById(String typeOfTask, int id, HttpExchange httpExchange) throws IOException {
            if (typeOfTask.contains("task") && !typeOfTask.contains("sub")) {
                tasksManager.removeTask(id);
                try (OutputStream os = httpExchange.getResponseBody()) {
                    httpExchange.sendResponseHeaders(201, 0);
                    os.write("Task is deleted".getBytes());
                }
            }
            else if (typeOfTask.contains("subtask")) {
                tasksManager.removeSubTask(id);
                try (OutputStream os = httpExchange.getResponseBody()) {
                    httpExchange.sendResponseHeaders(201, 0);
                    os.write("Subtask is deleted".getBytes());
                }
            }
            else if (typeOfTask.contains("epic")) {
                tasksManager.removeEpic(id);
                try (OutputStream os = httpExchange.getResponseBody()) {
                    httpExchange.sendResponseHeaders(201, 0);
                    os.write("Epic is deleted".getBytes());
                }
            } else {
                httpExchange.sendResponseHeaders(400, 0);
            }

    }

}


