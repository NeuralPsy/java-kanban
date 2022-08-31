package taskmanager.HttpServer;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVTaskClient {
    HttpClient httpClient;
    URI urlRegister;
    URI url;
    private final String urlPut = "save/";
    private final String urlLoad = "load/";
    private final String apiToken;
    Gson gson = new Gson();

    public KVTaskClient(String url) throws IOException, InterruptedException {
        httpClient = HttpClient.newHttpClient();
        this.url = URI.create(url);
        URI urlRegister = URI.create(url+"/register");
        HttpRequest request = HttpRequest.newBuilder().uri(urlRegister).GET().build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        apiToken = response.toString();
    }

    public void put(String key, String json) throws IOException, InterruptedException {
        URI urlPut = URI.create(this.url+this.urlPut+key+"?"+"API_TOKEN="+apiToken);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(urlPut)
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public String load(String key) throws IOException, InterruptedException {
        URI urlLoad = URI.create(this.url+this.urlLoad+key+"?"+"API_TOKEN="+apiToken);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(urlLoad)
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}
