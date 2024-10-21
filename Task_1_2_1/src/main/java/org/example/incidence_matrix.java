package org.example;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class incidence_matrix implements graph {
    private final List<List<Integer>> matrix;
    private int numVertices;
    private int numEdges;

    public incidence_matrix(int numVertices, int numEdges) {
        this.numVertices = numVertices;
        this.numEdges = numEdges;
        this.matrix = new ArrayList<>(numVertices);
        for (int i = 0; i < numVertices; i++) {
            List<Integer> row = new ArrayList<>(numEdges);
            for (int j = 0; j < numEdges; j++) {
                row.add(0);
            }
            this.matrix.add(row);
        }
    }

    @Override
    public void addVertex(int vertex) {
        if (vertex >= numVertices) {
            numVertices = vertex + 1;
            while (matrix.size() < numVertices) {
                List<Integer> newRow = new ArrayList<>(numEdges);
                for (int i = 0; i < numEdges; i++) {
                    newRow.add(0);
                }
                matrix.add(newRow);
            }
        }
    }

    @Override
    public void removeVertex(int vertex) {
        if (vertex < numVertices) {
            matrix.remove(vertex);
            numVertices--;
        }
    }

    @Override
    public void addEdge(int from, int to) {
        if (from < numVertices && to < numVertices) {
            for (int i = 0; i < numEdges; i++) {
                if (matrix.get(from).get(i) == 0 && matrix.get(to).get(i) == 0) {
                    matrix.get(from).set(i, 1);
                    matrix.get(to).set(i, -1);
                    return;
                }
            }
            // Если не нашли свободное место для ребра, увеличиваем количество ребер
            numEdges++;
            for (List<Integer> row : matrix) {
                row.add(0);
            }
            matrix.get(from).set(numEdges - 1, 1);
            matrix.get(to).set(numEdges - 1, -1);
        }
    }

    @Override
    public void removeEdge(int from, int to) {
        if (from < numVertices && to < numVertices && from != to) {
            for (int i = 0; i < numEdges; i++) {
                if (matrix.get(from).get(i) == 1 && matrix.get(to).get(i) == -1) {
                    matrix.get(from).set(i, 0);
                    matrix.get(to).set(i, 0);
                    return;
                }
            }
        }
    }

    @Override
    public List<Integer> getNeighbors(int vertex) {
        List<Integer> neighbors = new ArrayList<>();
        for (int i = 0; i < numEdges; i++) {
            if (matrix.get(vertex).get(i) == 1) {
                for (int j = 0; j < numVertices; j++) {
                    if (matrix.get(j).get(i) == -1) {
                        neighbors.add(j);
                    }
                }
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
        incidence_matrix that = (incidence_matrix) o;
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
    public List<Integer> topologicalSort() {
        List<Integer> result = new ArrayList<>();
        boolean[] visited = new boolean[numVertices];
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < numVertices; i++) {
            if (!visited[i]) {
                topSort(i, visited, stack);
            }
        }

        while (!stack.isEmpty()) {
            result.add(stack.pop());
        }

        return result;
    }

    private void topSort(int v, boolean[] visited, Stack<Integer> stack) {
        visited[v] = true;
        for (int i = 0; i < numEdges; i++) {
            if (matrix.get(v).get(i) == 1) {
                for (int j = 0; j < numVertices; j++) {
                    if (matrix.get(j).get(i) == -1 && !visited[j]) {
                        topSort(j, visited, stack);
                    }
                }
            }
        }
        stack.push(v);
    }
}