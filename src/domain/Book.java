package domain;

public class Book {
    private DoubleLinkedList<Page> pages;
    private int totalPages;

    public Book() {
        this.pages = new DoubleLinkedList<>();
    }

    public void addPage(Page page) {
        this.pages.add(page);
        this.totalPages++;
    }

    public DoubleLinkedList<Page> getPages() {
        return pages;
    }

    public int getTotalStopwords() {
        return this.pages.map(Page::getNumberOfStopwords).reduce(Integer::sum, 0);
    }

    public int getTotalWordsCount() {
        DoubleLinkedList<Line> allLines = new DoubleLinkedList<>();
        DoubleLinkedList<DoubleLinkedList<Line>> linesMatrix = this.pages.map(Page::getLines);
        linesMatrix.forEach(allLines::addAll);
        return allLines.reduce((acc, line) -> acc + line.getWords().size(), 0);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        this.pages.forEach(builder::append);
        return builder.toString();
    }
}
