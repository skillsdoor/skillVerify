package com.skillsdoor.skillverify.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.skillsdoor.skillverify.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ExamSessionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExamSessionDTO.class);
        ExamSessionDTO examSessionDTO1 = new ExamSessionDTO();
        examSessionDTO1.setId(1L);
        ExamSessionDTO examSessionDTO2 = new ExamSessionDTO();
        assertThat(examSessionDTO1).isNotEqualTo(examSessionDTO2);
        examSessionDTO2.setId(examSessionDTO1.getId());
        assertThat(examSessionDTO1).isEqualTo(examSessionDTO2);
        examSessionDTO2.setId(2L);
        assertThat(examSessionDTO1).isNotEqualTo(examSessionDTO2);
        examSessionDTO1.setId(null);
        assertThat(examSessionDTO1).isNotEqualTo(examSessionDTO2);
    }
}
