package com.skillsdoor.skillverify.domain;

import static com.skillsdoor.skillverify.domain.ExamSessionTestSamples.*;
import static com.skillsdoor.skillverify.domain.ExamTestSamples.*;
import static com.skillsdoor.skillverify.domain.ExerciseTestSamples.*;
import static com.skillsdoor.skillverify.domain.RecruiterTestSamples.*;
import static com.skillsdoor.skillverify.domain.TrainingProviderTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.skillsdoor.skillverify.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ExamTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Exam.class);
        Exam exam1 = getExamSample1();
        Exam exam2 = new Exam();
        assertThat(exam1).isNotEqualTo(exam2);

        exam2.setId(exam1.getId());
        assertThat(exam1).isEqualTo(exam2);

        exam2 = getExamSample2();
        assertThat(exam1).isNotEqualTo(exam2);
    }

    @Test
    void exercisesTest() {
        Exam exam = getExamRandomSampleGenerator();
        Exercise exerciseBack = getExerciseRandomSampleGenerator();

        exam.addExercises(exerciseBack);
        assertThat(exam.getExercises()).containsOnly(exerciseBack);
        assertThat(exerciseBack.getExam()).isEqualTo(exam);

        exam.removeExercises(exerciseBack);
        assertThat(exam.getExercises()).doesNotContain(exerciseBack);
        assertThat(exerciseBack.getExam()).isNull();

        exam.exercises(new HashSet<>(Set.of(exerciseBack)));
        assertThat(exam.getExercises()).containsOnly(exerciseBack);
        assertThat(exerciseBack.getExam()).isEqualTo(exam);

        exam.setExercises(new HashSet<>());
        assertThat(exam.getExercises()).doesNotContain(exerciseBack);
        assertThat(exerciseBack.getExam()).isNull();
    }

    @Test
    void examSessionsTest() {
        Exam exam = getExamRandomSampleGenerator();
        ExamSession examSessionBack = getExamSessionRandomSampleGenerator();

        exam.addExamSessions(examSessionBack);
        assertThat(exam.getExamSessions()).containsOnly(examSessionBack);
        assertThat(examSessionBack.getExam()).isEqualTo(exam);

        exam.removeExamSessions(examSessionBack);
        assertThat(exam.getExamSessions()).doesNotContain(examSessionBack);
        assertThat(examSessionBack.getExam()).isNull();

        exam.examSessions(new HashSet<>(Set.of(examSessionBack)));
        assertThat(exam.getExamSessions()).containsOnly(examSessionBack);
        assertThat(examSessionBack.getExam()).isEqualTo(exam);

        exam.setExamSessions(new HashSet<>());
        assertThat(exam.getExamSessions()).doesNotContain(examSessionBack);
        assertThat(examSessionBack.getExam()).isNull();
    }

    @Test
    void recruiterTest() {
        Exam exam = getExamRandomSampleGenerator();
        Recruiter recruiterBack = getRecruiterRandomSampleGenerator();

        exam.setRecruiter(recruiterBack);
        assertThat(exam.getRecruiter()).isEqualTo(recruiterBack);

        exam.recruiter(null);
        assertThat(exam.getRecruiter()).isNull();
    }

    @Test
    void trainingProviderTest() {
        Exam exam = getExamRandomSampleGenerator();
        TrainingProvider trainingProviderBack = getTrainingProviderRandomSampleGenerator();

        exam.setTrainingProvider(trainingProviderBack);
        assertThat(exam.getTrainingProvider()).isEqualTo(trainingProviderBack);

        exam.trainingProvider(null);
        assertThat(exam.getTrainingProvider()).isNull();
    }
}
