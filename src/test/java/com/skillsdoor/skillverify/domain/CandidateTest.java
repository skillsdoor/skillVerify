package com.skillsdoor.skillverify.domain;

import static com.skillsdoor.skillverify.domain.CandidateTestSamples.*;
import static com.skillsdoor.skillverify.domain.ExamSessionTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.skillsdoor.skillverify.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CandidateTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Candidate.class);
        Candidate candidate1 = getCandidateSample1();
        Candidate candidate2 = new Candidate();
        assertThat(candidate1).isNotEqualTo(candidate2);

        candidate2.setId(candidate1.getId());
        assertThat(candidate1).isEqualTo(candidate2);

        candidate2 = getCandidateSample2();
        assertThat(candidate1).isNotEqualTo(candidate2);
    }

    @Test
    void examSessionsTest() {
        Candidate candidate = getCandidateRandomSampleGenerator();
        ExamSession examSessionBack = getExamSessionRandomSampleGenerator();

        candidate.addExamSessions(examSessionBack);
        assertThat(candidate.getExamSessions()).containsOnly(examSessionBack);
        assertThat(examSessionBack.getCandidate()).isEqualTo(candidate);

        candidate.removeExamSessions(examSessionBack);
        assertThat(candidate.getExamSessions()).doesNotContain(examSessionBack);
        assertThat(examSessionBack.getCandidate()).isNull();

        candidate.examSessions(new HashSet<>(Set.of(examSessionBack)));
        assertThat(candidate.getExamSessions()).containsOnly(examSessionBack);
        assertThat(examSessionBack.getCandidate()).isEqualTo(candidate);

        candidate.setExamSessions(new HashSet<>());
        assertThat(candidate.getExamSessions()).doesNotContain(examSessionBack);
        assertThat(examSessionBack.getCandidate()).isNull();
    }
}
