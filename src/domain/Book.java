package domain;

public class Book {
    private final DoubleLinkedList<Page> pages;
    private DoubleLinkedList<Word> words;
    private int totalPages;

    public Book() {
        this.pages = new DoubleLinkedList<>();
        this.words = new DoubleLinkedList<>();
    }

    public void addPage(Page page) {
        this.pages.add(page);
        this.totalPages++;
    }

    public int getTotalStopwords() {
        return this.pages.map(Page::getNumberOfStopwords).reduce(Integer::sum, 0);
    }

    public int getTotalWordsCount() {
        return this.getAllWords().reduce((acc, word) -> acc + word.getNumberOfAppearence(), 0);
//        return getPages().map(Page::getNumberOfWords).reduce(Integer::sum, 0);
    }

    public DoubleLinkedList<Word> getAllWords() {
        if (this.words.isEmpty()) {
            this.words = getAllLines().reduce((acc, line) -> acc.addAll(line.getWords()), new DoubleLinkedList<>());
        }

        return this.words;
    }

    private DoubleLinkedList<Line> getAllLines() {
        DoubleLinkedList<Line> allLines = new DoubleLinkedList<>();
        DoubleLinkedList<DoubleLinkedList<Line>> linesMatrix = this.pages.map(Page::getLines);
        linesMatrix.forEach(allLines::addAll);
        return allLines;
    }

    public double getStopwordsPercentage() {
        return (this.getTotalStopwords() * 100.0) / this.getTotalWordsCount();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        this.pages.forEach(builder::append);
        return builder.toString();
    }
}
