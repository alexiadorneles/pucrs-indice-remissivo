package domain;

import java.util.Objects;

public class Word {
    private String originalText;
    private String stripedText;
    private boolean stopword;

    public Word(String originalText, String stripedText) {
        this.originalText = originalText;
        this.stripedText = stripedText;
    }

    public String getOriginalText() {
        return originalText;
    }

    public String getStripedText() {
        return stripedText;
    }

    public boolean isStopword() {
        return stopword;
    }

    public void setOriginalText(String originalText) {
        this.originalText = originalText;
    }

    public void setStripedText(String stripedText) {
        this.stripedText = stripedText;
    }

    public void setStopword(boolean stopword) {
        this.stopword = stopword;
    }

    @Override
    public String toString() {
        return this.originalText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Word word = (Word) o;
        return Objects.equals(originalText, word.originalText);
    }
}
