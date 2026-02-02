package com.skillsdoor.skillverify.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.skillsdoor.skillverify.domain.ListItem} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ListItemDTO implements Serializable {

    private Long id;

    @NotNull
    private String content;

    @NotNull
    private String matchingContent;

    @NotNull
    private Boolean isLeftSide;

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

    public String getMatchingContent() {
        return matchingContent;
    }

    public void setMatchingContent(String matchingContent) {
        this.matchingContent = matchingContent;
    }

    public Boolean getIsLeftSide() {
        return isLeftSide;
    }

    public void setIsLeftSide(Boolean isLeftSide) {
        this.isLeftSide = isLeftSide;
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
        if (!(o instanceof ListItemDTO)) {
            return false;
        }

        ListItemDTO listItemDTO = (ListItemDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, listItemDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ListItemDTO{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            ", matchingContent='" + getMatchingContent() + "'" +
            ", isLeftSide='" + getIsLeftSide() + "'" +
            ", orderIndex=" + getOrderIndex() +
            ", exercise=" + getExercise() +
            "}";
    }
}
