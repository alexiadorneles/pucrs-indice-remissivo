package domain;

import java.util.Objects;

public class Word implements Comparable<Word> {
    private final LinkedSet<Page> pagesWhereItAppears;
    private final String stripedText;
    private final boolean stopword;
    private int numberOfAppearence;

    public Word(String stripedText, boolean stopword) {
        this.stripedText = stripedText;
        this.stopword = stopword;
        this.pagesWhereItAppears = new LinkedSet<>();
        this.numberOfAppearence = 1;
    }

    public String getStripedText() {
        return stripedText;
    }

    public boolean isStopword() {
        return this.stopword;
    }

    public boolean isNOTStopword() {
        return !stopword;
    }

    @Override
    public String toString() {
        return this.stripedText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Word word = (Word) o;
        return Objects.equals(this.stripedText, word.stripedText);
    }

    public void incrementNumberOfAppearence() {
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
