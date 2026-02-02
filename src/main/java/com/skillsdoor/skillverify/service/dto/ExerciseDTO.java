package com.skillsdoor.skillverify.service.dto;

import com.skillsdoor.skillverify.domain.enumeration.ExerciseType;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the {@link com.skillsdoor.skillverify.domain.Exercise} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ExerciseDTO implements Serializable {

    private Long id;

    @NotNull
    private ExerciseType type;

    @NotNull
    private String title;

    @Lob
    private String instruction;

    @Lob
    private String content;

    @Lob
    private String explanation;

    @NotNull
    private Integer points;

    @NotNull
    private Integer orderIndex;

    @NotNull
    private ExamDTO exam;

    private List<QcmOptionDTO> qcmOptions;

    private List<BlankSpaceDTO> blankSpaces;

    private List<ListItemDTO> listItems;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ExerciseType getType() {
        return type;
    }

    public void setType(ExerciseType type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

    public ExamDTO getExam() {
        return exam;
    }

    public void setExam(ExamDTO exam) {
        this.exam = exam;
    }

    public List<QcmOptionDTO> getQcmOptions() {
        return qcmOptions;
    }

    public void setQcmOptions(List<QcmOptionDTO> qcmOptions) {
        this.qcmOptions = qcmOptions;
    }

    public List<BlankSpaceDTO> getBlankSpaces() {
        return blankSpaces;
    }

    public void setBlankSpaces(List<BlankSpaceDTO> blankSpaces) {
        this.blankSpaces = blankSpaces;
    }

    public List<ListItemDTO> getListItems() {
        return listItems;
    }

    public void setListItems(List<ListItemDTO> listItems) {
        this.listItems = listItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExerciseDTO)) {
            return false;
        }

        ExerciseDTO exerciseDTO = (ExerciseDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, exerciseDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ExerciseDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", title='" + getTitle() + "'" +
            ", instruction='" + getInstruction() + "'" +
            ", content='" + getContent() + "'" +
            ", explanation='" + getExplanation() + "'" +
            ", points=" + getPoints() +
            ", orderIndex=" + getOrderIndex() +
            ", exam=" + getExam() +
            "}";
    }
}
