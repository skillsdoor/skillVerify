package com.skillsdoor.skillverify.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.skillsdoor.skillverify.domain.QcmOption} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class QcmOptionDTO implements Serializable {

    private Long id;

    @NotNull
    private String content;

    @NotNull
    private Boolean isCorrect;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(Boolean isCorrect) {
        this.isCorrect = isCorrect;
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
        if (!(o instanceof QcmOptionDTO)) {
            return false;
        }

        QcmOptionDTO qcmOptionDTO = (QcmOptionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, qcmOptionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QcmOptionDTO{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            ", isCorrect='" + getIsCorrect() + "'" +
            ", orderIndex=" + getOrderIndex() +
            ", exercise=" + getExercise() +
            "}";
    }
}
