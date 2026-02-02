package com.skillsdoor.skillverify.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.skillsdoor.skillverify.domain.BlankSpace} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BlankSpaceDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer position;

    @NotNull
    private String correctAnswer;

    @NotNull
    private Boolean caseSensitive;

    @NotNull
    private Integer orderIndex;

    @NotNull
    private ExerciseDTO exercise;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
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

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

    public ExerciseDTO getExercise() {
        return exercise;
    }

    public void setExercise(ExerciseDTO exercise) {
        this.exercise = exercise;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BlankSpaceDTO)) {
            return false;
        }

        BlankSpaceDTO blankSpaceDTO = (BlankSpaceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, blankSpaceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BlankSpaceDTO{" +
            "id=" + getId() +
            ", position=" + getPosition() +
            ", correctAnswer='" + getCorrectAnswer() + "'" +
            ", caseSensitive='" + getCaseSensitive() + "'" +
            ", orderIndex=" + getOrderIndex() +
            ", exercise=" + getExercise() +
            "}";
    }
}
