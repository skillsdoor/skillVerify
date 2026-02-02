package com.skillsdoor.skillverify.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.skillsdoor.skillverify.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AnswerOptionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnswerOptionDTO.class);
        AnswerOptionDTO answerOptionDTO1 = new AnswerOptionDTO();
        answerOptionDTO1.setId(1L);
        AnswerOptionDTO answerOptionDTO2 = new AnswerOptionDTO();
        assertThat(answerOptionDTO1).isNotEqualTo(answerOptionDTO2);
        answerOptionDTO2.setId(answerOptionDTO1.getId());
        assertThat(answerOptionDTO1).isEqualTo(answerOptionDTO2);
        answerOptionDTO2.setId(2L);
        assertThat(answerOptionDTO1).isNotEqualTo(answerOptionDTO2);
        answerOptionDTO1.setId(null);
        assertThat(answerOptionDTO1).isNotEqualTo(answerOptionDTO2);
    }
}
