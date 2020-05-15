package domain;

public class Word {
    private String originalText;
    private String stripedText;

    public Word(String originalText, String stripedText) {
        this.originalText = originalText;
        this.stripedText = stripedText;
    }

    @Override
    public String toString() {
        return this.originalText;
    }
}
