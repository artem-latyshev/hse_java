import java.util.ArrayList;
import java.util.List;

public class CamelCase {
    public static void main(String[] args) {
        String string = "ILoveJavaProgramming";
        camelCaseManual(string);
        camelCaseRegexp(string);
    }

    private static void camelCaseManual(String string) {
        char [] chars = string.toCharArray();
        List<String> result = new ArrayList<>(string.length());
        StringBuilder stringBuilder = new StringBuilder();
        boolean capitalLetterFound = false;
        for(char letter : chars) {
            if (letter >= 'A' && letter  <= 'Z') {
                capitalLetterFound = true;
                addToResultAndClearBuilderIfNotEmpty(stringBuilder, result);
                stringBuilder.append(letter);
            } else if (capitalLetterFound) {
                stringBuilder.append(letter);
            }
        }
        addToResultAndClearBuilderIfNotEmpty(stringBuilder, result);

        for(String word : result) {
            System.out.println(word);
        }
    }

    private static void camelCaseRegexp(String string) {
        String[] result = string.split("(?=\\p{Upper})");
        for(String word : result) {
            System.out.println(word);
        }
    }


    private static void addToResultAndClearBuilderIfNotEmpty(StringBuilder stringBuilder, List<String> result){
        if (stringBuilder.length() > 0) {
            result.add(stringBuilder.toString());
            stringBuilder.setLength(0);
        }
    }
}
