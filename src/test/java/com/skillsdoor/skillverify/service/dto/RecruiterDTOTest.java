package com.skillsdoor.skillverify.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.skillsdoor.skillverify.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RecruiterDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecruiterDTO.class);
        RecruiterDTO recruiterDTO1 = new RecruiterDTO();
        recruiterDTO1.setId(1L);
        RecruiterDTO recruiterDTO2 = new RecruiterDTO();
        assertThat(recruiterDTO1).isNotEqualTo(recruiterDTO2);
        recruiterDTO2.setId(recruiterDTO1.getId());
        assertThat(recruiterDTO1).isEqualTo(recruiterDTO2);
        recruiterDTO2.setId(2L);
        assertThat(recruiterDTO1).isNotEqualTo(recruiterDTO2);
        recruiterDTO1.setId(null);
        assertThat(recruiterDTO1).isNotEqualTo(recruiterDTO2);
    }
}
