package org.example;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class adjacency_matrix implements graph {
    private final List<List<Integer>> matrix;
    private int numVertices;

    public adjacency_matrix(int numVertices) {
        this.numVertices = numVertices;
        this.matrix = new ArrayList<>(numVertices);
        for (int i = 0; i < numVertices; i++) {
            List<Integer> adj_matrix = new ArrayList<>(numVertices);
            for (int j = 0; j < numVertices; j++) {
                adj_matrix.add(0);
            }
            matrix.add(adj_matrix);
        }
    }

    @Override
    public void addVertex(int vertex) {
        if (vertex >= numVertices) {
            numVertices = vertex + 1;
            for (List<Integer> row : matrix) {
                while (row.size() < numVertices) {
                    row.add(0);
                }
            }
            while (matrix.size() < numVertices) {
                List<Integer> newRow = new ArrayList<>(numVertices);
                for (int i = 0; i < numVertices; i++) {
                    newRow.add(0);
                }
                matrix.add(newRow);
            }
        }
    }

    @Override
    public void removeVertex(int vertex) {
        if (vertex < numVertices) {
            for (List<Integer> row : matrix) {
                row.remove(vertex);
            }
            matrix.remove(vertex);
            numVertices--;
        }
    }

    @Override
    public void addEdge(int from, int to) {
        if (from < numVertices && to < numVertices) {
            matrix.get(from).set(to, 1);
        }
    }

    @Override
    public void removeEdge(int from, int to) {
        if (from < numVertices && to < numVertices) {
            matrix.get(from).set(to, 0);
        }
    }

    @Override
    public List<Integer> getNeighbors(int vertex) {
        List<Integer> neighbors = new ArrayList<>();
        for (int i = 0; i < numVertices; i++) {
            if (matrix.get(vertex).get(i) == 1) {
                neighbors.add(i);
            }
        }
        return neighbors;
    }

    @Override
    public void readFromFile(String filePath) throws Exception {
        try (FileReader reader = new FileReader(filePath)) {
            Scanner fileScanner = new Scanner(reader);
            int row = 0;
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] values = line.split(" ");
                for (int col = 0; col < values.length; col++) {
                    matrix.get(row).set(col, Integer.parseInt(values[col]));
                }
                row++;
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        adjacency_matrix that = (adjacency_matrix) o;
        return matrix.equals(that.matrix);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (List<Integer> row : matrix) {
            for (int value : row) {
                sb.append(value).append(" ");
            }
            sb.append("\n");
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
            return true;
        }
        if (visited[v]) {
            return false;
        }

        visited[v] = true;
        stack[v] = true;

        for (int i = 0; i < numVertices; i++) {
            if (matrix.get(v).get(i) == 1 && topSort(i, visited, stack, result)) {
                return true; // цикл
            }
        }

        stack[v] = false;
        result.add(0, v);
        return false;
    }
}
