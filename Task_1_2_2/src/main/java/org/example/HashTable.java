package org.example;

import java.util.*;

public class HashTable<K, V> implements Iterable<Map.Entry<K, V>> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    private int size;
    private int capacity;
    private float loadFactor;
    private ArrayList<Entry<K, V>>[] table;
    private int modCount;

    public HashTable() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    @SuppressWarnings("unchecked")
    public HashTable(int capacity, float loadFactor) {
        this.capacity = capacity;
        this.loadFactor = loadFactor;
        this.table = new ArrayList[capacity];
        this.size = 0;
        this.modCount = 0;
    }


    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }

        if (size >= capacity * loadFactor) {
            resize();
        }

        int index = hash(key);
        if (table[index] == null) {
            table[index] = new ArrayList<>();
        }

        for (Entry<K, V> entry : table[index]) {
            if (entry.getKey().equals(key)) {
                entry.setValue(value);
                return;
            }
        }

        table[index].add(new Entry<>(key, value));
        size++;
        modCount++;
    }

    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }

        int index = hash(key);
        if (table[index] != null) {
            for (Entry<K, V> entry : table[index]) {
                if (entry.getKey().equals(key)) {
                    return entry.getValue();
                }
            }
        }

        return null;
    }

    public void remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }

        int index = hash(key);
        if (table[index] != null) {
            Iterator<Entry<K, V>> iterator = table[index].iterator();
            while (iterator.hasNext()) {
                if (iterator.next().getKey().equals(key)) {
                    iterator.remove();
                    size--;
                    modCount++;
                    return;
                }
            }
        }
    }

    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }

        int index = hash(key);
        if (table[index] != null) {
            for (Entry<K, V> entry : table[index]) {
                if (entry.getKey().equals(key)) {
                    return true;
                }
            }
        }

        return false;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HashTable<?, ?> that = (HashTable<?, ?>) o;
        if (this.size != that.size) return false;

        for (int i = 0; i < capacity; i++) {
            if (table[i] != null) {
                for (Entry<K, V> entry : table[i]) {
                    if (!containsEntry((HashTable<K, ?>) that, entry.getKey(), entry.getValue())) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private boolean containsEntry(HashTable<K, ?> that, K key, V value) {
        Object thatValue = that.get(key);
        return thatValue != null && thatValue.equals(value);
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(", ", "{", "}");
        for (int i = 0; i < capacity; i++) {
            if (table[i] != null) {
                for (Entry<K, V> entry : table[i]) {
                    joiner.add(entry.getKey() + "=" + entry.getValue());
                }
            }
        }
        return joiner.toString();
    }


    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        return new HashTableIterator();
    }

    private int hash(K key) {
        if (key instanceof String) {
            return Math.abs(jenkinsHash((String) key)) % capacity;
        } else {
            return Math.abs(key.hashCode()) % capacity;
        }
    }


    private int jenkinsHash(String key) {
        int hash = 0;
        for (char c : key.toCharArray()) {
            hash += c;
            hash += (hash << 10);
            hash ^= (hash >> 6);
        }
        hash += (hash << 3);
        hash ^= (hash >> 11);
        hash += (hash << 15);
        return (hash & Integer.MAX_VALUE);
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        capacity *= 2;
        ArrayList<Entry<K, V>>[] newTable = new ArrayList[capacity];
        for (ArrayList<Entry<K, V>> bucket : table) {
            if (bucket != null) {
                for (Entry<K, V> entry : bucket) {
                    int index = hash(entry.getKey());
                    if (newTable[index] == null) {
                        newTable[index] = new ArrayList<>();
                    }
                    newTable[index].add(entry);
                }
            }
        }
        table = newTable;
    }


    public void update(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        int index = hash(key);
        if (table[index]!= null) {
            for (Entry<K, V> entry : table[index]) {
                if (entry.getKey().equals(key)) {
                    entry.setValue(value);
                    return;
                }
            }
        }
        put(key, value);
    }

    private static class Entry<K, V> implements Map.Entry<K, V> {
        private final K key;
        private V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }
    }

    private class HashTableIterator implements Iterator<Map.Entry<K, V>> {
        private int expectedModCount = modCount;
        private int currentBucket = 0;
        private Iterator<Entry<K, V>> currentIterator = null;

        @Override
        public boolean hasNext() {
            if (expectedModCount != modCount) {
                throw new ConcurrentModificationException();
            }

            while ((currentIterator == null || !currentIterator.hasNext()) && currentBucket < capacity) {
                if (table[currentBucket] != null && !table[currentBucket].isEmpty()) {
                    currentIterator = table[currentBucket].iterator();
                }
                currentBucket++;
            }

            return currentIterator != null && currentIterator.hasNext();
        }


        @Override
        public Map.Entry<K, V> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            return currentIterator.next();
        }

        @Override
        public void remove() {
            if (expectedModCount != modCount) {
                throw new ConcurrentModificationException();
            }

            currentIterator.remove();
            expectedModCount = modCount;
        }
    }

}
