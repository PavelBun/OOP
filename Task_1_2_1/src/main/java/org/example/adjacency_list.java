package org.example;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class adjacency_list implements graph {
    private final List<List<Integer>> adjacencyList;
    private int numVertices;

    public adjacency_list(int numVertices) {
        this.numVertices = numVertices;
        this.adjacencyList = new ArrayList<>(numVertices);
        for (int i = 0; i < numVertices; i++) {
            adjacencyList.add(new ArrayList<>());
        }
    }

    @Override
    public void addVertex(int vertex) {
        numVertices++;
        adjacencyList.add(new ArrayList<>());
    }

    @Override
    public void removeVertex(int vertex) {
        if (vertex < numVertices) {
            adjacencyList.remove(vertex);
            numVertices--;
            for (List<Integer> neighbors : adjacencyList) {
                neighbors.remove(Integer.valueOf(vertex));
            }
        }
    }

    @Override
    public void addEdge(int from, int to) {
        if (from < numVertices && to < numVertices) {
            adjacencyList.get(from).add(to);
        }
    }

    @Override
    public void removeEdge(int from, int to) {
        List<Integer> edges = adjacencyList.get(from);
        if (edges != null) {
            edges.remove(Integer.valueOf(to));
        }
    }

    @Override
    public List<Integer> getNeighbors(int vertex) {
        return adjacencyList.get(vertex);
    }

    @Override
    public void readFromFile(String filePath) throws Exception {
        try (FileReader reader = new FileReader(filePath)) {
            Scanner fileScanner = new Scanner(reader);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] values = line.split(" ");
                int from = Integer.parseInt(values[0]);
                int to = Integer.parseInt(values[1]);
                addEdge(from, to);
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        adjacency_list that = (adjacency_list) obj;
        return adjacencyList.equals(that.adjacencyList);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numVertices; i++) {
            sb.append(i).append(": ");
            sb.append(adjacencyList.get(i)).append("\n");
        }
        return sb.toString();
    }

    @Override
    public List<Integer> topologicalSort() throws Exception {
        List<Integer> result = new ArrayList<>();
        boolean[] visited = new boolean[numVertices];
        boolean[] stack = new boolean[numVertices];

        for (int i = 0; i < numVertices; i++) {
            if (!visited[i]) {
                if (topSort(i, visited, stack, result)) {
                    throw new Exception("Graph contains a cycle");
                }
            }
        }

        return result;
    }

    private boolean topSort(int v, boolean[] visited, boolean[] stack, List<Integer> result) {
        if (stack[v]) {
            return true; // цикл
        }

        if (visited[v]) {
            return false; // посещенный узел
        }

        visited[v] = true;
        stack[v] = true;

        for (int neighbor : adjacencyList.get(v)) {
            if (topSort(neighbor, visited, stack, result)) {
                return true; // цикл
            }
        }

        stack[v] = false;
        result.add(0, v);
        return false;
    }
}
