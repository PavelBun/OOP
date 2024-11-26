package org.example;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TranscriptTest {

    private Transcript transcript;

    @BeforeEach
    void setUp() {
        transcript = new Transcript("John Doe", "contract");
    }

    @Test
    void testCalculateGPA() {
        Semester semester1 = new Semester();
        semester1.grades.add(new Grade("Императивное программирование", 5, true));
        semester1.grades.add(new Grade("Мат. анализ", 4, true));
        transcript.semesters.add(semester1);

        Semester semester2 = new Semester();
        semester2.grades.add(new Grade("Операционные системы", 3, true));
        semester2.grades.add(new Grade("ООП", 5, true));
        transcript.semesters.add(semester2);

        assertEquals(4.25, transcript.calculateGPA(), 0.001);
    }

    @Test
    void testCanTransferToBudget() {
        Semester semester1 = new Semester();
        semester1.grades.add(new Grade("Императивное программирование", 5, true));
        semester1.grades.add(new Grade("Мат. анализ", 4, true));
        transcript.semesters.add(semester1);

        Semester semester2 = new Semester();
        semester2.grades.add(new Grade("Операционные системы", 5, true));
        semester2.grades.add(new Grade("ООП", 5, true));
        transcript.semesters.add(semester2);

        assertTrue(transcript.canTransferToBudget());

        transcript.currentForm = "budget";
        assertFalse(transcript.canTransferToBudget());

        transcript.currentForm = "contract";
        semester2.grades.add(new Grade("Введение в ИИ", 3, true));
        assertFalse(transcript.canTransferToBudget());
    }

    @Test
    void testCanGetRedDiploma() {
        Semester semester1 = new Semester();
        semester1.grades.add(new Grade("Мат. анализ", 5, true));
        semester1.grades.add(new Grade("Императивное программирование", 5, true));
        transcript.semesters.add(semester1);

        Semester semester2 = new Semester();
        semester2.grades.add(new Grade("Операционные системы", 5, true));
        semester2.grades.add(new Grade("ООП", 5, true));
        transcript.semesters.add(semester2);

        transcript.qualificationWorkGrade = 5;
        assertTrue(transcript.canGetRedDiploma());

        transcript.qualificationWorkGrade = 4;
        assertFalse(transcript.canGetRedDiploma());

        transcript.qualificationWorkGrade = 5;
        semester2.grades.add(new Grade("Введение в ИИ", 3, true));
        assertFalse(transcript.canGetRedDiploma());
    }

    @Test
    void testCanGetIncreasedScholarship() {
        Semester semester1 = new Semester();
        semester1.grades.add(new Grade("Мат.анализ", 5, true));
        semester1.grades.add(new Grade("Императивное программирование", 4, true));
        transcript.semesters.add(semester1);

        Semester semester2 = new Semester();
        semester2.grades.add(new Grade("Операционные системы", 5, true));
        semester2.grades.add(new Grade("ООП", 5, true));
        transcript.semesters.add(semester2);

        assertTrue(transcript.canGetIncreasedScholarship());

        semester2.grades.add(new Grade("Введение в ИИ", 3, true));
        assertFalse(transcript.canGetIncreasedScholarship());
    }
}