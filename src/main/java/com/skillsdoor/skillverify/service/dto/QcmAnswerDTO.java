package com.skillsdoor.skillverify.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.skillsdoor.skillverify.domain.QcmAnswer} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class QcmAnswerDTO implements Serializable {

    private Long id;

    private Boolean isCorrect;

    @NotNull
    private CandidateAnswerDTO candidateAnswer;

    @NotNull
    private QcmOptionDTO qcmOption;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public QcmOptionDTO getQcmOption() {
        return qcmOption;
    }

    public void setQcmOption(QcmOptionDTO qcmOption) {
        this.qcmOption = qcmOption;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QcmAnswerDTO)) {
            return false;
        }

        QcmAnswerDTO qcmAnswerDTO = (QcmAnswerDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, qcmAnswerDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QcmAnswerDTO{" +
            "id=" + getId() +
            ", isCorrect='" + getIsCorrect() + "'" +
            ", candidateAnswer=" + getCandidateAnswer() +
            ", qcmOption=" + getQcmOption() +
            "}";
    }
}
