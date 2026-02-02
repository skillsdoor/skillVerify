package com.skillsdoor.skillverify.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.skillsdoor.skillverify.domain.enumeration.SubmissionStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ExamSession.
 */
@Entity
@Table(name = "exam_session")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ExamSession implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "started_at", nullable = false)
    private Instant startedAt;

    @Column(name = "submitted_at")
    private Instant submittedAt;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private SubmissionStatus status;

    @Column(name = "score")
    private Integer score;

    @Column(name = "total_points")
    private Integer totalPoints;

    @Column(name = "time_spent")
    private Integer timeSpent;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "examSession")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "qcmAnswers", "blankAnswers", "bindAnswers", "examSession", "exercise", "question", "bindListAnswers" },
        allowSetters = true
    )
    private Set<CandidateAnswer> candidateAnswers = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "user", "examSessions" }, allowSetters = true)
    private Candidate candidate;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "exercises", "examSessions", "recruiter", "trainingProvider" }, allowSetters = true)
    private Exam exam;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ExamSession id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getStartedAt() {
        return this.startedAt;
    }

    public ExamSession startedAt(Instant startedAt) {
        this.setStartedAt(startedAt);
        return this;
    }

    public void setStartedAt(Instant startedAt) {
        this.startedAt = startedAt;
    }

    public Instant getSubmittedAt() {
        return this.submittedAt;
    }

    public ExamSession submittedAt(Instant submittedAt) {
        this.setSubmittedAt(submittedAt);
        return this;
    }

    public void setSubmittedAt(Instant submittedAt) {
        this.submittedAt = submittedAt;
    }

    public SubmissionStatus getStatus() {
        return this.status;
    }

    public ExamSession status(SubmissionStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(SubmissionStatus status) {
        this.status = status;
    }

    public Integer getScore() {
        return this.score;
    }

    public ExamSession score(Integer score) {
        this.setScore(score);
        return this;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getTotalPoints() {
        return this.totalPoints;
    }

    public ExamSession totalPoints(Integer totalPoints) {
        this.setTotalPoints(totalPoints);
        return this;
    }

    public void setTotalPoints(Integer totalPoints) {
        this.totalPoints = totalPoints;
    }

    public Integer getTimeSpent() {
        return this.timeSpent;
    }

    public ExamSession timeSpent(Integer timeSpent) {
        this.setTimeSpent(timeSpent);
        return this;
    }

    public void setTimeSpent(Integer timeSpent) {
        this.timeSpent = timeSpent;
    }

    public Set<CandidateAnswer> getCandidateAnswers() {
        return this.candidateAnswers;
    }

    public void setCandidateAnswers(Set<CandidateAnswer> candidateAnswers) {
        if (this.candidateAnswers != null) {
            this.candidateAnswers.forEach(i -> i.setExamSession(null));
        }
        if (candidateAnswers != null) {
            candidateAnswers.forEach(i -> i.setExamSession(this));
        }
        this.candidateAnswers = candidateAnswers;
    }

    public ExamSession candidateAnswers(Set<CandidateAnswer> candidateAnswers) {
        this.setCandidateAnswers(candidateAnswers);
        return this;
    }

    public ExamSession addCandidateAnswers(CandidateAnswer candidateAnswer) {
        this.candidateAnswers.add(candidateAnswer);
        candidateAnswer.setExamSession(this);
        return this;
    }

    public ExamSession removeCandidateAnswers(CandidateAnswer candidateAnswer) {
        this.candidateAnswers.remove(candidateAnswer);
        candidateAnswer.setExamSession(null);
        return this;
    }

    public Candidate getCandidate() {
        return this.candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public ExamSession candidate(Candidate candidate) {
        this.setCandidate(candidate);
        return this;
    }

    public Exam getExam() {
        return this.exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public ExamSession exam(Exam exam) {
        this.setExam(exam);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExamSession)) {
            return false;
        }
        return getId() != null && getId().equals(((ExamSession) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ExamSession{" +
            "id=" + getId() +
            ", startedAt='" + getStartedAt() + "'" +
            ", submittedAt='" + getSubmittedAt() + "'" +
            ", status='" + getStatus() + "'" +
            ", score=" + getScore() +
            ", totalPoints=" + getTotalPoints() +
            ", timeSpent=" + getTimeSpent() +
            "}";
    }
}
