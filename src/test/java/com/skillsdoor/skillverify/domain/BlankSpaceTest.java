package com.skillsdoor.skillverify.domain;

import static com.skillsdoor.skillverify.domain.BlankAnswerTestSamples.*;
import static com.skillsdoor.skillverify.domain.BlankSpaceTestSamples.*;
import static com.skillsdoor.skillverify.domain.ExerciseTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.skillsdoor.skillverify.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class BlankSpaceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BlankSpace.class);
        BlankSpace blankSpace1 = getBlankSpaceSample1();
        BlankSpace blankSpace2 = new BlankSpace();
        assertThat(blankSpace1).isNotEqualTo(blankSpace2);

        blankSpace2.setId(blankSpace1.getId());
        assertThat(blankSpace1).isEqualTo(blankSpace2);

        blankSpace2 = getBlankSpaceSample2();
        assertThat(blankSpace1).isNotEqualTo(blankSpace2);
    }

    @Test
    void blankAnswersTest() {
        BlankSpace blankSpace = getBlankSpaceRandomSampleGenerator();
        BlankAnswer blankAnswerBack = getBlankAnswerRandomSampleGenerator();

        blankSpace.addBlankAnswers(blankAnswerBack);
        assertThat(blankSpace.getBlankAnswers()).containsOnly(blankAnswerBack);
        assertThat(blankAnswerBack.getBlankSpace()).isEqualTo(blankSpace);

        blankSpace.removeBlankAnswers(blankAnswerBack);
        assertThat(blankSpace.getBlankAnswers()).doesNotContain(blankAnswerBack);
        assertThat(blankAnswerBack.getBlankSpace()).isNull();

        blankSpace.blankAnswers(new HashSet<>(Set.of(blankAnswerBack)));
        assertThat(blankSpace.getBlankAnswers()).containsOnly(blankAnswerBack);
        assertThat(blankAnswerBack.getBlankSpace()).isEqualTo(blankSpace);

        blankSpace.setBlankAnswers(new HashSet<>());
        assertThat(blankSpace.getBlankAnswers()).doesNotContain(blankAnswerBack);
        assertThat(blankAnswerBack.getBlankSpace()).isNull();
    }

    @Test
    void exerciseTest() {
        BlankSpace blankSpace = getBlankSpaceRandomSampleGenerator();
        Exercise exerciseBack = getExerciseRandomSampleGenerator();

        blankSpace.setExercise(exerciseBack);
        assertThat(blankSpace.getExercise()).isEqualTo(exerciseBack);

        blankSpace.exercise(null);
        assertThat(blankSpace.getExercise()).isNull();
    }
}
