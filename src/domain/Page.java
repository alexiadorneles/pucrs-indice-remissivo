package domain;

public class Page {
    private DoubleLinkedList<Line> lines;
    private int number;

    public Page(int number) {
        this.number = number;
        this.lines = new DoubleLinkedList<>();
    }

    public void addLine(Line line) {
        this.lines.add(line);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        this.lines.forEach((line -> builder.append(line).append("\n")));
        return builder.toString();
    }
}
