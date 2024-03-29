package taskmanager.HttpServer;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

public class KVServer {
	public static final int PORT = 8078;
	private String apiToken;
	private final HttpServer server;
	private final Map<String, String> data = new HashMap<>();
	Gson gson = new Gson();

	public KVServer() throws IOException {
		apiToken = generateApiToken();
		server = HttpServer.create(new InetSocketAddress("localhost", PORT), 0);
		server.createContext("/register", this::register);
		server.createContext("/save", this::save);
		server.createContext("/load", this::load);
	}


	private void load(HttpExchange h) throws IOException {
		if (!hasAuth(h)){
			try (OutputStream os = h.getResponseBody()){
				String response = "Unauthorized request, parameter is needed in query API_TOKEN";
				h.sendResponseHeaders(403, 0);
				os.write(response.getBytes());

			} return;
		}
		if ("GET".equals(h.getRequestMethod())){
			String key = h.getRequestURI().getPath().substring("/load/".length());
			if (!data.containsKey(key)) {
				System.out.println("Kкey is not registered");
				h.sendResponseHeaders(400, 0);
				return;
			}
			sendText(h, data.get(key));
		}
	}

	private void save(HttpExchange h) throws IOException {
		try {
			System.out.println("\n/save");
			if (!hasAuth(h)) {
				try (OutputStream os = h.getResponseBody()){
					String response = "Unauthorized request, parameter is needed in query API_TOKEN";
					h.sendResponseHeaders(403, 0);
					os.write(response.getBytes());

				} return;

			}
			if ("POST".equals(h.getRequestMethod())) {
				String key = h.getRequestURI().getPath().substring("/save/".length());
				if (key.isEmpty()) {
					try (OutputStream os = h.getResponseBody()){
						String response = "Key is empty. key should be given in path: /save/{key}";
						h.sendResponseHeaders(400, 0);
						os.write(response.getBytes());

					} return;
				}
				String value = readText(h);
				if (value.isEmpty()) {
					try (OutputStream os = h.getResponseBody()){
						String response = "\"Value is empty. value should bi given in request-body\" " +
								 h.getRequestMethod();
						h.sendResponseHeaders(405, 0);
						os.write(response.getBytes());

					} return;
				}
				data.put(key, value);
				try (OutputStream os = h.getResponseBody()){
					String response = "Key value " + key + " is successfully updated!";
					h.sendResponseHeaders(200, 0);
					os.write(response.getBytes());
				}



			} else {
				try (OutputStream os = h.getResponseBody()){
					String response = "/save POST-request was expected, but: " + h.getRequestMethod() +" is received";
					h.sendResponseHeaders(405, 0);
					os.write(response.getBytes());
				}
			}
		} finally {
			h.close();
		}
	}

	private void register(HttpExchange h) throws IOException {
		try {
			System.out.println("\n/register");
			if ("GET".equals(h.getRequestMethod())) {
				sendText(h, apiToken);
			} else {
				try (OutputStream os = h.getResponseBody()){
					String response = "/register GET-request expected, but " + h.getRequestMethod() +" was received";
					h.sendResponseHeaders(405, 0);
					os.write(response.getBytes());
				}
			}
		} finally {
			h.close();
		}
	}

	public void start() {
		System.out.println("Star server at port " + PORT);
		System.out.println("Open the link in browser http://localhost:" + PORT + "/");
		System.out.println("API_TOKEN: " + apiToken);
		server.start();
	}

	public void stop() {
		server.stop(0);
	}

	private String generateApiToken() {
		return "" + System.currentTimeMillis();
	}

	protected boolean hasAuth(HttpExchange h) {
		String rawQuery = h.getRequestURI().getRawQuery();
		return rawQuery != null && (rawQuery.contains("API_TOKEN=" + apiToken) || rawQuery.contains("API_TOKEN=DEBUG"));
	}

	protected String readText(HttpExchange h) throws IOException {
		return new String(h.getRequestBody().readAllBytes(), UTF_8);
	}

	protected void sendText(HttpExchange h, String text) throws IOException {
		byte[] resp = text.getBytes(UTF_8);
		h.getResponseHeaders().add("Content-Type", "application/json");
		h.sendResponseHeaders(200, resp.length);
		h.getResponseBody().write(resp);
	}
}
