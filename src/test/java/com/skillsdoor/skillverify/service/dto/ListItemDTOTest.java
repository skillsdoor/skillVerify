package com.skillsdoor.skillverify.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.skillsdoor.skillverify.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ListItemDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ListItemDTO.class);
        ListItemDTO listItemDTO1 = new ListItemDTO();
        listItemDTO1.setId(1L);
        ListItemDTO listItemDTO2 = new ListItemDTO();
        assertThat(listItemDTO1).isNotEqualTo(listItemDTO2);
        listItemDTO2.setId(listItemDTO1.getId());
        assertThat(listItemDTO1).isEqualTo(listItemDTO2);
        listItemDTO2.setId(2L);
        assertThat(listItemDTO1).isNotEqualTo(listItemDTO2);
        listItemDTO1.setId(null);
        assertThat(listItemDTO1).isNotEqualTo(listItemDTO2);
    }
}
