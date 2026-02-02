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
 * A QcmOption.
 */
@Entity
@Table(name = "qcm_option")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class QcmOption implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "content", nullable = false)
    private String content;

    @NotNull
    @Column(name = "is_correct", nullable = false)
    private Boolean isCorrect;

    @NotNull
    @Column(name = "order_index", nullable = false)
    private Integer orderIndex;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "qcmOption")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "candidateAnswer", "qcmOption" }, allowSetters = true)
    private Set<QcmAnswer> qcmAnswers = new HashSet<>();

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

    public QcmOption id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return this.content;
    }

    public QcmOption content(String content) {
        this.setContent(content);
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getIsCorrect() {
        return this.isCorrect;
    }

    public QcmOption isCorrect(Boolean isCorrect) {
        this.setIsCorrect(isCorrect);
        return this;
    }

    public void setIsCorrect(Boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public Integer getOrderIndex() {
        return this.orderIndex;
    }

    public QcmOption orderIndex(Integer orderIndex) {
        this.setOrderIndex(orderIndex);
        return this;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

    public Set<QcmAnswer> getQcmAnswers() {
        return this.qcmAnswers;
    }

    public void setQcmAnswers(Set<QcmAnswer> qcmAnswers) {
        if (this.qcmAnswers != null) {
            this.qcmAnswers.forEach(i -> i.setQcmOption(null));
        }
        if (qcmAnswers != null) {
            qcmAnswers.forEach(i -> i.setQcmOption(this));
        }
        this.qcmAnswers = qcmAnswers;
    }

    public QcmOption qcmAnswers(Set<QcmAnswer> qcmAnswers) {
        this.setQcmAnswers(qcmAnswers);
        return this;
    }

    public QcmOption addQcmAnswers(QcmAnswer qcmAnswer) {
        this.qcmAnswers.add(qcmAnswer);
        qcmAnswer.setQcmOption(this);
        return this;
    }

    public QcmOption removeQcmAnswers(QcmAnswer qcmAnswer) {
        this.qcmAnswers.remove(qcmAnswer);
        qcmAnswer.setQcmOption(null);
        return this;
    }

    public Exercise getExercise() {
        return this.exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public QcmOption exercise(Exercise exercise) {
        this.setExercise(exercise);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QcmOption)) {
            return false;
        }
        return getId() != null && getId().equals(((QcmOption) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QcmOption{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            ", isCorrect='" + getIsCorrect() + "'" +
            ", orderIndex=" + getOrderIndex() +
            "}";
    }
}
