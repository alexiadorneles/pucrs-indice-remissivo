package domain;

public class Page {
    private final DoubleLinkedList<Line> lines;
    private final int number;

    public Page(int number) {
        this.number = number;
        this.lines = new DoubleLinkedList<>();
    }

    public void addLine(Line line) {
        this.lines.add(line);
    }

    public DoubleLinkedList<Line> getLines() {
        return lines;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        this.lines.forEach((line -> builder.append(line).append("\n")));
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Page page = (Page) o;
        return number == page.number;
    }
}
