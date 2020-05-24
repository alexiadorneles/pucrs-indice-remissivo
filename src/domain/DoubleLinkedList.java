package domain;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class DoubleLinkedList<T> {
    private Node header;
    private Node trailer;
    protected Node current;
    private int count;

    private class Node {

        public T element;
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

    public boolean some(Function<T, Boolean> function) {
        this.reset();
        while (!this.current.equals(this.trailer)) {
            boolean result = function.apply(this.current.element);
            if (result) return true;
            this.next();
        }

        return false;
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

    public void add(int index, T element) throws IndexOutOfBoundsException {
        if (index < 0 || index > count)
            throw new IndexOutOfBoundsException();

        if (index == count) {
            add(element);
        } else {
            Node n = new Node(element);
            Node aux = getNodeRef(index);
            n.next = aux;
            n.prev = aux.prev;
            aux.prev.next = n;
            aux.prev = n;
            count++;
        }
    }

    public boolean remove(T element) {
        Node aux = header.next;

        while (aux != trailer) {
            if (aux.element.equals(element)) {
                aux.prev.next = aux.next;
                aux.next.prev = aux.prev;
                count--;
                return true;
            } else
                aux = aux.next;
        }
        return false;
    }

    public T removeByIndex(int index) {
        if (index < 0 || index >= count) {
            throw new IndexOutOfBoundsException();
        }

        Node aux = this.getNodeRef(index);
        return this.removeByRef(aux);
    }

    private T removeByRef(Node aux) {
        aux.prev.next = aux.next;
        aux.next.prev = aux.prev;
        count--;

        return aux.element;
    }

    public boolean contains(T element) {
        Node aux = header.next;
        while (aux != trailer) {
            if (aux.element.equals(element)) return true;
            aux = aux.next;
        }
        return false;
    }

    public T get(int index) {
        if ((index < 0) || (index >= count)) {
            throw new IndexOutOfBoundsException();
        }
        Node aux = getNodeRef(index);
        return aux.element;
    }

    private Node getNodeRef(int index) {
        Node aux;
        if (index < count / 2) {
            aux = header.next;
            for (int i = 0; i < index; i++)
                aux = aux.next;
        } else {
            aux = trailer.prev;
            for (int i = count - 1; i > index; i--)
                aux = aux.prev;
        }
        return aux;
    }

    public int indexOf(T element) {
        Node aux = header.next;
        for (int i = 0; i < size(); i++) {
            if (aux.element.equals(element)) return i;
            aux = aux.next;
        }
        return -1;
    }

    public T set(int index, T element) {
        if ((index < 0) || (index >= count)) {
            throw new IndexOutOfBoundsException();
        }

        Node aux = this.getNodeRef(index);
        T auxElement = aux.element;
        aux.element = element;
        return auxElement;
    }

    public void clear() {
        header = new Node(null);
        trailer = new Node(null);
        header.next = trailer;
        trailer.prev = header;
        count = 0;
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


    public String toStringBackToFront() { // O(n)
        StringBuilder s = new StringBuilder();
        Node aux = trailer.prev;
        for (int i = 0; i < count; i++) {
            s.append(aux.element.toString());
            s.append("\n");
            aux = aux.prev;
        }
        return s.toString();

    }

    public void reverse() { // O(n)
        Node primeiro = header.next;
        Node ultimo = trailer.prev;

        for (int i = 0; i < size() / 2; i++) {
            T element = primeiro.element;
            primeiro.element = ultimo.element;
            ultimo.element = element;
            primeiro = primeiro.next;
            ultimo = ultimo.prev;
        }
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
