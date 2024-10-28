package org.example;

import java.io.FileReader;
import java.util.*;

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
            // Удаляем все рёбра, связанные с этой вершиной
            for (int i = 0; i < numEdges; i++) {
                if (matrix.get(vertex).get(i) != 0) {
                    for (int j = 0; j < numVertices; j++) {
                        matrix.get(j).set(i, 0);
                    }
                }
            }
            // Удаляем строку, соответствующую вершине
            matrix.remove(vertex);
            numVertices--;
        }
    }

    @Override
    public void addEdge(int from, int to) {
        if (from >= matrix.size() || to >= matrix.size()
                || from < 0 || to < 0) {
            return;
        }

        if (from == to) {
            for (int i = 0; i < matrix.size(); i++) {
                if (i == from) {
                    matrix.get(i).add(2);
                } else {
                    matrix.get(i).add(0);
                }
            }
        } else {
            for (int i = 0; i < matrix.size(); i++) {
                if (i == from) {
                    matrix.get(i).add(1);
                } else if (i == to) {
                    matrix.get(i).add(-1);
                } else {
                    matrix.get(i).add(0);
                }
            }
        }
        numEdges++; // Увеличиваем счетчик ребер
    }

    @Override
    public void removeEdge(int from, int to) {
        if (from < numVertices && to < numVertices && from != to) {
            for (int i = 0; i < numEdges; i++) {
                if (matrix.get(from).get(i) == 1 && matrix.get(to).get(i) == -1) {
                    matrix.get(from).set(i, 0);
                    matrix.get(to).set(i, 0);
                    numEdges--; // Уменьшаем счетчик ребер
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
                        break;
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
