package domain;

public class Line {
    private final DoubleLinkedList<Word> words;
    private String originalText;

    public Line(Page page) {
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

    public void setOriginalText(String lineText) {
        this.originalText = lineText;
    }
}
