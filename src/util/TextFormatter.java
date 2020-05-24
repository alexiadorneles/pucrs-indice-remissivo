package util;

public class TextFormatter {
    private TextFormatter() {
    }

    public static String formatWord(String text) {
        return text.toLowerCase().replaceAll("[^a-zA-Z0-9 ]", "");
    }

    public static String formatLine(String text) {
        return text.replaceAll("\\t", " ")
                .replaceAll("-", " ")
                .replaceAll("/", " ");
    }
}
