package com.skillsdoor.skillverify.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.skillsdoor.skillverify.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BlankSpaceDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BlankSpaceDTO.class);
        BlankSpaceDTO blankSpaceDTO1 = new BlankSpaceDTO();
        blankSpaceDTO1.setId(1L);
        BlankSpaceDTO blankSpaceDTO2 = new BlankSpaceDTO();
        assertThat(blankSpaceDTO1).isNotEqualTo(blankSpaceDTO2);
        blankSpaceDTO2.setId(blankSpaceDTO1.getId());
        assertThat(blankSpaceDTO1).isEqualTo(blankSpaceDTO2);
        blankSpaceDTO2.setId(2L);
        assertThat(blankSpaceDTO1).isNotEqualTo(blankSpaceDTO2);
        blankSpaceDTO1.setId(null);
        assertThat(blankSpaceDTO1).isNotEqualTo(blankSpaceDTO2);
    }
}
