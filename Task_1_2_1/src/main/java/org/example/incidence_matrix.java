package org.example;

import java.io.FileReader;
import java.util.*;

public class incidence_matrix implements graph {
    private final List<List<Integer>> matrix;
    private int numVertices;
    private int numEdges;


    public incidence_matrix(int numVertices) {
        this.numVertices = numVertices;
        this.numEdges = 0;
        this.matrix = new ArrayList<>(numVertices);
        for (int i = 0; i < numVertices; i++) {
            List<Integer> row = new ArrayList<>(numEdges);
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
            for (int i = 0; i < numEdges; i++) {
                if (matrix.get(vertex).get(i) != 0) {
                    for (int j = 0; j < numVertices; j++) {
                        matrix.get(j).set(i, 0);
                    }
                }
            }
            // Удаляем строку
            matrix.remove(vertex);
            numVertices--;
        }
    }

    @Override
    public void addEdge(int from, int to) {
        numEdges++;
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
            int maxCols = 0;


            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] values = line.split(" ");
                maxCols = Math.max(maxCols, values.length);
            }
            fileScanner = new Scanner(new FileReader(filePath));

            this.numEdges = maxCols;

            for (List<Integer> rowList : matrix) {
                rowList.clear();
                for (int i = 0; i < numEdges; i++) {
                    rowList.add(0);
                }
            }
            row = 0;
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
            return false;
        }

        visited[v] = true;
        stack[v] = true;

        for (int i = 0; i < numEdges; i++) {
            if (matrix.get(v).get(i) == 1) {
                for (int j = 0; j < numVertices; j++) {
                    if (matrix.get(j).get(i) == -1 && topSort(j, visited, stack, result)) {
                        return true; // цикл
                    }
                }
            }
        }
        stack[v] = false;
        result.add(0, v);
        return false;
    }
}
