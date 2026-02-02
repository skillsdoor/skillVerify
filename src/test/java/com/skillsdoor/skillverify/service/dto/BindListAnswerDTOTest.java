package com.skillsdoor.skillverify.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.skillsdoor.skillverify.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BindListAnswerDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BindListAnswerDTO.class);
        BindListAnswerDTO bindListAnswerDTO1 = new BindListAnswerDTO();
        bindListAnswerDTO1.setId(1L);
        BindListAnswerDTO bindListAnswerDTO2 = new BindListAnswerDTO();
        assertThat(bindListAnswerDTO1).isNotEqualTo(bindListAnswerDTO2);
        bindListAnswerDTO2.setId(bindListAnswerDTO1.getId());
        assertThat(bindListAnswerDTO1).isEqualTo(bindListAnswerDTO2);
        bindListAnswerDTO2.setId(2L);
        assertThat(bindListAnswerDTO1).isNotEqualTo(bindListAnswerDTO2);
        bindListAnswerDTO1.setId(null);
        assertThat(bindListAnswerDTO1).isNotEqualTo(bindListAnswerDTO2);
    }
}
