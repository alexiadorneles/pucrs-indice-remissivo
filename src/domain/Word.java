package domain;

import java.util.Objects;

public class Word implements Comparable<Word> {
    private LinkedSet<Page> pagesWhereItAppears;
    private String originalText;
    private String stripedText;
    private boolean stopword;
    private int numberOfAppearence;

    public Word(String originalText, String stripedText) {
        this.originalText = originalText;
        this.stripedText = stripedText;
        this.numberOfAppearence = 1;
        this.pagesWhereItAppears = new LinkedSet<>();
    }

    public Word(String originalText, String stripedText, boolean stopword) {
        this.originalText = originalText;
        this.stripedText = stripedText;
        this.stopword = stopword;
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

    public boolean isNOTStopword() {
        return !stopword;
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
        return Objects.equals(this.stripedText, word.stripedText);
    }

    public void incrementNumber() {
        this.numberOfAppearence++;
    }

    public int getNumberOfAppearence() {
        return numberOfAppearence;
    }

    public void appearsOn(Page page) {
        this.pagesWhereItAppears.add(page);
    }

    public LinkedSet<Page> getPagesWhereItAppears() {
        return pagesWhereItAppears;
    }

    @Override
    public int compareTo(Word word) {
        return this.stripedText.compareTo(word.stripedText);
    }
}
