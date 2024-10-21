package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class adjacency_listTest {
    private adjacency_list graph;

    @BeforeEach
    public void setUp() {
        graph = new adjacency_list(4);
    }

    @Test
    public void testAddEdge() {
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);

        assertEquals(1, graph.getNeighbors(0).size());
        assertEquals(1, graph.getNeighbors(1).size());
        assertEquals(1, graph.getNeighbors(2).size());
        assertEquals(0, graph.getNeighbors(3).size());
    }

    @Test
    public void testRemoveEdge() {
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.removeEdge(1, 2);

        assertEquals(1, graph.getNeighbors(0).size());
        assertEquals(0, graph.getNeighbors(1).size());
        assertEquals(0, graph.getNeighbors(2).size());
    }

    @Test
    public void testAddVertex() {
        graph.addVertex(4);
        graph.addEdge(4, 0);

        assertEquals(1, graph.getNeighbors(4).size());
    }

    @Test
    public void testRemoveVertex() {
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.removeVertex(1);

        assertEquals(0, graph.getNeighbors(0).size());
        assertEquals(0, graph.getNeighbors(2).size());
    }

    @Test
    public void testTopologicalSort() {
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);

        List<Integer> sorted = graph.topologicalSort();
        assertEquals(4, sorted.size());
        assertEquals(0, sorted.get(0));
        assertEquals(3, sorted.get(3));
    }
}