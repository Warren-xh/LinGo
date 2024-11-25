import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        String content = CallFiveAPI.callFiveAPI(input);
        String content1 = CallOneAPI.callOneAPI(input);
        System.out.println(content);
        System.out.println(content1);
        System.out.println(EnglishAPI.EnglishAPI(input));
        System.out.println(FrenchAPI.FrenchAPI(input));
        System.out.println(RussiaAPI.RussiaAPI(input));
        System.out.println(SpanishAPI.SpanishAPI(input));
        System.out.println(ArabicAPI.ArabicAPI(input));
    }
}