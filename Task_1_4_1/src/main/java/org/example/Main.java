package org.example;

import java.io.*;
import java.util.*;

class Grade implements Serializable {
    String subject;
    int grade;
    boolean isExam;

    Grade(String subject, int grade, boolean isExam) {
        this.subject = subject;
        this.grade = grade;
        this.isExam = isExam;
    }
}

class Semester implements Serializable {
    List<Grade> grades = new ArrayList<>();
}

class Transcript implements Serializable {
    String studentName;
    List<Semester> semesters = new ArrayList<>();
    String currentForm;
    int qualificationWorkGrade = -1;

    Transcript(String studentName, String currentForm) {
        this.studentName = studentName;
        this.currentForm = currentForm;
    }

    double calculateGPA() {
        int totalGrades = 0;
        int totalSum = 0;
        for (Semester semester : semesters) {
            for (Grade grade : semester.grades) {
                totalGrades++;
                totalSum += grade.grade;
            }
        }
        return totalGrades == 0 ? 0 : (double) totalSum / totalGrades;
    }

    boolean canTransferToBudget() {
        if (currentForm.equals("budget")) return false;
        for (int i = semesters.size() - 1; i >= semesters.size() - 2 && i >= 0; i--) {
            for (Grade grade : semesters.get(i).grades) {
                if (grade.isExam && grade.grade == 3) return false;
            }
        }
        return true;
    }

    boolean canGetRedDiploma() {
        int excellentGrades = 0;
        int totalGrades = 0;
        for (Semester semester : semesters) {
            for (Grade grade : semester.grades) {
                if (grade.grade == 5) excellentGrades++;
                totalGrades++;
            }
        }
        if (totalGrades == 0 || (double) excellentGrades / totalGrades < 0.75) return false;
        for (Semester semester : semesters) {
            for (Grade grade : semester.grades) {
                if (grade.grade == 3) return false;
            }
        }
        return qualificationWorkGrade == 5;
    }

    boolean canGetIncreasedScholarship() {
        if (semesters.isEmpty()) return false;
        Semester lastSemester = semesters.get(semesters.size() - 1);
        for (Grade grade : lastSemester.grades) {
            if (grade.grade < 4) return false;
        }
        return true;
    }
}
public class Main {
    public static void main(String[] args) {
        Transcript transcript = new Transcript("Павел Бунь", "contract");

        Semester semester1 = new Semester();
        semester1.grades.add(new Grade("Мат. анализ", 5, true));
        semester1.grades.add(new Grade("История", 4, true));
        transcript.semesters.add(semester1);

        Semester semester2 = new Semester();
        semester2.grades.add(new Grade("Императивное программирование", 5, true));
        semester2.grades.add(new Grade("Оси", 5, false));
        transcript.semesters.add(semester2);

        // Сериализация
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("transcript.dat"))) {
            oos.writeObject(transcript);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Десериализация
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("transcript.dat"))) {
            Transcript loadedTranscript = (Transcript) ois.readObject();
            System.out.println("Имя студента: " + loadedTranscript.studentName);
            System.out.println("Форма обучения: " + loadedTranscript.currentForm);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
