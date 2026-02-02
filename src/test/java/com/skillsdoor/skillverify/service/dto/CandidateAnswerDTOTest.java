package com.skillsdoor.skillverify.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.skillsdoor.skillverify.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CandidateAnswerDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CandidateAnswerDTO.class);
        CandidateAnswerDTO candidateAnswerDTO1 = new CandidateAnswerDTO();
        candidateAnswerDTO1.setId(1L);
        CandidateAnswerDTO candidateAnswerDTO2 = new CandidateAnswerDTO();
        assertThat(candidateAnswerDTO1).isNotEqualTo(candidateAnswerDTO2);
        candidateAnswerDTO2.setId(candidateAnswerDTO1.getId());
        assertThat(candidateAnswerDTO1).isEqualTo(candidateAnswerDTO2);
        candidateAnswerDTO2.setId(2L);
        assertThat(candidateAnswerDTO1).isNotEqualTo(candidateAnswerDTO2);
        candidateAnswerDTO1.setId(null);
        assertThat(candidateAnswerDTO1).isNotEqualTo(candidateAnswerDTO2);
    }
}
