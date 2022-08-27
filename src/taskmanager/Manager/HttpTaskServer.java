package taskmanager.Manager;

import com.sun.net.httpserver.HttpServer;
import java.io.*;
import java.net.InetSocketAddress;

public class HttpTaskServer {
    HttpServer httpServer = HttpServer.create(new InetSocketAddress(8080), 0);
    TaskManager tasksManager = Managers.getDefault();

    public HttpTaskServer() throws IOException{
    }


}
