package com.skillsdoor.skillverify.domain;

import static com.skillsdoor.skillverify.domain.AnswerOptionTestSamples.*;
import static com.skillsdoor.skillverify.domain.BindListTestSamples.*;
import static com.skillsdoor.skillverify.domain.CandidateAnswerTestSamples.*;
import static com.skillsdoor.skillverify.domain.ExerciseTestSamples.*;
import static com.skillsdoor.skillverify.domain.FillInTheBlankTestSamples.*;
import static com.skillsdoor.skillverify.domain.MediaTestSamples.*;
import static com.skillsdoor.skillverify.domain.QuestionTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.skillsdoor.skillverify.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class QuestionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Question.class);
        Question question1 = getQuestionSample1();
        Question question2 = new Question();
        assertThat(question1).isNotEqualTo(question2);

        question2.setId(question1.getId());
        assertThat(question1).isEqualTo(question2);

        question2 = getQuestionSample2();
        assertThat(question1).isNotEqualTo(question2);
    }

    @Test
    void mediaTest() {
        Question question = getQuestionRandomSampleGenerator();
        Media mediaBack = getMediaRandomSampleGenerator();

        question.addMedia(mediaBack);
        assertThat(question.getMedia()).containsOnly(mediaBack);
        assertThat(mediaBack.getQuestion()).isEqualTo(question);

        question.removeMedia(mediaBack);
        assertThat(question.getMedia()).doesNotContain(mediaBack);
        assertThat(mediaBack.getQuestion()).isNull();

        question.media(new HashSet<>(Set.of(mediaBack)));
        assertThat(question.getMedia()).containsOnly(mediaBack);
        assertThat(mediaBack.getQuestion()).isEqualTo(question);

        question.setMedia(new HashSet<>());
        assertThat(question.getMedia()).doesNotContain(mediaBack);
        assertThat(mediaBack.getQuestion()).isNull();
    }

    @Test
    void answerOptionsTest() {
        Question question = getQuestionRandomSampleGenerator();
        AnswerOption answerOptionBack = getAnswerOptionRandomSampleGenerator();

        question.addAnswerOptions(answerOptionBack);
        assertThat(question.getAnswerOptions()).containsOnly(answerOptionBack);
        assertThat(answerOptionBack.getQuestion()).isEqualTo(question);

        question.removeAnswerOptions(answerOptionBack);
        assertThat(question.getAnswerOptions()).doesNotContain(answerOptionBack);
        assertThat(answerOptionBack.getQuestion()).isNull();

        question.answerOptions(new HashSet<>(Set.of(answerOptionBack)));
        assertThat(question.getAnswerOptions()).containsOnly(answerOptionBack);
        assertThat(answerOptionBack.getQuestion()).isEqualTo(question);

        question.setAnswerOptions(new HashSet<>());
        assertThat(question.getAnswerOptions()).doesNotContain(answerOptionBack);
        assertThat(answerOptionBack.getQuestion()).isNull();
    }

    @Test
    void fillInTheBlanksTest() {
        Question question = getQuestionRandomSampleGenerator();
        FillInTheBlank fillInTheBlankBack = getFillInTheBlankRandomSampleGenerator();

        question.addFillInTheBlanks(fillInTheBlankBack);
        assertThat(question.getFillInTheBlanks()).containsOnly(fillInTheBlankBack);
        assertThat(fillInTheBlankBack.getQuestion()).isEqualTo(question);

        question.removeFillInTheBlanks(fillInTheBlankBack);
        assertThat(question.getFillInTheBlanks()).doesNotContain(fillInTheBlankBack);
        assertThat(fillInTheBlankBack.getQuestion()).isNull();

        question.fillInTheBlanks(new HashSet<>(Set.of(fillInTheBlankBack)));
        assertThat(question.getFillInTheBlanks()).containsOnly(fillInTheBlankBack);
        assertThat(fillInTheBlankBack.getQuestion()).isEqualTo(question);

        question.setFillInTheBlanks(new HashSet<>());
        assertThat(question.getFillInTheBlanks()).doesNotContain(fillInTheBlankBack);
        assertThat(fillInTheBlankBack.getQuestion()).isNull();
    }

    @Test
    void bindListsTest() {
        Question question = getQuestionRandomSampleGenerator();
        BindList bindListBack = getBindListRandomSampleGenerator();

        question.addBindLists(bindListBack);
        assertThat(question.getBindLists()).containsOnly(bindListBack);
        assertThat(bindListBack.getQuestion()).isEqualTo(question);

        question.removeBindLists(bindListBack);
        assertThat(question.getBindLists()).doesNotContain(bindListBack);
        assertThat(bindListBack.getQuestion()).isNull();

        question.bindLists(new HashSet<>(Set.of(bindListBack)));
        assertThat(question.getBindLists()).containsOnly(bindListBack);
        assertThat(bindListBack.getQuestion()).isEqualTo(question);

        question.setBindLists(new HashSet<>());
        assertThat(question.getBindLists()).doesNotContain(bindListBack);
        assertThat(bindListBack.getQuestion()).isNull();
    }

    @Test
    void candidateAnswersTest() {
        Question question = getQuestionRandomSampleGenerator();
        CandidateAnswer candidateAnswerBack = getCandidateAnswerRandomSampleGenerator();

        question.addCandidateAnswers(candidateAnswerBack);
        assertThat(question.getCandidateAnswers()).containsOnly(candidateAnswerBack);
        assertThat(candidateAnswerBack.getQuestion()).isEqualTo(question);

        question.removeCandidateAnswers(candidateAnswerBack);
        assertThat(question.getCandidateAnswers()).doesNotContain(candidateAnswerBack);
        assertThat(candidateAnswerBack.getQuestion()).isNull();

        question.candidateAnswers(new HashSet<>(Set.of(candidateAnswerBack)));
        assertThat(question.getCandidateAnswers()).containsOnly(candidateAnswerBack);
        assertThat(candidateAnswerBack.getQuestion()).isEqualTo(question);

        question.setCandidateAnswers(new HashSet<>());
        assertThat(question.getCandidateAnswers()).doesNotContain(candidateAnswerBack);
        assertThat(candidateAnswerBack.getQuestion()).isNull();
    }

    @Test
    void exerciseTest() {
        Question question = getQuestionRandomSampleGenerator();
        Exercise exerciseBack = getExerciseRandomSampleGenerator();

        question.setExercise(exerciseBack);
        assertThat(question.getExercise()).isEqualTo(exerciseBack);

        question.exercise(null);
        assertThat(question.getExercise()).isNull();
    }
}
