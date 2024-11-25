import java.io.File;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class ExtractInputValue {
    public static void main(String[] args) {
        try {
            // 读取HTML文件
            File input = new File("index.html");
            Document doc = Jsoup.parse(input, "UTF-8");

            // 获取输入字段的值
            Element inputElement = doc.getElementById("inputText");
            if (inputElement != null) {
                String inputValue = inputElement.val();
                System.out.println("Input value: " + inputValue);
            } else {
                System.out.println("Input element not found!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
