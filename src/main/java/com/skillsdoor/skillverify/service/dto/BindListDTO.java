package com.skillsdoor.skillverify.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.skillsdoor.skillverify.domain.BindList} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BindListDTO implements Serializable {

    private Long id;

    @NotNull
    private String leftItem;

    @NotNull
    private String rightItem;

    @NotNull
    private Integer orderIndex;

    @NotNull
    private QuestionDTO question;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLeftItem() {
        return leftItem;
    }

    public void setLeftItem(String leftItem) {
        this.leftItem = leftItem;
    }

    public String getRightItem() {
        return rightItem;
    }

    public void setRightItem(String rightItem) {
        this.rightItem = rightItem;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
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
        if (!(o instanceof BindListDTO)) {
            return false;
        }

        BindListDTO bindListDTO = (BindListDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, bindListDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BindListDTO{" +
            "id=" + getId() +
            ", leftItem='" + getLeftItem() + "'" +
            ", rightItem='" + getRightItem() + "'" +
            ", orderIndex=" + getOrderIndex() +
            ", question=" + getQuestion() +
            "}";
    }
}
