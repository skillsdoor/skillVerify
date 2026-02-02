package com.skillsdoor.skillverify.domain;

import static com.skillsdoor.skillverify.domain.BindListAnswerTestSamples.*;
import static com.skillsdoor.skillverify.domain.CandidateAnswerTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.skillsdoor.skillverify.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BindListAnswerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BindListAnswer.class);
        BindListAnswer bindListAnswer1 = getBindListAnswerSample1();
        BindListAnswer bindListAnswer2 = new BindListAnswer();
        assertThat(bindListAnswer1).isNotEqualTo(bindListAnswer2);

        bindListAnswer2.setId(bindListAnswer1.getId());
        assertThat(bindListAnswer1).isEqualTo(bindListAnswer2);

        bindListAnswer2 = getBindListAnswerSample2();
        assertThat(bindListAnswer1).isNotEqualTo(bindListAnswer2);
    }

    @Test
    void candidateAnswerTest() {
        BindListAnswer bindListAnswer = getBindListAnswerRandomSampleGenerator();
        CandidateAnswer candidateAnswerBack = getCandidateAnswerRandomSampleGenerator();

        bindListAnswer.setCandidateAnswer(candidateAnswerBack);
        assertThat(bindListAnswer.getCandidateAnswer()).isEqualTo(candidateAnswerBack);

        bindListAnswer.candidateAnswer(null);
        assertThat(bindListAnswer.getCandidateAnswer()).isNull();
    }
}
