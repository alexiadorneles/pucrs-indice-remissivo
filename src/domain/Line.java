package domain;

public class Line {
    private final DoubleLinkedList<Word> words;
    private final String originalText;

    public Line(String originalText) {
        this.originalText = originalText;
        this.words = new DoubleLinkedList<>();
    }

    public void addWord(Word word) {
        this.words.add(word);
    }

    public DoubleLinkedList<Word> getWords() {
        return words;
    }

    @Override
    public String toString() {
        return this.originalText;
    }
}
