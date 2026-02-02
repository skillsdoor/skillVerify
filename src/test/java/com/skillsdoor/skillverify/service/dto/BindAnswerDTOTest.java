package com.skillsdoor.skillverify.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.skillsdoor.skillverify.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BindAnswerDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BindAnswerDTO.class);
        BindAnswerDTO bindAnswerDTO1 = new BindAnswerDTO();
        bindAnswerDTO1.setId(1L);
        BindAnswerDTO bindAnswerDTO2 = new BindAnswerDTO();
        assertThat(bindAnswerDTO1).isNotEqualTo(bindAnswerDTO2);
        bindAnswerDTO2.setId(bindAnswerDTO1.getId());
        assertThat(bindAnswerDTO1).isEqualTo(bindAnswerDTO2);
        bindAnswerDTO2.setId(2L);
        assertThat(bindAnswerDTO1).isNotEqualTo(bindAnswerDTO2);
        bindAnswerDTO1.setId(null);
        assertThat(bindAnswerDTO1).isNotEqualTo(bindAnswerDTO2);
    }
}
