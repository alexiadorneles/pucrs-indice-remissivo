package domain;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class DoubleLinkedList<T> {
    private final Node header;
    private final Node trailer;
    protected Node current;
    private int count;

    private class Node {

        public final T element;
        public Node next;
        public Node prev;

        public Node(T e) {
            element = e;
            next = null;
            prev = null;
        }

    }

    @SafeVarargs
    public static <T> DoubleLinkedList<T> asList(T... elements) {
        DoubleLinkedList<T> list = new DoubleLinkedList<>();
        for (final T element : elements) {
            list.add(element);
        }
        return list;
    }

    public void reset() {
        current = header.next;
    }

    public T next() {
        T aux;
        if (current != null) {
            aux = current.element;
            current = current.next;
            return aux;
        }

        return null;
    }

    public void forEach(Consumer<T> consumer) {
        this.reset();
        while (!this.current.equals(this.trailer)) {
            consumer.accept(this.current.element);
            this.next();
        }
    }

    public <R> DoubleLinkedList<R> map(Function<? super T, R> function) {
        this.reset();
        DoubleLinkedList<R> newList = new DoubleLinkedList<>();
        while (!this.current.equals(this.trailer)) {
            R result = function.apply(this.current.element);
            newList.add(result);
            this.next();
        }

        return newList;
    }

    public DoubleLinkedList<T> filter(Function<? super T, Boolean> function) {
        this.reset();
        DoubleLinkedList<T> newList = new DoubleLinkedList<>();
        while (!this.current.equals(this.trailer)) {
            if (function.apply(this.current.element)) newList.add(this.current.element);
            this.next();
        }

        return newList;
    }

    public <R> R reduce(BiFunction<R, ? super T, R> function, R accumulator) {
        if (this.isEmpty()) return null;
        this.reset();
        R newAccumulator = accumulator;
        while (!this.current.equals(this.trailer)) {
            newAccumulator = function.apply(newAccumulator, this.current.element);
            this.next();
        }

        return newAccumulator;
    }

    public T reduce(BiFunction<T, ? super T, T> function) {
        if (this.isEmpty()) return null;
        this.reset();
        T newAccumulator = function.apply(this.current.element, this.next());
        while (!this.current.equals(this.trailer)) {
            newAccumulator = function.apply(newAccumulator, this.current.element);
            this.next();
        }

        return newAccumulator;
    }

    public T find(Function<T, Boolean> function) {
        this.reset();
        while (!this.current.equals(this.trailer)) {
            if (function.apply(this.current.element)) return this.current.element;
            this.next();
        }

        return null;
    }

    public DoubleLinkedList<T> addAll(DoubleLinkedList<T> list) {
        list.forEach(this::add);
        return this;
    }

    public DoubleLinkedList() {
        header = new Node(null);
        trailer = new Node(null);
        header.next = trailer;
        trailer.prev = header;
        count = 0;
    }


    public void add(T element) {
        Node n = new Node(element);
        n.next = trailer;
        n.prev = trailer.prev;
        trailer.prev.next = n;
        trailer.prev = n;
        count++;
    }

    public boolean contains(T element) {
        Node aux = header.next;
        while (aux != trailer) {
            if (aux.element.equals(element)) return true;
            aux = aux.next;
        }
        return false;
    }

    public int size() {
        return count;
    }

    public boolean isEmpty() {
        return (count == 0);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        Node aux = header.next;
        for (int i = 0; i < count; i++) {
            s.append(aux.element.toString());
            s.append("\n");
            aux = aux.next;
        }
        return s.toString();
    }


    public void sort() {
        Node sortedList = this.mergeSort(this.header.next);
        this.header.next = sortedList;
        this.header.next.prev = this.header;
        this.trailer.prev = this.getEnd(sortedList);
        this.trailer.prev.next = trailer;
    }

    private Node mergeSort(Node h) {
        if (h == null || h.next == null) {
            return h;
        }
        Node middle = getMiddle(h);
        Node nextOfMiddle = middle.next;
        middle.next = null;
        Node left = mergeSort(h);
        Node right = mergeSort(nextOfMiddle);
        return this.merge(left, right);
    }

    @SuppressWarnings("unchecked")
    private Node merge(Node a, Node b) {
        Node result;

        if (a == null || a.element == null)
            return b;
        if (b == null || b.element == null)
            return a;

        if (((Comparable<T>) a.element).compareTo(b.element) < 0) {
            result = a;
            result.next = merge(a.next, b);
        } else {
            result = b;
            result.next = merge(a, b.next);
        }
        return result;
    }

    private Node getMiddle(Node h) {
        if (h == null)
            return null;

        Node slow = h, fast = h;

        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow;
    }

    private Node getEnd(Node h) {
        if (h == null)
            return null;

        while (h.next != null) {
            h = h.next;
        }

        return h;
    }
}
