package Json;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.InetSocketAddress;

public class SimpleHttpServer {
    public static void main(String[] args) throws IOException {
        int port = 8000; // 设置为 8000 端口
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        System.out.println("Server started at http://localhost:" + port);

        // 指定 HTML 文件路径
        String htmlFilePath = "C:\\Users\\33160\\Desktop\\Study\\2025\\Hackathon\\Lingo\\L1ngo\\src\\index.html";

        // 为根路径创建处理器
        server.createContext("/", new FileHandler(htmlFilePath));

        server.setExecutor(null); // 使用默认的线程池
        server.start();
    }

    static class FileHandler implements HttpHandler {
        private final String filePath;

        public FileHandler(String filePath) {
            this.filePath = filePath;
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            File file = new File(filePath);
            if (!file.exists()) {
                String errorMessage = "File not found: " + filePath;
                exchange.sendResponseHeaders(404, errorMessage.length());
                OutputStream os = exchange.getResponseBody();
                os.write(errorMessage.getBytes());
                os.close();
                return;
            }

            // 设置响应头
            exchange.getResponseHeaders().add("Content-Type", "text/html");
            exchange.sendResponseHeaders(200, file.length());

            // 输出 HTML 文件内容
            try (OutputStream os = exchange.getResponseBody();
                 InputStream fis = new FileInputStream(file)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = fis.read(buffer)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
            }
        }
    }
}
