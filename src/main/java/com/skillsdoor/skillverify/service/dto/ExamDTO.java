package com.skillsdoor.skillverify.service.dto;

import com.skillsdoor.skillverify.domain.enumeration.ExamStatus;
import com.skillsdoor.skillverify.domain.enumeration.TestType;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.skillsdoor.skillverify.domain.Exam} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ExamDTO implements Serializable {

    private Long id;

    @NotNull
    private String title;

    @Lob
    private String description;

    @NotNull
    private Integer duration;

    @NotNull
    private Integer passingScore;

    @NotNull
    private ExamStatus status;

    @NotNull
    private TestType testType;

    private Instant createdAt;

    private Instant updatedAt;

    private Instant publishedAt;

    private RecruiterDTO recruiter;

    private TrainingProviderDTO trainingProvider;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getPassingScore() {
        return passingScore;
    }

    public void setPassingScore(Integer passingScore) {
        this.passingScore = passingScore;
    }

    public ExamStatus getStatus() {
        return status;
    }

    public void setStatus(ExamStatus status) {
        this.status = status;
    }

    public TestType getTestType() {
        return testType;
    }

    public void setTestType(TestType testType) {
        this.testType = testType;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Instant publishedAt) {
        this.publishedAt = publishedAt;
    }

    public RecruiterDTO getRecruiter() {
        return recruiter;
    }

    public void setRecruiter(RecruiterDTO recruiter) {
        this.recruiter = recruiter;
    }

    public TrainingProviderDTO getTrainingProvider() {
        return trainingProvider;
    }

    public void setTrainingProvider(TrainingProviderDTO trainingProvider) {
        this.trainingProvider = trainingProvider;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExamDTO)) {
            return false;
        }

        ExamDTO examDTO = (ExamDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, examDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ExamDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", duration=" + getDuration() +
            ", passingScore=" + getPassingScore() +
            ", status='" + getStatus() + "'" +
            ", testType='" + getTestType() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", publishedAt='" + getPublishedAt() + "'" +
            ", recruiter=" + getRecruiter() +
            ", trainingProvider=" + getTrainingProvider() +
            "}";
    }
}
