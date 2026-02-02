package com.skillsdoor.skillverify.domain;

import static com.skillsdoor.skillverify.domain.BlankSpaceTestSamples.*;
import static com.skillsdoor.skillverify.domain.CandidateAnswerTestSamples.*;
import static com.skillsdoor.skillverify.domain.ExamTestSamples.*;
import static com.skillsdoor.skillverify.domain.ExerciseTestSamples.*;
import static com.skillsdoor.skillverify.domain.ListItemTestSamples.*;
import static com.skillsdoor.skillverify.domain.MediaTestSamples.*;
import static com.skillsdoor.skillverify.domain.QcmOptionTestSamples.*;
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
    void mediaTest() {
        Exercise exercise = getExerciseRandomSampleGenerator();
        Media mediaBack = getMediaRandomSampleGenerator();

        exercise.addMedia(mediaBack);
        assertThat(exercise.getMedia()).containsOnly(mediaBack);
        assertThat(mediaBack.getExercise()).isEqualTo(exercise);

        exercise.removeMedia(mediaBack);
        assertThat(exercise.getMedia()).doesNotContain(mediaBack);
        assertThat(mediaBack.getExercise()).isNull();

        exercise.media(new HashSet<>(Set.of(mediaBack)));
        assertThat(exercise.getMedia()).containsOnly(mediaBack);
        assertThat(mediaBack.getExercise()).isEqualTo(exercise);

        exercise.setMedia(new HashSet<>());
        assertThat(exercise.getMedia()).doesNotContain(mediaBack);
        assertThat(mediaBack.getExercise()).isNull();
    }

    @Test
    void qcmOptionsTest() {
        Exercise exercise = getExerciseRandomSampleGenerator();
        QcmOption qcmOptionBack = getQcmOptionRandomSampleGenerator();

        exercise.addQcmOptions(qcmOptionBack);
        assertThat(exercise.getQcmOptions()).containsOnly(qcmOptionBack);
        assertThat(qcmOptionBack.getExercise()).isEqualTo(exercise);

        exercise.removeQcmOptions(qcmOptionBack);
        assertThat(exercise.getQcmOptions()).doesNotContain(qcmOptionBack);
        assertThat(qcmOptionBack.getExercise()).isNull();

        exercise.qcmOptions(new HashSet<>(Set.of(qcmOptionBack)));
        assertThat(exercise.getQcmOptions()).containsOnly(qcmOptionBack);
        assertThat(qcmOptionBack.getExercise()).isEqualTo(exercise);

        exercise.setQcmOptions(new HashSet<>());
        assertThat(exercise.getQcmOptions()).doesNotContain(qcmOptionBack);
        assertThat(qcmOptionBack.getExercise()).isNull();
    }

    @Test
    void blankSpacesTest() {
        Exercise exercise = getExerciseRandomSampleGenerator();
        BlankSpace blankSpaceBack = getBlankSpaceRandomSampleGenerator();

        exercise.addBlankSpaces(blankSpaceBack);
        assertThat(exercise.getBlankSpaces()).containsOnly(blankSpaceBack);
        assertThat(blankSpaceBack.getExercise()).isEqualTo(exercise);

        exercise.removeBlankSpaces(blankSpaceBack);
        assertThat(exercise.getBlankSpaces()).doesNotContain(blankSpaceBack);
        assertThat(blankSpaceBack.getExercise()).isNull();

        exercise.blankSpaces(new HashSet<>(Set.of(blankSpaceBack)));
        assertThat(exercise.getBlankSpaces()).containsOnly(blankSpaceBack);
        assertThat(blankSpaceBack.getExercise()).isEqualTo(exercise);

        exercise.setBlankSpaces(new HashSet<>());
        assertThat(exercise.getBlankSpaces()).doesNotContain(blankSpaceBack);
        assertThat(blankSpaceBack.getExercise()).isNull();
    }

    @Test
    void listItemsTest() {
        Exercise exercise = getExerciseRandomSampleGenerator();
        ListItem listItemBack = getListItemRandomSampleGenerator();

        exercise.addListItems(listItemBack);
        assertThat(exercise.getListItems()).containsOnly(listItemBack);
        assertThat(listItemBack.getExercise()).isEqualTo(exercise);

        exercise.removeListItems(listItemBack);
        assertThat(exercise.getListItems()).doesNotContain(listItemBack);
        assertThat(listItemBack.getExercise()).isNull();

        exercise.listItems(new HashSet<>(Set.of(listItemBack)));
        assertThat(exercise.getListItems()).containsOnly(listItemBack);
        assertThat(listItemBack.getExercise()).isEqualTo(exercise);

        exercise.setListItems(new HashSet<>());
        assertThat(exercise.getListItems()).doesNotContain(listItemBack);
        assertThat(listItemBack.getExercise()).isNull();
    }

    @Test
    void candidateAnswersTest() {
        Exercise exercise = getExerciseRandomSampleGenerator();
        CandidateAnswer candidateAnswerBack = getCandidateAnswerRandomSampleGenerator();

        exercise.addCandidateAnswers(candidateAnswerBack);
        assertThat(exercise.getCandidateAnswers()).containsOnly(candidateAnswerBack);
        assertThat(candidateAnswerBack.getExercise()).isEqualTo(exercise);

        exercise.removeCandidateAnswers(candidateAnswerBack);
        assertThat(exercise.getCandidateAnswers()).doesNotContain(candidateAnswerBack);
        assertThat(candidateAnswerBack.getExercise()).isNull();

        exercise.candidateAnswers(new HashSet<>(Set.of(candidateAnswerBack)));
        assertThat(exercise.getCandidateAnswers()).containsOnly(candidateAnswerBack);
        assertThat(candidateAnswerBack.getExercise()).isEqualTo(exercise);

        exercise.setCandidateAnswers(new HashSet<>());
        assertThat(exercise.getCandidateAnswers()).doesNotContain(candidateAnswerBack);
        assertThat(candidateAnswerBack.getExercise()).isNull();
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
}
