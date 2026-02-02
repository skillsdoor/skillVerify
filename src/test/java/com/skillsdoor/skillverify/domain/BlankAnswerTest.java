package com.skillsdoor.skillverify.domain;

import static com.skillsdoor.skillverify.domain.BlankAnswerTestSamples.*;
import static com.skillsdoor.skillverify.domain.BlankSpaceTestSamples.*;
import static com.skillsdoor.skillverify.domain.CandidateAnswerTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.skillsdoor.skillverify.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BlankAnswerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BlankAnswer.class);
        BlankAnswer blankAnswer1 = getBlankAnswerSample1();
        BlankAnswer blankAnswer2 = new BlankAnswer();
        assertThat(blankAnswer1).isNotEqualTo(blankAnswer2);

        blankAnswer2.setId(blankAnswer1.getId());
        assertThat(blankAnswer1).isEqualTo(blankAnswer2);

        blankAnswer2 = getBlankAnswerSample2();
        assertThat(blankAnswer1).isNotEqualTo(blankAnswer2);
    }

    @Test
    void candidateAnswerTest() {
        BlankAnswer blankAnswer = getBlankAnswerRandomSampleGenerator();
        CandidateAnswer candidateAnswerBack = getCandidateAnswerRandomSampleGenerator();

        blankAnswer.setCandidateAnswer(candidateAnswerBack);
        assertThat(blankAnswer.getCandidateAnswer()).isEqualTo(candidateAnswerBack);

        blankAnswer.candidateAnswer(null);
        assertThat(blankAnswer.getCandidateAnswer()).isNull();
    }

    @Test
    void blankSpaceTest() {
        BlankAnswer blankAnswer = getBlankAnswerRandomSampleGenerator();
        BlankSpace blankSpaceBack = getBlankSpaceRandomSampleGenerator();

        blankAnswer.setBlankSpace(blankSpaceBack);
        assertThat(blankAnswer.getBlankSpace()).isEqualTo(blankSpaceBack);

        blankAnswer.blankSpace(null);
        assertThat(blankAnswer.getBlankSpace()).isNull();
    }
}
