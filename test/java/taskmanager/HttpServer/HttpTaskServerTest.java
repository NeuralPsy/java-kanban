package taskmanager.HttpServer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import taskmanager.Manager.Exceptions.EpicDoesNotExistWhenAddingSubtaskException;
import taskmanager.TaskTypes.TaskTypes;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;

class HttpTaskServerTest {
    HttpTaskServer httpTaskServer;



    HttpTaskServerTest() throws IOException {
    }

    @BeforeEach
    void beforeEach() throws IOException {
        httpTaskServer = new HttpTaskServer();
    }

    @AfterEach
    void afterEach(){
        httpTaskServer.stop();
    }

    public String addTask(String dateTime) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task");
        String task = "{" +
                "\t\"taskName\" : \"Task\"," +
                "\t\"description\" : \"First task on server\"," +
                "\t\"duration\" : \"PT24H\"," +
                "\t\"startTime\" : \""+dateTime+"\"" +
                "}";
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(task)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    public String addSubtask(String dateTime, String epicId) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/subtask");
        String subtask = "{" +
                "\t\"taskName\" : \"Subtask1\"," +
                "\t\"description\" : \"First subtask on server\"," +
                "\t\"duration\" : \"PT24H\"," +
                "\t\"startTime\" : \""+dateTime+"\"," +
                "\t\"epicId\" : " + epicId +
                "}";
        HttpRequest request2 = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(subtask)).build();
        HttpResponse<String> response = client.send(request2, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }



    @Test
    void shouldAddTask() throws IOException, InterruptedException {
        String answer = addTask("01.10.2022 14:43");
        assertEquals("Task is created", answer);
    }

    @Test
    void shouldAddSubtask() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url0 = URI.create("http://localhost:8080/tasks/epic");
        String epic = "{" +
                "\t\"taskName\" : \"Epic1\"," +
                "\t\"description\" : \"First epic on server\"," +
                "\t\"subTasksInEpic\" : [2]" +
                "}";

        HttpRequest request0 = HttpRequest.newBuilder().uri(url0).POST(HttpRequest.BodyPublishers.ofString(epic)).build();
        HttpResponse<String> response0 = client.send(request0, HttpResponse.BodyHandlers.ofString());

        String epicId = response0.body()
                .substring(response0.body().length()-1);

        String answer = addSubtask("06.08.2022 14:43", epicId);
        assertEquals("Subtask is created", answer);
    }


    @Test
    void shouldAddEpic() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url0 = URI.create("http://localhost:8080/tasks/epic");
        String epic = "{" +
                "\t\"taskName\" : \"Epic1\"," +
                "\t\"description\" : \"First epic on server\"," +
                "\t\"subTasksInEpic\" : [2]" +
                "}";

        HttpRequest request0 = HttpRequest.newBuilder().uri(url0).POST(HttpRequest.BodyPublishers.ofString(epic)).build();
        HttpResponse<String> response0 = client.send(request0, HttpResponse.BodyHandlers.ofString());

        String answer = response0.body();
        String epicId = answer.substring(answer.length()-1);
        assertEquals("Epic is created with id "+epicId, answer);
    }

    @Test
    void shouldGetTasksList() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task");
        String task1 = "{" +
                "\t\"taskName\" : \"Task\"," +
                "\t\"description\" : \"First task on server\"," +
                "\t\"duration\" : \"PT24H\"," +
                "\t\"startTime\" : \"01.10.2022 14:43\"" +
                "}";
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(task1)).build();
        HttpResponse<String> response1 = client.send(request, HttpResponse.BodyHandlers.ofString());
        String task2 = "{" +
                "\t\"taskName\" : \"Task\"," +
                "\t\"description\" : \"First task on server\"," +
                "\t\"duration\" : \"PT24H\"," +
                "\t\"startTime\" : \"03.10.2022 14:43\"" +
                "}";
        HttpRequest request2 = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(task2)).build();
        HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());


        URI url0 = URI.create("http://localhost:8080/tasks/task");

        HttpRequest request0 = HttpRequest.newBuilder().uri(url0).GET().build();
        HttpResponse<String> response0 = client.send(request0, HttpResponse.BodyHandlers.ofString());

        String answer = response0.body();
        assertEquals("[13,14]", answer);
    }

    @Test
    void shouldGetSubtasksList() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url0 = URI.create("http://localhost:8080/tasks/epic");
        String epic = "{" +
                "\t\"taskName\" : \"Epic1\"," +
                "\t\"description\" : \"First epic on server\"," +
                "\t\"subTasksInEpic\" : [2]" +
                "}";

        HttpRequest request0 = HttpRequest.newBuilder().uri(url0).POST(HttpRequest.BodyPublishers.ofString(epic)).build();
        HttpResponse<String> response0 = client.send(request0, HttpResponse.BodyHandlers.ofString());

        String epicId = response0.body().substring(response0.body().length()-1);

        String st1 = addSubtask("03.10.2022 14:43", epicId);
        String st2 = addSubtask("05.10.2022 14:43", epicId);


        URI url3 = URI.create("http://localhost:8080/tasks/subtask");

        HttpRequest request4 = HttpRequest.newBuilder().uri(url3).GET().build();
        HttpResponse<String> response3 = client.send(request4, HttpResponse.BodyHandlers.ofString());

        String answer = response3.body();
        assertEquals("[9,10]", answer);
    }

    @Test
    void getEpicsListAsJson() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        URI url0 = URI.create("http://localhost:8080/tasks/epic");
        String epic = "{" +
                "\t\"taskName\" : \"Epic1\"," +
                "\t\"description\" : \"First epic on server\"," +
                "\t\"subTasksInEpic\" : [2]" +
                "}";

        HttpRequest request0 = HttpRequest.newBuilder().uri(url0).POST(HttpRequest.BodyPublishers.ofString(epic)).build();
        HttpResponse<String> response0 = client.send(request0, HttpResponse.BodyHandlers.ofString());

        URI url1 = URI.create("http://localhost:8080/tasks/epic");
        String epic2 = "{" +
                "\t\"taskName\" : \"Epic1\"," +
                "\t\"description\" : \"First epic on server\"," +
                "\t\"subTasksInEpic\" : [2]" +
                "}";

        HttpRequest request1 = HttpRequest.newBuilder().uri(url1).POST(HttpRequest.BodyPublishers.ofString(epic2)).build();
        HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());


        URI url3 = URI.create("http://localhost:8080/tasks/epic");

        HttpRequest request4 = HttpRequest.newBuilder().uri(url3).GET().build();
        HttpResponse<String> response3 = client.send(request4, HttpResponse.BodyHandlers.ofString());

        String answer = response3.body();
        assertEquals("[11,12]", answer);
    }

    @Test
    void shouldGetItemById() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        String task = addTask("06.04.2022 14:43");

        URI url1 = URI.create("http://localhost:8080/tasks/task?id=5");

        HttpRequest request1 = HttpRequest.newBuilder().uri(url1).GET().build();
        HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());

        String expected = "{\"taskId\":5,\"taskName\":\"Task\",\"description\":\"First task on server\"," +
                "\"startTime\":\"2022-04-06T14:43\",\"duration\":\"PT24H\",\"endTime\":\"2022-04-07T14:43\"," +
                "\"status\":\"NEW\",\"type\":\"TASK\"}";

        String answer = response1.body();
        assertEquals(expected, answer);
    }


    @Test
    void shouldDeleteItemById() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        String task = addTask("06.03.2022 14:43");
        String task1 = addTask("09.03.2022 14:43");


        URI url1 = URI.create("http://localhost:8080/tasks/task?id=6");

        HttpRequest request1 = HttpRequest.newBuilder().uri(url1).DELETE().build();
        HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());

        URI url0 = URI.create("http://localhost:8080/tasks/task");

        HttpRequest request0 = HttpRequest.newBuilder().uri(url0).GET().build();
        HttpResponse<String> response0 = client.send(request0, HttpResponse.BodyHandlers.ofString());

        String answer = response0.body();
        assertEquals("[6]", answer);

    }
}