package domain;

public class Line {
    private DoubleLinkedList<Word> words;

    public Line() {
        this.words = new DoubleLinkedList<>();
    }

    public void addWord(Word word) {
        this.words.add(word);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        this.words.forEach((word -> builder.append(word).append(" ")));
        return builder.toString();
    }
}
