package com.skillsdoor.skillverify.domain;

import static com.skillsdoor.skillverify.domain.AnswerOptionTestSamples.*;
import static com.skillsdoor.skillverify.domain.QuestionTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.skillsdoor.skillverify.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AnswerOptionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnswerOption.class);
        AnswerOption answerOption1 = getAnswerOptionSample1();
        AnswerOption answerOption2 = new AnswerOption();
        assertThat(answerOption1).isNotEqualTo(answerOption2);

        answerOption2.setId(answerOption1.getId());
        assertThat(answerOption1).isEqualTo(answerOption2);

        answerOption2 = getAnswerOptionSample2();
        assertThat(answerOption1).isNotEqualTo(answerOption2);
    }

    @Test
    void questionTest() {
        AnswerOption answerOption = getAnswerOptionRandomSampleGenerator();
        Question questionBack = getQuestionRandomSampleGenerator();

        answerOption.setQuestion(questionBack);
        assertThat(answerOption.getQuestion()).isEqualTo(questionBack);

        answerOption.question(null);
        assertThat(answerOption.getQuestion()).isNull();
    }
}
