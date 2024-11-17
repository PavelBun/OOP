package org.example;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.util.List;
import java.nio.file.Files;
import static org.junit.jupiter.api.Assertions.*;

public class incidence_matrixTest {
    private incidence_matrix graph;

    @BeforeEach
    public void setUp() {
        graph = new incidence_matrix(4);
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
    public void testReadFromFile() throws Exception {
        // Читаем матрицу из файла
        graph.readFromFile("src/test/resources/test_incidence.txt");

        // Ожидаемая матрица инцидентности
        incidence_matrix expectedMatrix = new incidence_matrix(4);
        expectedMatrix.addEdge(0, 1);
        expectedMatrix.addEdge(1, 2);
        expectedMatrix.addEdge(2, 3);
        expectedMatrix.addEdge(3, 0);

        // Проверяем, что матрицы равны
        assertTrue(graph.equals(expectedMatrix));
    }
    @Test
    public void testEquals_EqualMatrices() {
        // Создаем две матрицы с одинаковыми данными

        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);

        incidence_matrix graph2 = new incidence_matrix(4);
        graph2.addEdge(0, 1);
        graph2.addEdge(1, 2);
        graph2.addEdge(2, 3);

        // Проверяем, что метод equals возвращает true
        assertTrue(graph.equals(graph2));

        graph2.addEdge(0, 3);


        assertFalse(graph.equals(graph2));
    }
}
