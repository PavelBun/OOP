package org.example.core;

import org.example.dsl.Configuration;
import org.example.dsl.ConfigLoader;
import org.example.core.models.Student;
import org.example.core.models.LabResult;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        try {
            // Загрузка конфигурации
            Configuration config = ConfigLoader.load("config.groovy"); // Используем статический метод

            // Получение списка студентов
            List<Student> students = config.getGroups().stream()
                    .flatMap(g -> g.getStudents().stream())
                    .collect(Collectors.toList());

            // Клонирование репозиториев
            Path workspace = Files.createTempDirectory("oop-checker");
            for (Student student : students) {
                GitService.cloneRepository(student, workspace);
            }

            // Проверка лабораторных работ
            List<LabResult> results = new CheckProcessor(config).processAllTasks();

            // Генерация отчёта
            String htmlReport = ReportGenerator.generate(results);
            System.out.println(htmlReport);

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}