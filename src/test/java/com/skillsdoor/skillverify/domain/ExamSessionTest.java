package com.skillsdoor.skillverify.domain;

import static com.skillsdoor.skillverify.domain.CandidateAnswerTestSamples.*;
import static com.skillsdoor.skillverify.domain.CandidateTestSamples.*;
import static com.skillsdoor.skillverify.domain.ExamSessionTestSamples.*;
import static com.skillsdoor.skillverify.domain.ExamTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.skillsdoor.skillverify.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ExamSessionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExamSession.class);
        ExamSession examSession1 = getExamSessionSample1();
        ExamSession examSession2 = new ExamSession();
        assertThat(examSession1).isNotEqualTo(examSession2);

        examSession2.setId(examSession1.getId());
        assertThat(examSession1).isEqualTo(examSession2);

        examSession2 = getExamSessionSample2();
        assertThat(examSession1).isNotEqualTo(examSession2);
    }

    @Test
    void candidateAnswersTest() {
        ExamSession examSession = getExamSessionRandomSampleGenerator();
        CandidateAnswer candidateAnswerBack = getCandidateAnswerRandomSampleGenerator();

        examSession.addCandidateAnswers(candidateAnswerBack);
        assertThat(examSession.getCandidateAnswers()).containsOnly(candidateAnswerBack);
        assertThat(candidateAnswerBack.getExamSession()).isEqualTo(examSession);

        examSession.removeCandidateAnswers(candidateAnswerBack);
        assertThat(examSession.getCandidateAnswers()).doesNotContain(candidateAnswerBack);
        assertThat(candidateAnswerBack.getExamSession()).isNull();

        examSession.candidateAnswers(new HashSet<>(Set.of(candidateAnswerBack)));
        assertThat(examSession.getCandidateAnswers()).containsOnly(candidateAnswerBack);
        assertThat(candidateAnswerBack.getExamSession()).isEqualTo(examSession);

        examSession.setCandidateAnswers(new HashSet<>());
        assertThat(examSession.getCandidateAnswers()).doesNotContain(candidateAnswerBack);
        assertThat(candidateAnswerBack.getExamSession()).isNull();
    }

    @Test
    void candidateTest() {
        ExamSession examSession = getExamSessionRandomSampleGenerator();
        Candidate candidateBack = getCandidateRandomSampleGenerator();

        examSession.setCandidate(candidateBack);
        assertThat(examSession.getCandidate()).isEqualTo(candidateBack);

        examSession.candidate(null);
        assertThat(examSession.getCandidate()).isNull();
    }

    @Test
    void examTest() {
        ExamSession examSession = getExamSessionRandomSampleGenerator();
        Exam examBack = getExamRandomSampleGenerator();

        examSession.setExam(examBack);
        assertThat(examSession.getExam()).isEqualTo(examBack);

        examSession.exam(null);
        assertThat(examSession.getExam()).isNull();
    }
}
