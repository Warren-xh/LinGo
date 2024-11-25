import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class CallFiveAPI {
    // 设定API密钥
    private static final String ONE_API_TOKEN = "sk-m64JA72WnVbOj5CQ7cFc4cFf05Df4eCe93B2F54aC0F6E3Cf";
    private static final String API_URL = "http://localhost:3000/v1/chat/completions";

    // 定义提示模板
    private static final String PROMPT_TEMPLATE =
            "请生成与以下词语完全同义的五个词语，作为搜索关键词。请严格按照以下原则生成：" +
                    "1. 输出严格为五个完全同义的词语。" +
                    "2. 如果没有五个完全同义的词语，请重复原词直到达到五个。" +
                    "3. 确保输出词语彼此之间没有重复。" +
                    "4. 按顺序列出五个词语。" +
                    "5. 每个词语之间仅用','分隔，不包含任何其他符号或空格。" +
                    "6. 只输出生成的词语，不包含任何注释或解释。" +
                    "7. 和输入词处于同一范围，"+
                    "输入词语：____";

    // 创建自定义提示
    private static String createCustomPrompt(String keyword) {
        return PROMPT_TEMPLATE.replace("____", keyword);
    }

    // 获取预测
    private static String getPrediction(String prompt) throws IOException {
        URL url = new URL(API_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", "Bearer " + ONE_API_TOKEN);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        // 设置请求体数据
        String requestBody = String.format(
                "{\"model\":\"ERNIE-4.0-8K\",\"messages\":[{\"role\":\"user\",\"content\":\"%s\"}],\"temperature\":0.7}",
                prompt
        );

        // 发送请求
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = requestBody.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        // 读取响应
        int status = connection.getResponseCode();
        BufferedReader br = new BufferedReader(new InputStreamReader(
                status > 299 ? connection.getErrorStream() : connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String responseLine;
        while ((responseLine = br.readLine()) != null) {
            response.append(responseLine.trim());
        }
        br.close();

        return response.toString();
    }

    // 提取content字段的内容
    private static String extractContent(String jsonResponse) {
        String contentKey = "\"content\":\"";
        int startIndex = jsonResponse.indexOf(contentKey) + contentKey.length();
        int endIndex = jsonResponse.indexOf("\"", startIndex);
        if (startIndex != -1 && endIndex != -1) {
            return jsonResponse.substring(startIndex, endIndex);
        } else {
            return "未找到内容";
        }
    }

    // 提供一个供外部调用的方法
    public static String callFiveAPI(String keyword) {
        try {
            String customPrompt = createCustomPrompt(keyword);
            String prediction = getPrediction(customPrompt);
            return extractContent(prediction);
        } catch (IOException e) {
            e.printStackTrace();
            return "调用API时出错";
        }
    }
}
