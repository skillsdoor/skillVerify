package com.skillsdoor.skillverify.domain;

import static com.skillsdoor.skillverify.domain.BindAnswerTestSamples.*;
import static com.skillsdoor.skillverify.domain.BindListAnswerTestSamples.*;
import static com.skillsdoor.skillverify.domain.BlankAnswerTestSamples.*;
import static com.skillsdoor.skillverify.domain.CandidateAnswerTestSamples.*;
import static com.skillsdoor.skillverify.domain.ExamSessionTestSamples.*;
import static com.skillsdoor.skillverify.domain.ExerciseTestSamples.*;
import static com.skillsdoor.skillverify.domain.QcmAnswerTestSamples.*;
import static com.skillsdoor.skillverify.domain.QuestionTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.skillsdoor.skillverify.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CandidateAnswerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CandidateAnswer.class);
        CandidateAnswer candidateAnswer1 = getCandidateAnswerSample1();
        CandidateAnswer candidateAnswer2 = new CandidateAnswer();
        assertThat(candidateAnswer1).isNotEqualTo(candidateAnswer2);

        candidateAnswer2.setId(candidateAnswer1.getId());
        assertThat(candidateAnswer1).isEqualTo(candidateAnswer2);

        candidateAnswer2 = getCandidateAnswerSample2();
        assertThat(candidateAnswer1).isNotEqualTo(candidateAnswer2);
    }

    @Test
    void qcmAnswersTest() {
        CandidateAnswer candidateAnswer = getCandidateAnswerRandomSampleGenerator();
        QcmAnswer qcmAnswerBack = getQcmAnswerRandomSampleGenerator();

        candidateAnswer.addQcmAnswers(qcmAnswerBack);
        assertThat(candidateAnswer.getQcmAnswers()).containsOnly(qcmAnswerBack);
        assertThat(qcmAnswerBack.getCandidateAnswer()).isEqualTo(candidateAnswer);

        candidateAnswer.removeQcmAnswers(qcmAnswerBack);
        assertThat(candidateAnswer.getQcmAnswers()).doesNotContain(qcmAnswerBack);
        assertThat(qcmAnswerBack.getCandidateAnswer()).isNull();

        candidateAnswer.qcmAnswers(new HashSet<>(Set.of(qcmAnswerBack)));
        assertThat(candidateAnswer.getQcmAnswers()).containsOnly(qcmAnswerBack);
        assertThat(qcmAnswerBack.getCandidateAnswer()).isEqualTo(candidateAnswer);

        candidateAnswer.setQcmAnswers(new HashSet<>());
        assertThat(candidateAnswer.getQcmAnswers()).doesNotContain(qcmAnswerBack);
        assertThat(qcmAnswerBack.getCandidateAnswer()).isNull();
    }

    @Test
    void blankAnswersTest() {
        CandidateAnswer candidateAnswer = getCandidateAnswerRandomSampleGenerator();
        BlankAnswer blankAnswerBack = getBlankAnswerRandomSampleGenerator();

        candidateAnswer.addBlankAnswers(blankAnswerBack);
        assertThat(candidateAnswer.getBlankAnswers()).containsOnly(blankAnswerBack);
        assertThat(blankAnswerBack.getCandidateAnswer()).isEqualTo(candidateAnswer);

        candidateAnswer.removeBlankAnswers(blankAnswerBack);
        assertThat(candidateAnswer.getBlankAnswers()).doesNotContain(blankAnswerBack);
        assertThat(blankAnswerBack.getCandidateAnswer()).isNull();

        candidateAnswer.blankAnswers(new HashSet<>(Set.of(blankAnswerBack)));
        assertThat(candidateAnswer.getBlankAnswers()).containsOnly(blankAnswerBack);
        assertThat(blankAnswerBack.getCandidateAnswer()).isEqualTo(candidateAnswer);

        candidateAnswer.setBlankAnswers(new HashSet<>());
        assertThat(candidateAnswer.getBlankAnswers()).doesNotContain(blankAnswerBack);
        assertThat(blankAnswerBack.getCandidateAnswer()).isNull();
    }

    @Test
    void bindAnswersTest() {
        CandidateAnswer candidateAnswer = getCandidateAnswerRandomSampleGenerator();
        BindAnswer bindAnswerBack = getBindAnswerRandomSampleGenerator();

        candidateAnswer.addBindAnswers(bindAnswerBack);
        assertThat(candidateAnswer.getBindAnswers()).containsOnly(bindAnswerBack);
        assertThat(bindAnswerBack.getCandidateAnswer()).isEqualTo(candidateAnswer);

        candidateAnswer.removeBindAnswers(bindAnswerBack);
        assertThat(candidateAnswer.getBindAnswers()).doesNotContain(bindAnswerBack);
        assertThat(bindAnswerBack.getCandidateAnswer()).isNull();

        candidateAnswer.bindAnswers(new HashSet<>(Set.of(bindAnswerBack)));
        assertThat(candidateAnswer.getBindAnswers()).containsOnly(bindAnswerBack);
        assertThat(bindAnswerBack.getCandidateAnswer()).isEqualTo(candidateAnswer);

        candidateAnswer.setBindAnswers(new HashSet<>());
        assertThat(candidateAnswer.getBindAnswers()).doesNotContain(bindAnswerBack);
        assertThat(bindAnswerBack.getCandidateAnswer()).isNull();
    }

    @Test
    void examSessionTest() {
        CandidateAnswer candidateAnswer = getCandidateAnswerRandomSampleGenerator();
        ExamSession examSessionBack = getExamSessionRandomSampleGenerator();

        candidateAnswer.setExamSession(examSessionBack);
        assertThat(candidateAnswer.getExamSession()).isEqualTo(examSessionBack);

        candidateAnswer.examSession(null);
        assertThat(candidateAnswer.getExamSession()).isNull();
    }

    @Test
    void exerciseTest() {
        CandidateAnswer candidateAnswer = getCandidateAnswerRandomSampleGenerator();
        Exercise exerciseBack = getExerciseRandomSampleGenerator();

        candidateAnswer.setExercise(exerciseBack);
        assertThat(candidateAnswer.getExercise()).isEqualTo(exerciseBack);

        candidateAnswer.exercise(null);
        assertThat(candidateAnswer.getExercise()).isNull();
    }

    @Test
    void questionTest() {
        CandidateAnswer candidateAnswer = getCandidateAnswerRandomSampleGenerator();
        Question questionBack = getQuestionRandomSampleGenerator();

        candidateAnswer.setQuestion(questionBack);
        assertThat(candidateAnswer.getQuestion()).isEqualTo(questionBack);

        candidateAnswer.question(null);
        assertThat(candidateAnswer.getQuestion()).isNull();
    }

    @Test
    void bindListAnswersTest() {
        CandidateAnswer candidateAnswer = getCandidateAnswerRandomSampleGenerator();
        BindListAnswer bindListAnswerBack = getBindListAnswerRandomSampleGenerator();

        candidateAnswer.addBindListAnswers(bindListAnswerBack);
        assertThat(candidateAnswer.getBindListAnswers()).containsOnly(bindListAnswerBack);
        assertThat(bindListAnswerBack.getCandidateAnswer()).isEqualTo(candidateAnswer);

        candidateAnswer.removeBindListAnswers(bindListAnswerBack);
        assertThat(candidateAnswer.getBindListAnswers()).doesNotContain(bindListAnswerBack);
        assertThat(bindListAnswerBack.getCandidateAnswer()).isNull();

        candidateAnswer.bindListAnswers(new HashSet<>(Set.of(bindListAnswerBack)));
        assertThat(candidateAnswer.getBindListAnswers()).containsOnly(bindListAnswerBack);
        assertThat(bindListAnswerBack.getCandidateAnswer()).isEqualTo(candidateAnswer);

        candidateAnswer.setBindListAnswers(new HashSet<>());
        assertThat(candidateAnswer.getBindListAnswers()).doesNotContain(bindListAnswerBack);
        assertThat(bindListAnswerBack.getCandidateAnswer()).isNull();
    }
}
