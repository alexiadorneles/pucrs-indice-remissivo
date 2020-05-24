package domain;

public class LinkedSet<T> extends DoubleLinkedList<T> {

    @Override
    public void add(T element) {
        if (this.contains(element)) return;
        super.add(element);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        this.reset();
        for (int i = 0; i < this.size(); i++) {
            builder.append("->").append(this.next());
        }
        return builder.toString();
    }
}
