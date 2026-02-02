package com.skillsdoor.skillverify.domain;

import static com.skillsdoor.skillverify.domain.BindListAnswerTestSamples.*;
import static com.skillsdoor.skillverify.domain.CandidateAnswerTestSamples.*;
import static com.skillsdoor.skillverify.domain.ExamSessionTestSamples.*;
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
    void questionTest() {
        CandidateAnswer candidateAnswer = getCandidateAnswerRandomSampleGenerator();
        Question questionBack = getQuestionRandomSampleGenerator();

        candidateAnswer.setQuestion(questionBack);
        assertThat(candidateAnswer.getQuestion()).isEqualTo(questionBack);

        candidateAnswer.question(null);
        assertThat(candidateAnswer.getQuestion()).isNull();
    }
}
