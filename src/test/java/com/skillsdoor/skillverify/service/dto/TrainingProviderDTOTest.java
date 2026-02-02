package com.skillsdoor.skillverify.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.skillsdoor.skillverify.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TrainingProviderDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TrainingProviderDTO.class);
        TrainingProviderDTO trainingProviderDTO1 = new TrainingProviderDTO();
        trainingProviderDTO1.setId(1L);
        TrainingProviderDTO trainingProviderDTO2 = new TrainingProviderDTO();
        assertThat(trainingProviderDTO1).isNotEqualTo(trainingProviderDTO2);
        trainingProviderDTO2.setId(trainingProviderDTO1.getId());
        assertThat(trainingProviderDTO1).isEqualTo(trainingProviderDTO2);
        trainingProviderDTO2.setId(2L);
        assertThat(trainingProviderDTO1).isNotEqualTo(trainingProviderDTO2);
        trainingProviderDTO1.setId(null);
        assertThat(trainingProviderDTO1).isNotEqualTo(trainingProviderDTO2);
    }
}
