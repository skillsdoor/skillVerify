package com.skillsdoor.skillverify.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.skillsdoor.skillverify.domain.BindListAnswer} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BindListAnswerDTO implements Serializable {

    private Long id;

    @NotNull
    private Long leftItemId;

    @NotNull
    private Long rightItemId;

    private Boolean isCorrect;

    @NotNull
    private CandidateAnswerDTO candidateAnswer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLeftItemId() {
        return leftItemId;
    }

    public void setLeftItemId(Long leftItemId) {
        this.leftItemId = leftItemId;
    }

    public Long getRightItemId() {
        return rightItemId;
    }

    public void setRightItemId(Long rightItemId) {
        this.rightItemId = rightItemId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BindListAnswerDTO)) {
            return false;
        }

        BindListAnswerDTO bindListAnswerDTO = (BindListAnswerDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, bindListAnswerDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BindListAnswerDTO{" +
            "id=" + getId() +
            ", leftItemId=" + getLeftItemId() +
            ", rightItemId=" + getRightItemId() +
            ", isCorrect='" + getIsCorrect() + "'" +
            ", candidateAnswer=" + getCandidateAnswer() +
            "}";
    }
}
