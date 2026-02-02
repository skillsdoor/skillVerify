package com.skillsdoor.skillverify.domain;

import static com.skillsdoor.skillverify.domain.ExamTestSamples.*;
import static com.skillsdoor.skillverify.domain.RecruiterTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.skillsdoor.skillverify.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class RecruiterTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Recruiter.class);
        Recruiter recruiter1 = getRecruiterSample1();
        Recruiter recruiter2 = new Recruiter();
        assertThat(recruiter1).isNotEqualTo(recruiter2);

        recruiter2.setId(recruiter1.getId());
        assertThat(recruiter1).isEqualTo(recruiter2);

        recruiter2 = getRecruiterSample2();
        assertThat(recruiter1).isNotEqualTo(recruiter2);
    }

    @Test
    void examsTest() {
        Recruiter recruiter = getRecruiterRandomSampleGenerator();
        Exam examBack = getExamRandomSampleGenerator();

        recruiter.addExams(examBack);
        assertThat(recruiter.getExams()).containsOnly(examBack);
        assertThat(examBack.getRecruiter()).isEqualTo(recruiter);

        recruiter.removeExams(examBack);
        assertThat(recruiter.getExams()).doesNotContain(examBack);
        assertThat(examBack.getRecruiter()).isNull();

        recruiter.exams(new HashSet<>(Set.of(examBack)));
        assertThat(recruiter.getExams()).containsOnly(examBack);
        assertThat(examBack.getRecruiter()).isEqualTo(recruiter);

        recruiter.setExams(new HashSet<>());
        assertThat(recruiter.getExams()).doesNotContain(examBack);
        assertThat(examBack.getRecruiter()).isNull();
    }
}
