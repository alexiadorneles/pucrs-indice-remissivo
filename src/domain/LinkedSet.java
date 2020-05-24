package domain;

public class LinkedSet<T> extends DoubleLinkedList<T> {

    @Override
    public void add(T element) {
        if (this.contains(element)) return;
        super.add(element);
    }

}
