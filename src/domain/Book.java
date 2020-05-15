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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        this.pages.forEach(builder::append);
        return builder.toString();
    }
}
