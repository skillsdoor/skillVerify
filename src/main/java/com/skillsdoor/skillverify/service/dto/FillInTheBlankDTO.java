package com.skillsdoor.skillverify.service.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.skillsdoor.skillverify.domain.FillInTheBlank} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FillInTheBlankDTO implements Serializable {

    private Long id;

    @Lob
    private String textWithBlanks;

    @NotNull
    private Integer blankIndex;

    @NotNull
    private String correctAnswer;

    @NotNull
    private Boolean caseSensitive;

    @NotNull
    private QuestionDTO question;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTextWithBlanks() {
        return textWithBlanks;
    }

    public void setTextWithBlanks(String textWithBlanks) {
        this.textWithBlanks = textWithBlanks;
    }

    public Integer getBlankIndex() {
        return blankIndex;
    }

    public void setBlankIndex(Integer blankIndex) {
        this.blankIndex = blankIndex;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public Boolean getCaseSensitive() {
        return caseSensitive;
    }

    public void setCaseSensitive(Boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
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
        if (!(o instanceof FillInTheBlankDTO)) {
            return false;
        }

        FillInTheBlankDTO fillInTheBlankDTO = (FillInTheBlankDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, fillInTheBlankDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FillInTheBlankDTO{" +
            "id=" + getId() +
            ", textWithBlanks='" + getTextWithBlanks() + "'" +
            ", blankIndex=" + getBlankIndex() +
            ", correctAnswer='" + getCorrectAnswer() + "'" +
            ", caseSensitive='" + getCaseSensitive() + "'" +
            ", question=" + getQuestion() +
            "}";
    }
}
