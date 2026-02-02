package com.skillsdoor.skillverify.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.skillsdoor.skillverify.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BlankAnswerDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BlankAnswerDTO.class);
        BlankAnswerDTO blankAnswerDTO1 = new BlankAnswerDTO();
        blankAnswerDTO1.setId(1L);
        BlankAnswerDTO blankAnswerDTO2 = new BlankAnswerDTO();
        assertThat(blankAnswerDTO1).isNotEqualTo(blankAnswerDTO2);
        blankAnswerDTO2.setId(blankAnswerDTO1.getId());
        assertThat(blankAnswerDTO1).isEqualTo(blankAnswerDTO2);
        blankAnswerDTO2.setId(2L);
        assertThat(blankAnswerDTO1).isNotEqualTo(blankAnswerDTO2);
        blankAnswerDTO1.setId(null);
        assertThat(blankAnswerDTO1).isNotEqualTo(blankAnswerDTO2);
    }
}
