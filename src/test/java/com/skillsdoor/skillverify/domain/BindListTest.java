package com.skillsdoor.skillverify.domain;

import static com.skillsdoor.skillverify.domain.BindListTestSamples.*;
import static com.skillsdoor.skillverify.domain.QuestionTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.skillsdoor.skillverify.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BindListTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BindList.class);
        BindList bindList1 = getBindListSample1();
        BindList bindList2 = new BindList();
        assertThat(bindList1).isNotEqualTo(bindList2);

        bindList2.setId(bindList1.getId());
        assertThat(bindList1).isEqualTo(bindList2);

        bindList2 = getBindListSample2();
        assertThat(bindList1).isNotEqualTo(bindList2);
    }

    @Test
    void questionTest() {
        BindList bindList = getBindListRandomSampleGenerator();
        Question questionBack = getQuestionRandomSampleGenerator();

        bindList.setQuestion(questionBack);
        assertThat(bindList.getQuestion()).isEqualTo(questionBack);

        bindList.question(null);
        assertThat(bindList.getQuestion()).isNull();
    }
}
