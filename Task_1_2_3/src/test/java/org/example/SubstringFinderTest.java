package org.example;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SubstringFinderTest {

    @Test
    public void testFindSubstringInFile() throws IOException {
        String fileName = "testFile.txt";
        String content = "qweasdqwezxcasdqwe";
        Files.write(Paths.get(fileName), content.getBytes());


        List<Integer> indices = SubstringFinder.find(fileName, "qwe");


        assertEquals(List.of(0, 6, 15), indices);

        Files.delete(Paths.get(fileName));
    }

    @Test
    public void testFindSubstringNotInFile() throws IOException {
        // Создаем временный файл с содержимым
        String fileName = "testFile.txt";
        String content = "asdzxcqweasdzxc";
        Files.write(Paths.get(fileName), content.getBytes());

        List<Integer> indices = SubstringFinder.find(fileName, "qaz");

        assertEquals(List.of(), indices);

        // Удаляем временный файл
        Files.delete(Paths.get(fileName));
    }

    @Test
    public void testFindSubstringCrossingBufferBoundaries() throws IOException {

        String fileName = "testFile.txt";
        String content = "asdqwezxcasdqwe";
        Files.write(Paths.get(fileName), content.getBytes());

        // Ищем подстроку "qwe"
        List<Integer> indices = SubstringFinder.find(fileName, "qwe");

        // Ожидаемые индексы
        assertEquals(List.of(3, 12), indices);

        // Удаляем временный файл
        Files.delete(Paths.get(fileName));
    }

    @Test
    public void testFindSubstringAtFileEnd() throws IOException {

        String fileName = "testFile.txt";
        String content = "asdzxcqwe";
        Files.write(Paths.get(fileName), content.getBytes());

        List<Integer> indices = SubstringFinder.find(fileName, "qwe");

        assertEquals(List.of(6), indices);

        Files.delete(Paths.get(fileName));
    }

    @Test
    public void testFindSubstringAtFileStart() throws IOException {

        String fileName = "testFile.txt";
        String content = "qweasdzxc";
        Files.write(Paths.get(fileName), content.getBytes());

        List<Integer> indices = SubstringFinder.find(fileName, "qwe");


        assertEquals(List.of(0), indices);

        Files.delete(Paths.get(fileName));
    }
    @Test
    public void testFindChineseSubstring() throws IOException {
        String fileName = "testFile.txt";
        String content = "你好世界 你好 你好世界";
        Files.write(Paths.get(fileName), content.getBytes("UTF-8"));

        List<Integer> indices = SubstringFinder.find(fileName, "你好");

        assertEquals(List.of(0, 5, 8), indices);

        Files.delete(Paths.get(fileName));
    }

}