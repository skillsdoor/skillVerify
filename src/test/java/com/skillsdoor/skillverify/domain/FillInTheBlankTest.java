package com.skillsdoor.skillverify.domain;

import static com.skillsdoor.skillverify.domain.FillInTheBlankTestSamples.*;
import static com.skillsdoor.skillverify.domain.QuestionTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.skillsdoor.skillverify.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FillInTheBlankTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FillInTheBlank.class);
        FillInTheBlank fillInTheBlank1 = getFillInTheBlankSample1();
        FillInTheBlank fillInTheBlank2 = new FillInTheBlank();
        assertThat(fillInTheBlank1).isNotEqualTo(fillInTheBlank2);

        fillInTheBlank2.setId(fillInTheBlank1.getId());
        assertThat(fillInTheBlank1).isEqualTo(fillInTheBlank2);

        fillInTheBlank2 = getFillInTheBlankSample2();
        assertThat(fillInTheBlank1).isNotEqualTo(fillInTheBlank2);
    }

    @Test
    void questionTest() {
        FillInTheBlank fillInTheBlank = getFillInTheBlankRandomSampleGenerator();
        Question questionBack = getQuestionRandomSampleGenerator();

        fillInTheBlank.setQuestion(questionBack);
        assertThat(fillInTheBlank.getQuestion()).isEqualTo(questionBack);

        fillInTheBlank.question(null);
        assertThat(fillInTheBlank.getQuestion()).isNull();
    }
}
