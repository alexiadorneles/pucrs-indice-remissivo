package domain;

public class LinkedMap<K extends Comparable<K>, V> {
    private class Node {
        public K key;
        public V value;
        public Node next;
        public Node prev;

        private Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public Node() {

        }
    }

    private Node header;
    private Node trailer;
    private int count;

    public LinkedMap() {
        header = new Node(null, null);
        trailer = new Node(null, null);
        header.next = trailer;
        trailer.prev = header;
        count = 0;
    }

    public boolean isEmpty() {
        return (count == 0);
    }

    public int size() {
        return count;
    }

    public void put(K key, V value) {
        Node n = new Node(key, value);
        n.next = trailer;
        n.prev = trailer.prev;
        trailer.prev.next = n;
        trailer.prev = n;
        count++;
    }

    public V get(K key) {
        Node aux = header.next;
        while (aux != trailer) {
            if (aux.key.equals(key)) return aux.value;
            aux = aux.next;
        }

        return null;
    }

    public boolean containsKey(K key) { // O(n)
        Node aux = header.next;
        for (int i = 0; i < count; i++) {
            if (aux.key.equals(key)) return true;
            aux = aux.next;
        }
        return false;
    }

    public void sort() {
        if (this.isEmpty()) return;
        for (Node current = header.next; current.next != null; current = current.next) {
            for (Node index = current.next; index != null; index = index.next) {
                if (current.key != null && index.key != null && current.key.compareTo(index.key) > 0) {
                    K tempKey = current.key;
                    V tempValue = current.value;
                    current.key = index.key;
                    current.value = index.value;
                    index.key = tempKey;
                    index.value = tempValue;
                }
            }
        }
    }


    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        Node aux = header.next;
        for (int i = 0; i < count; i++) {
            s.append(aux.key.toString()).append(" -> ").append(aux.value.toString());
            s.append("\n");
            aux = aux.next;
        }
        return s.toString();
    }

}
