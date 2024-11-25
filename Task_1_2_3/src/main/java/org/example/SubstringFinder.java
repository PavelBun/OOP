package org.example;

import java.io.*;
import java.util.*;

public class SubstringFinder {

    public static List<Integer> find(String fileName, String substring) throws IOException {
        List<Integer> indices = new ArrayList<>();
        int substringLength = substring.length();
        byte[] substringBytes = substring.getBytes("UTF-8");
        byte[] buffer = new byte[1024]; // Размер буфера для чтения файла
        int offset = 0;
        int bytesRead;
        int overlap = substringLength - 1; // Перекрытие для учета границ частей

        try (InputStream inputStream = new BufferedInputStream(new FileInputStream(fileName))) {
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                // Преобразуем буфер в строку для поиска
                String chunk = new String(buffer, 0, bytesRead, "UTF-8");

                // Ищем вхождения подстроки в текущем чанке
                int index = 0;
                while ((index = chunk.indexOf(substring, index)) != -1) {
                    indices.add(offset + index);
                    index += substringLength;
                }

                // Обновляем смещение
                offset += bytesRead - overlap;

                // Если есть перекрытие, сохраняем последние байты для следующей итерации
                if (overlap > 0) {
                    System.arraycopy(buffer, bytesRead - overlap, buffer, 0, overlap);
                }
            }
        }

        return indices;
    }

    public static void main(String[] args) {
        try {
            List<Integer> indices = find("/home/ang7y/IdeaProjects/OOP7/Task_1_2_3/src/main/resources/teext.txt", "qwe");
            System.out.println(indices); // Вывод: [1, 8]
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}