package taskmanager.Manager;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import taskmanager.Manager.Exceptions.TasksIntersectionException;
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

    private static TaskManager tasksManager;



    public static void main(String[] args) throws IOException {
        tasksManager = Managers.getDefault();
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(8080), 0);
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
                        int taskId = Integer.parseInt(splitPath[2].split("=")[1]);
                        getTaskAsJson(taskId, httpExchange);
                        break;
                    }
                    if (typeOfTask.equals("task")){
                        getTaskListAsJson(httpExchange);
                        break;
                    }
                    if (typeOfTask.equals("subtask")){
                        getSubtaskListAsJson(httpExchange);
                        break;
                    }
                    if (typeOfTask.equals("epic")){
                        getEpicsListAsJson(httpExchange);
                        break;
                    }




                case "POST":
                    if (typeOfTask.equals("task")){
                        getTaskFromJson(body, httpExchange);
                        break;
                    }


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


    public static void getTaskAsJson(int taskId, HttpExchange httpExchange) throws IOException {
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



    public static void getTaskListAsJson(HttpExchange httpExchange) throws IOException {
        List<Integer> tasksList = tasksManager.getTasksList();

        try (OutputStream os = httpExchange.getResponseBody()) {
            httpExchange.sendResponseHeaders(200, 0);
            os.write(gson.toJson(tasksList).getBytes());
        }
    }

    public static void getSubtaskListAsJson(HttpExchange httpExchange) throws IOException {

        try (OutputStream os = httpExchange.getResponseBody()) {
            httpExchange.sendResponseHeaders(200, 0);
            os.write(gson.toJson(tasksManager.getSubTasksList()).getBytes());
            return;
        }
    }

    public static void getEpicsListAsJson(HttpExchange httpExchange) throws IOException {
        try (OutputStream os = httpExchange.getResponseBody()) {
            httpExchange.sendResponseHeaders(200, 0);
            os.write(gson.toJson(tasksManager.getEpicsList()).getBytes());
            return;
        }
    }

    public static void getTaskFromJson(String body, HttpExchange httpExchange) throws TasksIntersectionException, IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

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
        } catch (TasksIntersectionException e){
            tasksManager.removeTask(taskId);
            try (OutputStream os = httpExchange.getResponseBody()) {
                httpExchange.sendResponseHeaders(400, 0);
                os.write(e.getMessage().getBytes());
            }
        }

    }

    public HttpTaskServer() throws IOException{
    }

}

