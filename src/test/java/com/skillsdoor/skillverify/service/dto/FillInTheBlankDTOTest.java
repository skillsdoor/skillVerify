package com.skillsdoor.skillverify.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.skillsdoor.skillverify.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FillInTheBlankDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FillInTheBlankDTO.class);
        FillInTheBlankDTO fillInTheBlankDTO1 = new FillInTheBlankDTO();
        fillInTheBlankDTO1.setId(1L);
        FillInTheBlankDTO fillInTheBlankDTO2 = new FillInTheBlankDTO();
        assertThat(fillInTheBlankDTO1).isNotEqualTo(fillInTheBlankDTO2);
        fillInTheBlankDTO2.setId(fillInTheBlankDTO1.getId());
        assertThat(fillInTheBlankDTO1).isEqualTo(fillInTheBlankDTO2);
        fillInTheBlankDTO2.setId(2L);
        assertThat(fillInTheBlankDTO1).isNotEqualTo(fillInTheBlankDTO2);
        fillInTheBlankDTO1.setId(null);
        assertThat(fillInTheBlankDTO1).isNotEqualTo(fillInTheBlankDTO2);
    }
}
