package domain;

public class Page {
    private DoubleLinkedList<Line> lines;
    private int number;
    private int numberOfStopwords;

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

    public void incrementNumberOfStopwords() {
        this.numberOfStopwords++;
    }

    public int getNumberOfStopwords() {
        return numberOfStopwords;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        this.lines.forEach((line -> builder.append(line).append("\n")));
        return builder.toString();
    }
}
