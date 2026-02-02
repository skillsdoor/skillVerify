package com.skillsdoor.skillverify.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.skillsdoor.skillverify.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BindListDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BindListDTO.class);
        BindListDTO bindListDTO1 = new BindListDTO();
        bindListDTO1.setId(1L);
        BindListDTO bindListDTO2 = new BindListDTO();
        assertThat(bindListDTO1).isNotEqualTo(bindListDTO2);
        bindListDTO2.setId(bindListDTO1.getId());
        assertThat(bindListDTO1).isEqualTo(bindListDTO2);
        bindListDTO2.setId(2L);
        assertThat(bindListDTO1).isNotEqualTo(bindListDTO2);
        bindListDTO1.setId(null);
        assertThat(bindListDTO1).isNotEqualTo(bindListDTO2);
    }
}
