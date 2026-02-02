package com.skillsdoor.skillverify.domain;

import static com.skillsdoor.skillverify.domain.ExerciseTestSamples.*;
import static com.skillsdoor.skillverify.domain.ListItemTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.skillsdoor.skillverify.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ListItemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ListItem.class);
        ListItem listItem1 = getListItemSample1();
        ListItem listItem2 = new ListItem();
        assertThat(listItem1).isNotEqualTo(listItem2);

        listItem2.setId(listItem1.getId());
        assertThat(listItem1).isEqualTo(listItem2);

        listItem2 = getListItemSample2();
        assertThat(listItem1).isNotEqualTo(listItem2);
    }

    @Test
    void exerciseTest() {
        ListItem listItem = getListItemRandomSampleGenerator();
        Exercise exerciseBack = getExerciseRandomSampleGenerator();

        listItem.setExercise(exerciseBack);
        assertThat(listItem.getExercise()).isEqualTo(exerciseBack);

        listItem.exercise(null);
        assertThat(listItem.getExercise()).isNull();
    }
}
