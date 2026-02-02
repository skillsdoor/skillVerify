package com.skillsdoor.skillverify.domain;

import static com.skillsdoor.skillverify.domain.BindAnswerTestSamples.*;
import static com.skillsdoor.skillverify.domain.CandidateAnswerTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.skillsdoor.skillverify.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BindAnswerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BindAnswer.class);
        BindAnswer bindAnswer1 = getBindAnswerSample1();
        BindAnswer bindAnswer2 = new BindAnswer();
        assertThat(bindAnswer1).isNotEqualTo(bindAnswer2);

        bindAnswer2.setId(bindAnswer1.getId());
        assertThat(bindAnswer1).isEqualTo(bindAnswer2);

        bindAnswer2 = getBindAnswerSample2();
        assertThat(bindAnswer1).isNotEqualTo(bindAnswer2);
    }

    @Test
    void candidateAnswerTest() {
        BindAnswer bindAnswer = getBindAnswerRandomSampleGenerator();
        CandidateAnswer candidateAnswerBack = getCandidateAnswerRandomSampleGenerator();

        bindAnswer.setCandidateAnswer(candidateAnswerBack);
        assertThat(bindAnswer.getCandidateAnswer()).isEqualTo(candidateAnswerBack);

        bindAnswer.candidateAnswer(null);
        assertThat(bindAnswer.getCandidateAnswer()).isNull();
    }
}
