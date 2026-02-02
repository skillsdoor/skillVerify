package com.skillsdoor.skillverify.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.skillsdoor.skillverify.domain.BlankAnswer} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BlankAnswerDTO implements Serializable {

    private Long id;

    @NotNull
    private String answerText;

    private Boolean isCorrect;

    @NotNull
    private CandidateAnswerDTO candidateAnswer;

    @NotNull
    private BlankSpaceDTO blankSpace;

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

    public CandidateAnswerDTO getCandidateAnswer() {
        return candidateAnswer;
    }

    public void setCandidateAnswer(CandidateAnswerDTO candidateAnswer) {
        this.candidateAnswer = candidateAnswer;
    }

    public BlankSpaceDTO getBlankSpace() {
        return blankSpace;
    }

    public void setBlankSpace(BlankSpaceDTO blankSpace) {
        this.blankSpace = blankSpace;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BlankAnswerDTO)) {
            return false;
        }

        BlankAnswerDTO blankAnswerDTO = (BlankAnswerDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, blankAnswerDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BlankAnswerDTO{" +
            "id=" + getId() +
            ", answerText='" + getAnswerText() + "'" +
            ", isCorrect='" + getIsCorrect() + "'" +
            ", candidateAnswer=" + getCandidateAnswer() +
            ", blankSpace=" + getBlankSpace() +
            "}";
    }
}
