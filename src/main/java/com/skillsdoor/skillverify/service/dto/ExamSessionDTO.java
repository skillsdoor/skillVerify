package com.skillsdoor.skillverify.service.dto;

import com.skillsdoor.skillverify.domain.enumeration.SubmissionStatus;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.skillsdoor.skillverify.domain.ExamSession} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ExamSessionDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant startedAt;

    private Instant submittedAt;

    @NotNull
    private SubmissionStatus status;

    private Integer score;

    private Integer totalPoints;

    private Integer timeSpent;

    @NotNull
    private CandidateDTO candidate;

    @NotNull
    private ExamDTO exam;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Instant startedAt) {
        this.startedAt = startedAt;
    }

    public Instant getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(Instant submittedAt) {
        this.submittedAt = submittedAt;
    }

    public SubmissionStatus getStatus() {
        return status;
    }

    public void setStatus(SubmissionStatus status) {
        this.status = status;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(Integer totalPoints) {
        this.totalPoints = totalPoints;
    }

    public Integer getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(Integer timeSpent) {
        this.timeSpent = timeSpent;
    }

    public CandidateDTO getCandidate() {
        return candidate;
    }

    public void setCandidate(CandidateDTO candidate) {
        this.candidate = candidate;
    }

    public ExamDTO getExam() {
        return exam;
    }

    public void setExam(ExamDTO exam) {
        this.exam = exam;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExamSessionDTO)) {
            return false;
        }

        ExamSessionDTO examSessionDTO = (ExamSessionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, examSessionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ExamSessionDTO{" +
            "id=" + getId() +
            ", startedAt='" + getStartedAt() + "'" +
            ", submittedAt='" + getSubmittedAt() + "'" +
            ", status='" + getStatus() + "'" +
            ", score=" + getScore() +
            ", totalPoints=" + getTotalPoints() +
            ", timeSpent=" + getTimeSpent() +
            ", candidate=" + getCandidate() +
            ", exam=" + getExam() +
            "}";
    }
}
