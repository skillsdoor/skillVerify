package com.skillsdoor.skillverify.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A BlankSpace.
 */
@Entity
@Table(name = "blank_space")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BlankSpace implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "position", nullable = false)
    private Integer position;

    @NotNull
    @Column(name = "correct_answer", nullable = false)
    private String correctAnswer;

    @NotNull
    @Column(name = "case_sensitive", nullable = false)
    private Boolean caseSensitive;

    @NotNull
    @Column(name = "order_index", nullable = false)
    private Integer orderIndex;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "blankSpace")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "candidateAnswer", "blankSpace" }, allowSetters = true)
    private Set<BlankAnswer> blankAnswers = new HashSet<>();

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

    public BlankSpace id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPosition() {
        return this.position;
    }

    public BlankSpace position(Integer position) {
        this.setPosition(position);
        return this;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getCorrectAnswer() {
        return this.correctAnswer;
    }

    public BlankSpace correctAnswer(String correctAnswer) {
        this.setCorrectAnswer(correctAnswer);
        return this;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public Boolean getCaseSensitive() {
        return this.caseSensitive;
    }

    public BlankSpace caseSensitive(Boolean caseSensitive) {
        this.setCaseSensitive(caseSensitive);
        return this;
    }

    public void setCaseSensitive(Boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
    }

    public Integer getOrderIndex() {
        return this.orderIndex;
    }

    public BlankSpace orderIndex(Integer orderIndex) {
        this.setOrderIndex(orderIndex);
        return this;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

    public Set<BlankAnswer> getBlankAnswers() {
        return this.blankAnswers;
    }

    public void setBlankAnswers(Set<BlankAnswer> blankAnswers) {
        if (this.blankAnswers != null) {
            this.blankAnswers.forEach(i -> i.setBlankSpace(null));
        }
        if (blankAnswers != null) {
            blankAnswers.forEach(i -> i.setBlankSpace(this));
        }
        this.blankAnswers = blankAnswers;
    }

    public BlankSpace blankAnswers(Set<BlankAnswer> blankAnswers) {
        this.setBlankAnswers(blankAnswers);
        return this;
    }

    public BlankSpace addBlankAnswers(BlankAnswer blankAnswer) {
        this.blankAnswers.add(blankAnswer);
        blankAnswer.setBlankSpace(this);
        return this;
    }

    public BlankSpace removeBlankAnswers(BlankAnswer blankAnswer) {
        this.blankAnswers.remove(blankAnswer);
        blankAnswer.setBlankSpace(null);
        return this;
    }

    public Exercise getExercise() {
        return this.exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public BlankSpace exercise(Exercise exercise) {
        this.setExercise(exercise);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BlankSpace)) {
            return false;
        }
        return getId() != null && getId().equals(((BlankSpace) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BlankSpace{" +
            "id=" + getId() +
            ", position=" + getPosition() +
            ", correctAnswer='" + getCorrectAnswer() + "'" +
            ", caseSensitive='" + getCaseSensitive() + "'" +
            ", orderIndex=" + getOrderIndex() +
            "}";
    }
}
