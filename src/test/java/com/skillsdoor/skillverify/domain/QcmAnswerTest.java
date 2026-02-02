package com.skillsdoor.skillverify.domain;

import static com.skillsdoor.skillverify.domain.CandidateAnswerTestSamples.*;
import static com.skillsdoor.skillverify.domain.QcmAnswerTestSamples.*;
import static com.skillsdoor.skillverify.domain.QcmOptionTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.skillsdoor.skillverify.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class QcmAnswerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(QcmAnswer.class);
        QcmAnswer qcmAnswer1 = getQcmAnswerSample1();
        QcmAnswer qcmAnswer2 = new QcmAnswer();
        assertThat(qcmAnswer1).isNotEqualTo(qcmAnswer2);

        qcmAnswer2.setId(qcmAnswer1.getId());
        assertThat(qcmAnswer1).isEqualTo(qcmAnswer2);

        qcmAnswer2 = getQcmAnswerSample2();
        assertThat(qcmAnswer1).isNotEqualTo(qcmAnswer2);
    }

    @Test
    void candidateAnswerTest() {
        QcmAnswer qcmAnswer = getQcmAnswerRandomSampleGenerator();
        CandidateAnswer candidateAnswerBack = getCandidateAnswerRandomSampleGenerator();

        qcmAnswer.setCandidateAnswer(candidateAnswerBack);
        assertThat(qcmAnswer.getCandidateAnswer()).isEqualTo(candidateAnswerBack);

        qcmAnswer.candidateAnswer(null);
        assertThat(qcmAnswer.getCandidateAnswer()).isNull();
    }

    @Test
    void qcmOptionTest() {
        QcmAnswer qcmAnswer = getQcmAnswerRandomSampleGenerator();
        QcmOption qcmOptionBack = getQcmOptionRandomSampleGenerator();

        qcmAnswer.setQcmOption(qcmOptionBack);
        assertThat(qcmAnswer.getQcmOption()).isEqualTo(qcmOptionBack);

        qcmAnswer.qcmOption(null);
        assertThat(qcmAnswer.getQcmOption()).isNull();
    }
}
