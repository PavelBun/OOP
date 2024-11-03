package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class HashTableTest {
    private HashTable<String, Integer> hashTable;

    @BeforeEach
    void setUp() {
        hashTable = new HashTable<>();
    }

    @Test
    void testPutAndGet() {
        hashTable.put("one", 1);
        hashTable.put("two", 2);
        hashTable.put("three", 3);

        assertEquals(1, hashTable.get("one"));
        assertEquals(2, hashTable.get("two"));
        assertEquals(3, hashTable.get("three"));
        assertNull(hashTable.get("four"));
    }

    @Test
    void testRemove() {
        hashTable.put("one", 1);
        hashTable.put("two", 2);
        hashTable.put("three", 3);

        hashTable.remove("two");

        assertNull(hashTable.get("two"));
        assertEquals(2, hashTable.size());
    }

    @Test
    void testContainsKey() {
        hashTable.put("one", 1);
        hashTable.put("two", 2);

        assertTrue(hashTable.containsKey("one"));
        assertTrue(hashTable.containsKey("two"));
        assertFalse(hashTable.containsKey("three"));
    }

    @Test
    void testSize() {
        assertEquals(0, hashTable.size());

        hashTable.put("one", 1);
        hashTable.put("two", 2);

        assertEquals(2, hashTable.size());

        hashTable.remove("one");

        assertEquals(1, hashTable.size());
    }

    @Test
    void testIsEmpty() {
        assertTrue(hashTable.isEmpty());

        hashTable.put("one", 1);

        assertFalse(hashTable.isEmpty());

        hashTable.remove("one");

        assertTrue(hashTable.isEmpty());
    }

    @Test
    void testEquals() {
        HashTable<String, Integer> otherHashTable = new HashTable<>();

        hashTable.put("one", 1);
        hashTable.put("two", 2);

        otherHashTable.put("one", 1);
        otherHashTable.put("two", 2);

        assertEquals(hashTable, otherHashTable);

        otherHashTable.put("three", 3);

        assertNotEquals(hashTable, otherHashTable);
    }

    @Test
    void testToString() {
        hashTable.put("one", 1);
        hashTable.put("two", 2);

        String result = hashTable.toString();
        assertTrue(result.matches("\\{(one=1, two=2|two=2, one=1)\\}"));
    }

    @Test
    void testIterator() {
        hashTable.put("one", 1);
        hashTable.put("two", 2);
        hashTable.put("three", 3);

        Set<String> expectedKeys = Set.of("one", "two", "three");
        Set<String> actualKeys = new HashSet<>();

        for (Map.Entry<String, Integer> entry : hashTable) {
            actualKeys.add(entry.getKey());
        }

        assertEquals(expectedKeys, actualKeys);
    }


    @Test
    void testConcurrentModificationException() {
        hashTable.put("one", 1);
        hashTable.put("two", 2);

        Iterator<Map.Entry<String, Integer>> iterator = hashTable.iterator();

        hashTable.put("three", 3);

        assertThrows(ConcurrentModificationException.class, iterator::next);
    }
}