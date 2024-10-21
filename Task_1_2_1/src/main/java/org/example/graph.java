package org.example;
import java.util.List;

public interface graph {
    void addVertex(int vertex);
    void removeVertex(int vertex);
    void addEdge(int from, int to);
    void removeEdge(int from, int to);
    List<Integer> getNeighbors(int vertex);
    void readFromFile(String filePath) throws Exception;
    String toString();
    List<Integer> topologicalSort();
}
