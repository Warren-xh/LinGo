import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class RussiaAPI {
    private static final String ONE_API_TOKEN = "sk-m64JA72WnVbOj5CQ7cFc4cFf05Df4eCe93B2F54aC0F6E3Cf";
    private static final String API_URL = "http://localhost:3000/v1/chat/completions";

    private static final String PROMPT_TEMPLATE =
            "请将以下词语翻译成俄文。请严格按照以下原则生成：" +
                    "1. 只输出翻译后的词语，不包含任何注释或解释。" +
                    "输入词语：____";

    private static String createCustomPrompt(String keyword) {
        return PROMPT_TEMPLATE.replace("____", keyword);
    }

    private static String getPrediction(String prompt) throws IOException {
        URL url = new URL(API_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", "Bearer " + ONE_API_TOKEN);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        String requestBody = String.format(
                "{\"model\":\"ERNIE-4.0-8K\",\"messages\":[{\"role\":\"user\",\"content\":\"%s\"}],\"temperature\":0.7}",
                prompt
        );

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = requestBody.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int status = connection.getResponseCode();
        InputStream is = (status > 299) ? connection.getErrorStream() : connection.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"));
        StringBuilder response = new StringBuilder();
        String responseLine;
        while ((responseLine = br.readLine()) != null) {
            response.append(responseLine.trim());
        }
        br.close();

        return response.toString();
    }

    private static String extractContent(String jsonResponse) {
        String contentKey = "\"content\":\"";
        int startIndex = jsonResponse.indexOf(contentKey) + contentKey.length();
        int endIndex = jsonResponse.indexOf("\"", startIndex);
        return (startIndex != -1 && endIndex != -1) ? jsonResponse.substring(startIndex, endIndex) : "未找到内容";
    }

    public static String RussiaAPI(String keyword) {
        try {
            String customPrompt = createCustomPrompt(keyword);
            String prediction = getPrediction(customPrompt);
            return extractContent(prediction);
        } catch (IOException e) {
            e.printStackTrace();
            return "调用API时出错";
        }
    }

    public static void main(String[] args) {
        String keyword = "测试词语";
        String result = RussiaAPI(keyword);
        System.out.println("翻译后的词语：" + result);
    }
}
