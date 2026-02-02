package com.skillsdoor.skillverify.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.skillsdoor.skillverify.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class QcmAnswerDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(QcmAnswerDTO.class);
        QcmAnswerDTO qcmAnswerDTO1 = new QcmAnswerDTO();
        qcmAnswerDTO1.setId(1L);
        QcmAnswerDTO qcmAnswerDTO2 = new QcmAnswerDTO();
        assertThat(qcmAnswerDTO1).isNotEqualTo(qcmAnswerDTO2);
        qcmAnswerDTO2.setId(qcmAnswerDTO1.getId());
        assertThat(qcmAnswerDTO1).isEqualTo(qcmAnswerDTO2);
        qcmAnswerDTO2.setId(2L);
        assertThat(qcmAnswerDTO1).isNotEqualTo(qcmAnswerDTO2);
        qcmAnswerDTO1.setId(null);
        assertThat(qcmAnswerDTO1).isNotEqualTo(qcmAnswerDTO2);
    }
}
