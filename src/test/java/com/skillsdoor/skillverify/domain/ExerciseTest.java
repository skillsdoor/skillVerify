package com.skillsdoor.skillverify.domain;

import static com.skillsdoor.skillverify.domain.ExamTestSamples.*;
import static com.skillsdoor.skillverify.domain.ExerciseTestSamples.*;
import static com.skillsdoor.skillverify.domain.QuestionTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.skillsdoor.skillverify.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ExerciseTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Exercise.class);
        Exercise exercise1 = getExerciseSample1();
        Exercise exercise2 = new Exercise();
        assertThat(exercise1).isNotEqualTo(exercise2);

        exercise2.setId(exercise1.getId());
        assertThat(exercise1).isEqualTo(exercise2);

        exercise2 = getExerciseSample2();
        assertThat(exercise1).isNotEqualTo(exercise2);
    }

    @Test
    void questionsTest() {
        Exercise exercise = getExerciseRandomSampleGenerator();
        Question questionBack = getQuestionRandomSampleGenerator();

        exercise.addQuestions(questionBack);
        assertThat(exercise.getQuestions()).containsOnly(questionBack);
        assertThat(questionBack.getExercise()).isEqualTo(exercise);

        exercise.removeQuestions(questionBack);
        assertThat(exercise.getQuestions()).doesNotContain(questionBack);
        assertThat(questionBack.getExercise()).isNull();

        exercise.questions(new HashSet<>(Set.of(questionBack)));
        assertThat(exercise.getQuestions()).containsOnly(questionBack);
        assertThat(questionBack.getExercise()).isEqualTo(exercise);

        exercise.setQuestions(new HashSet<>());
        assertThat(exercise.getQuestions()).doesNotContain(questionBack);
        assertThat(questionBack.getExercise()).isNull();
    }

    @Test
    void examTest() {
        Exercise exercise = getExerciseRandomSampleGenerator();
        Exam examBack = getExamRandomSampleGenerator();

        exercise.setExam(examBack);
        assertThat(exercise.getExam()).isEqualTo(examBack);

        exercise.exam(null);
        assertThat(exercise.getExam()).isNull();
    }
}
