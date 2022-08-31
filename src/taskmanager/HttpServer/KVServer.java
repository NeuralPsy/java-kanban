package taskmanager.HttpServer;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Постман: https://www.getpostman.com/collections/a83b61d9e1c81c10575c
 */
public class KVServer {
	public static final int PORT = 8078;
	private final String apiToken;
	private final HttpServer server;
	private final Map<String, String> data = new HashMap<>();

	public KVServer() throws IOException {
		apiToken = generateApiToken();
		server = HttpServer.create(new InetSocketAddress("localhost", PORT), 0);
		server.createContext("/register", this::register);
		server.createContext("/save", this::save);
		server.createContext("/load", this::load);
	}

	public static void main(String[] args) throws IOException {
		KVServer kvServer = new KVServer();
		kvServer.start();
	}

	private void load(HttpExchange h) throws IOException {
		if (!hasAuth(h)){
			try (OutputStream os = h.getResponseBody()){
				String response = "Запрос неавторизован, нужен параметр в query API_TOKEN со значением апи-ключа";
				h.sendResponseHeaders(403, 0);
				os.write(response.getBytes());

			} return;
		}
		if ("GET".equals(h.getRequestMethod())){
			String key = h.getRequestURI().getPath().substring("/load/".length());
			if (!data.containsKey(key)) {
				System.out.println("Указанный кey не зарегистрирован");
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
					String response = "Запрос неавторизован, нужен параметр в query API_TOKEN со значением апи-ключа";
					h.sendResponseHeaders(403, 0);
					os.write(response.getBytes());

				} return;

			}
			if ("POST".equals(h.getRequestMethod())) {
				String key = h.getRequestURI().getPath().substring("/save/".length());
				if (key.isEmpty()) {
					try (OutputStream os = h.getResponseBody()){
						String response = "Key для сохранения пустой. key указывается в пути: /save/{key}";
						h.sendResponseHeaders(400, 0);
						os.write(response.getBytes());

					} return;
				}
				String value = readText(h);
				if (value.isEmpty()) {
					try (OutputStream os = h.getResponseBody()){
						String response = "\"Value для сохранения пустой. value указывается в теле запроса\" " +
								 h.getRequestMethod();
						h.sendResponseHeaders(405, 0);
						os.write(response.getBytes());

					} return;
				}
				data.put(key, value);
				try (OutputStream os = h.getResponseBody()){
					String response = "Значение для ключа " + key + " успешно обновлено!";
					h.sendResponseHeaders(200, 0);
					os.write(response.getBytes());
				}



			} else {
				try (OutputStream os = h.getResponseBody()){
					String response = "/save ждёт POST-запрос, а получил: " + h.getRequestMethod();
					h.sendResponseHeaders(405, 0);
					os.write(response.getBytes());
				} return;
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
					String response = "/register ждёт GET-запрос, а получил " + h.getRequestMethod();
					h.sendResponseHeaders(405, 0);
					os.write(response.getBytes());
				}
			}
		} finally {
			h.close();
		}
	}

	public void start() {
		System.out.println("Запускаем сервер на порту " + PORT);
		System.out.println("Открой в браузере http://localhost:" + PORT + "/");
		System.out.println("API_TOKEN: " + apiToken);
		server.start();
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
