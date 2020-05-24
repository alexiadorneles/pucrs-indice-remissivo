package domain;

public class Index {
    private final Book book;
    private final LinkedMap<Word, LinkedSet<Page>> map;

    public Index(Book book) {
        this.book = book;
        this.map = new LinkedMap<>();
    }

    public void generate() {
        book.getAllWords().forEach(word -> this.map.put(word, word.getPagesWhereItAppears()));
        this.map.sort();
    }

    @Override
    public String toString() {
        DoubleLinkedList<Word> allWords = book.getAllWords();
        StringBuilder builder = new StringBuilder();
        allWords.forEach(word -> {
            builder.append(word.getStripedText());
            DoubleLinkedList<Integer> map = this.map.get(word).map(Page::getNumber);
            map.forEach(number -> builder.append(" -> ").append(number));
            builder.append("\n");
        });
        return builder.toString();
    }
}
