package com.skillsdoor.skillverify.service.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.skillsdoor.skillverify.domain.CandidateAnswer} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CandidateAnswerDTO implements Serializable {

    private Long id;

    @Lob
    private String answerText;

    private Boolean isCorrect;

    private Integer pointsAwarded;

    private Instant answeredAt;

    @NotNull
    private ExamSessionDTO examSession;

    @NotNull
    private ExerciseDTO exercise;

    private QuestionDTO question;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public Boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(Boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public Integer getPointsAwarded() {
        return pointsAwarded;
    }

    public void setPointsAwarded(Integer pointsAwarded) {
        this.pointsAwarded = pointsAwarded;
    }

    public Instant getAnsweredAt() {
        return answeredAt;
    }

    public void setAnsweredAt(Instant answeredAt) {
        this.answeredAt = answeredAt;
    }

    public ExamSessionDTO getExamSession() {
        return examSession;
    }

    public void setExamSession(ExamSessionDTO examSession) {
        this.examSession = examSession;
    }

    public ExerciseDTO getExercise() {
        return exercise;
    }

    public void setExercise(ExerciseDTO exercise) {
        this.exercise = exercise;
    }

    public QuestionDTO getQuestion() {
        return question;
    }

    public void setQuestion(QuestionDTO question) {
        this.question = question;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CandidateAnswerDTO)) {
            return false;
        }

        CandidateAnswerDTO candidateAnswerDTO = (CandidateAnswerDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, candidateAnswerDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CandidateAnswerDTO{" +
            "id=" + getId() +
            ", answerText='" + getAnswerText() + "'" +
            ", isCorrect='" + getIsCorrect() + "'" +
            ", pointsAwarded=" + getPointsAwarded() +
            ", answeredAt='" + getAnsweredAt() + "'" +
            ", examSession=" + getExamSession() +
            ", exercise=" + getExercise() +
            ", question=" + getQuestion() +
            "}";
    }
}
