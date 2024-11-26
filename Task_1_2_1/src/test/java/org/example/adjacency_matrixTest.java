package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class adjacency_matrixTest {
    private adjacency_matrix graph;

    @BeforeEach
    public void setUp() {
        graph = new adjacency_matrix(4);
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
    public void testTopologicalSort() throws Exception {
        graph.addEdge(0, 1);
        graph.addEdge(1, 3);
        graph.addEdge(3, 2);

        List<Integer> sorted = graph.topologicalSort();
        assertEquals(4, sorted.size());
        assertEquals(0, sorted.get(0));
        assertEquals(2, sorted.get(3));
    }
    @Test
    public void testCycle() throws Exception{
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 0);
        String expectedMessage = "Graph contains a cycle";
        try {
            List<Integer> sorted = graph.topologicalSort();
        }
        catch (Exception e){
            assertEquals(expectedMessage, e.getMessage());
        }

    }
    @Test
    public void testEquals() {
        adjacency_matrix matrix1 = new adjacency_matrix(3);
        adjacency_matrix matrix2 = new adjacency_matrix(3);

        matrix1.addEdge(0, 1);
        matrix1.addEdge(1, 2);
        matrix2.addEdge(0, 1);
        matrix2.addEdge(1, 2);

        assertTrue(matrix1.equals(matrix2));

        // Изменяем
        matrix2.addEdge(2, 0);

        // Проверяем
        assertFalse(matrix1.equals(matrix2));
    }

    @Test
    public void testReadFromFile() throws Exception {

        // Читаем матрицу из файла
        graph.readFromFile("src/test/resources/test_adjacency_matrix.txt");

        // Ожидаемая матрица
        adjacency_matrix expectedMatrix = new adjacency_matrix(4);
        expectedMatrix.addEdge(0, 1);
        expectedMatrix.addEdge(1, 2);
        expectedMatrix.addEdge(2, 3);
        expectedMatrix.addEdge(3, 0);


        // Проверяем, что матрицы равны
        assertTrue(graph.equals(expectedMatrix));
    }


}
