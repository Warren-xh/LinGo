import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

import org.json.JSONObject;

public class Main{
    private static String searchContent;
    public static void main(String[] args) throws IOException{
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/search", new SearchHandler());
        server.createContext("/preflight", new PreflightHandler());
        server.setExecutor(null);
        server.start();
        System.out.println("Server started at http://localhost:8000");


    }

    static class SearchHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
                exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "POST, OPTIONS");
                exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");
                exchange.sendResponseHeaders(204, -1); // No content
            } else if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                InputStream inputStream = exchange.getRequestBody();
                String requestBody = new BufferedReader(new InputStreamReader(inputStream))
                        .lines()
                        .reduce("", (accumulator, actual) -> accumulator + actual);
                System.out.println("Received JSON: " + requestBody);

                JSONObject jsonObject = new JSONObject(requestBody);
                String queryValue = jsonObject.getString("query");
                System.out.println("Query Value: " + queryValue);

                String languageValue = jsonObject.getString("language");
                System.out.println("Language Value: " + languageValue);

                PreciseSearch p = new PreciseSearch();
                p.getLanguage(languageValue);
                p.getSearchContent(queryValue);
                String result=p.concreteSearch();

                String outer[]=result.split(";");
                StringBuilder Response=new StringBuilder();
                Response.append("[");

                for(int i=0;i<outer.length;i++){
                    String inner[]=outer[i].split(",");
                    float f = Float.parseFloat(inner[2]);
                    if(inner[3].equals("null")||inner[3].equals(null)||inner[3].equals("")){
                        inner[3]="C:/Users/33160/Desktop/Study/2025/Hackathon/Lingo/L1ngo/src/Ingenico.jpg";
                    }
                    if(i!=outer.length-1){
                        Response.append("{\"name\": \""+inner[0]+"\", \"location\": \""+inner[1]+"\", \"price\": "+inner[2] +",\"image\": \""+inner[3]+"\"},");
                    }
                    else{
                        Response.append("{\"name\": \""+inner[0]+"\", \"location\": \""+inner[1]+"\", \"price\": "+inner[2] +",\"image\": \""+inner[3]+"\"}");
                    }
                }
                Response.append("]");
                String response=Response.toString();

                exchange.getResponseHeaders().add("Content-Type", "application/json");
                exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
                exchange.sendResponseHeaders(200, response.getBytes().length);

                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();

            } else {
                exchange.sendResponseHeaders(405, -1); // Method Not Allowed
            }
        }

    }


    static class PreflightHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "POST, OPTIONS");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");
            exchange.getResponseHeaders().add("Access-Control-Max-Age", "3600"); // 缓存时间
            exchange.sendResponseHeaders(204, -1); // 无内容响应
        }

        private static void handlePreflight(HttpExchange exchange) throws IOException {
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "POST, OPTIONS");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");
            exchange.getResponseHeaders().add("Access-Control-Max-Age", "3600");
            exchange.sendResponseHeaders(204, -1); // 无内容响应
        }

        static String readInputStream(InputStream inputStream) throws IOException {
            StringBuilder stringBuilder = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
            }
            return stringBuilder.toString();
        }
    }


}