package com.skillsdoor.skillverify.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.skillsdoor.skillverify.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class QcmOptionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(QcmOptionDTO.class);
        QcmOptionDTO qcmOptionDTO1 = new QcmOptionDTO();
        qcmOptionDTO1.setId(1L);
        QcmOptionDTO qcmOptionDTO2 = new QcmOptionDTO();
        assertThat(qcmOptionDTO1).isNotEqualTo(qcmOptionDTO2);
        qcmOptionDTO2.setId(qcmOptionDTO1.getId());
        assertThat(qcmOptionDTO1).isEqualTo(qcmOptionDTO2);
        qcmOptionDTO2.setId(2L);
        assertThat(qcmOptionDTO1).isNotEqualTo(qcmOptionDTO2);
        qcmOptionDTO1.setId(null);
        assertThat(qcmOptionDTO1).isNotEqualTo(qcmOptionDTO2);
    }
}
