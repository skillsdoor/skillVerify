package com.skillsdoor.skillverify.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ListItem.
 */
@Entity
@Table(name = "list_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ListItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "content", nullable = false)
    private String content;

    @NotNull
    @Column(name = "matching_content", nullable = false)
    private String matchingContent;

    @NotNull
    @Column(name = "is_left_side", nullable = false)
    private Boolean isLeftSide;

    @NotNull
    @Column(name = "order_index", nullable = false)
    private Integer orderIndex;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "media", "qcmOptions", "blankSpaces", "listItems", "candidateAnswers", "exam", "questions" },
        allowSetters = true
    )
    private Exercise exercise;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ListItem id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return this.content;
    }

    public ListItem content(String content) {
        this.setContent(content);
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMatchingContent() {
        return this.matchingContent;
    }

    public ListItem matchingContent(String matchingContent) {
        this.setMatchingContent(matchingContent);
        return this;
    }

    public void setMatchingContent(String matchingContent) {
        this.matchingContent = matchingContent;
    }

    public Boolean getIsLeftSide() {
        return this.isLeftSide;
    }

    public ListItem isLeftSide(Boolean isLeftSide) {
        this.setIsLeftSide(isLeftSide);
        return this;
    }

    public void setIsLeftSide(Boolean isLeftSide) {
        this.isLeftSide = isLeftSide;
    }

    public Integer getOrderIndex() {
        return this.orderIndex;
    }

    public ListItem orderIndex(Integer orderIndex) {
        this.setOrderIndex(orderIndex);
        return this;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

    public Exercise getExercise() {
        return this.exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public ListItem exercise(Exercise exercise) {
        this.setExercise(exercise);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ListItem)) {
            return false;
        }
        return getId() != null && getId().equals(((ListItem) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ListItem{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            ", matchingContent='" + getMatchingContent() + "'" +
            ", isLeftSide='" + getIsLeftSide() + "'" +
            ", orderIndex=" + getOrderIndex() +
            "}";
    }
}
