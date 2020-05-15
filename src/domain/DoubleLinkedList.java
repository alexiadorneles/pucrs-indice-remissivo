package domain;

import java.util.function.Consumer;

public class DoubleLinkedList<T> {
    private Node header;
    private Node trailer;
    private Node current;
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
        for (int i = 0; i < count; i++) {
            consumer.accept(current.element);
            this.next();
        }
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

    public void unique() { // O(n ^ 2)
        Node aux = header.next;
        while (aux != trailer) {
            Node helper = aux.next;
            while (helper != trailer) {
                if (helper != null && helper.element.equals(aux.element)) {
                    this.removeByRef(helper);
                }
                assert helper != null;
                helper = helper.next;
            }
            aux = aux.next;
        }
    }
}
