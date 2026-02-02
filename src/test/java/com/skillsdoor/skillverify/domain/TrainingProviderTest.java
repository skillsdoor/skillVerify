package com.skillsdoor.skillverify.domain;

import static com.skillsdoor.skillverify.domain.ExamTestSamples.*;
import static com.skillsdoor.skillverify.domain.TrainingProviderTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.skillsdoor.skillverify.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class TrainingProviderTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TrainingProvider.class);
        TrainingProvider trainingProvider1 = getTrainingProviderSample1();
        TrainingProvider trainingProvider2 = new TrainingProvider();
        assertThat(trainingProvider1).isNotEqualTo(trainingProvider2);

        trainingProvider2.setId(trainingProvider1.getId());
        assertThat(trainingProvider1).isEqualTo(trainingProvider2);

        trainingProvider2 = getTrainingProviderSample2();
        assertThat(trainingProvider1).isNotEqualTo(trainingProvider2);
    }

    @Test
    void examsTest() {
        TrainingProvider trainingProvider = getTrainingProviderRandomSampleGenerator();
        Exam examBack = getExamRandomSampleGenerator();

        trainingProvider.addExams(examBack);
        assertThat(trainingProvider.getExams()).containsOnly(examBack);
        assertThat(examBack.getTrainingProvider()).isEqualTo(trainingProvider);

        trainingProvider.removeExams(examBack);
        assertThat(trainingProvider.getExams()).doesNotContain(examBack);
        assertThat(examBack.getTrainingProvider()).isNull();

        trainingProvider.exams(new HashSet<>(Set.of(examBack)));
        assertThat(trainingProvider.getExams()).containsOnly(examBack);
        assertThat(examBack.getTrainingProvider()).isEqualTo(trainingProvider);

        trainingProvider.setExams(new HashSet<>());
        assertThat(trainingProvider.getExams()).doesNotContain(examBack);
        assertThat(examBack.getTrainingProvider()).isNull();
    }
}
