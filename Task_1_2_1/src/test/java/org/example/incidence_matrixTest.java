package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class incidence_matrixTest {
    private incidence_matrix graph;

    @BeforeEach
    public void setUp() {
        graph = new incidence_matrix(4, 4);
    }

    @Test
    public void testAddEdge() {
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);

        System.out.println("Матрица инцидентности:");
        System.out.println(graph);

        assertEquals(1, graph.getNeighbors(0).size());
        assertEquals(1, graph.getNeighbors(1).size());
        assertEquals(1, graph.getNeighbors(2).size());
        assertEquals(0, graph.getNeighbors(3).size());

        // Проверяем, что соседи правильные
        assertTrue(graph.getNeighbors(0).contains(1));
        assertTrue(graph.getNeighbors(1).contains(2));
        assertTrue(graph.getNeighbors(2).contains(3));
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
