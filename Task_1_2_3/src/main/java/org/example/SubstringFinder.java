package org.example;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class SubstringFinder {

    public static List<Integer> find(String fileName, String substring) throws IOException {
        List<Integer> indices = new ArrayList<>();
        int substringLength = substring.length(); // длина подстроки в символа
        int offset = 0;
        int overlap = substringLength - 1; // перекрытие для учета границ частей

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8))) {
            char[] buffer = new char[1024];
            int charsRead;

            while ((charsRead = reader.read(buffer)) != -1) {
                // преобразуем буфер в строку для поиска
                String chunk = new String(buffer, 0, charsRead);

                // ищем вхождения подстроки в текущем чанке
                int index = 0;
                while ((index = chunk.indexOf(substring, index)) != -1) {
                    indices.add(offset + index);
                    index += substringLength;
                }

                // Обновляем смещение
                offset += charsRead - overlap;

                // если есть перекрытие сохраняем последние символы для следующей итерации
                if (overlap > 0) {
                    System.arraycopy(buffer, charsRead - overlap, buffer, 0, overlap);
                }
            }
        }

        return indices;
    }

    public static void main(String[] args) {
        try {
            List<Integer> indices = find("/home/ang7y/IdeaProjects/OOP7/Task_1_2_3/src/main/resources/teext.txt", "qwe");
            System.out.println(indices);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
