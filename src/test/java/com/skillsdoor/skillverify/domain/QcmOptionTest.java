package com.skillsdoor.skillverify.domain;

import static com.skillsdoor.skillverify.domain.ExerciseTestSamples.*;
import static com.skillsdoor.skillverify.domain.QcmAnswerTestSamples.*;
import static com.skillsdoor.skillverify.domain.QcmOptionTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.skillsdoor.skillverify.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class QcmOptionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(QcmOption.class);
        QcmOption qcmOption1 = getQcmOptionSample1();
        QcmOption qcmOption2 = new QcmOption();
        assertThat(qcmOption1).isNotEqualTo(qcmOption2);

        qcmOption2.setId(qcmOption1.getId());
        assertThat(qcmOption1).isEqualTo(qcmOption2);

        qcmOption2 = getQcmOptionSample2();
        assertThat(qcmOption1).isNotEqualTo(qcmOption2);
    }

    @Test
    void qcmAnswersTest() {
        QcmOption qcmOption = getQcmOptionRandomSampleGenerator();
        QcmAnswer qcmAnswerBack = getQcmAnswerRandomSampleGenerator();

        qcmOption.addQcmAnswers(qcmAnswerBack);
        assertThat(qcmOption.getQcmAnswers()).containsOnly(qcmAnswerBack);
        assertThat(qcmAnswerBack.getQcmOption()).isEqualTo(qcmOption);

        qcmOption.removeQcmAnswers(qcmAnswerBack);
        assertThat(qcmOption.getQcmAnswers()).doesNotContain(qcmAnswerBack);
        assertThat(qcmAnswerBack.getQcmOption()).isNull();

        qcmOption.qcmAnswers(new HashSet<>(Set.of(qcmAnswerBack)));
        assertThat(qcmOption.getQcmAnswers()).containsOnly(qcmAnswerBack);
        assertThat(qcmAnswerBack.getQcmOption()).isEqualTo(qcmOption);

        qcmOption.setQcmAnswers(new HashSet<>());
        assertThat(qcmOption.getQcmAnswers()).doesNotContain(qcmAnswerBack);
        assertThat(qcmAnswerBack.getQcmOption()).isNull();
    }

    @Test
    void exerciseTest() {
        QcmOption qcmOption = getQcmOptionRandomSampleGenerator();
        Exercise exerciseBack = getExerciseRandomSampleGenerator();

        qcmOption.setExercise(exerciseBack);
        assertThat(qcmOption.getExercise()).isEqualTo(exerciseBack);

        qcmOption.exercise(null);
        assertThat(qcmOption.getExercise()).isNull();
    }
}
