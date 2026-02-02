package com.skillsdoor.skillverify.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A BindListAnswer.
 */
@Entity
@Table(name = "bind_list_answer")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BindListAnswer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "left_item_id", nullable = false)
    private Long leftItemId;

    @NotNull
    @Column(name = "right_item_id", nullable = false)
    private Long rightItemId;

    @Column(name = "is_correct")
    private Boolean isCorrect;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "bindListAnswers", "examSession", "question" }, allowSetters = true)
    private CandidateAnswer candidateAnswer;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BindListAnswer id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLeftItemId() {
        return this.leftItemId;
    }

    public BindListAnswer leftItemId(Long leftItemId) {
        this.setLeftItemId(leftItemId);
        return this;
    }

    public void setLeftItemId(Long leftItemId) {
        this.leftItemId = leftItemId;
    }

    public Long getRightItemId() {
        return this.rightItemId;
    }

    public BindListAnswer rightItemId(Long rightItemId) {
        this.setRightItemId(rightItemId);
        return this;
    }

    public void setRightItemId(Long rightItemId) {
        this.rightItemId = rightItemId;
    }

    public Boolean getIsCorrect() {
        return this.isCorrect;
    }

    public BindListAnswer isCorrect(Boolean isCorrect) {
        this.setIsCorrect(isCorrect);
        return this;
    }

    public void setIsCorrect(Boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public CandidateAnswer getCandidateAnswer() {
        return this.candidateAnswer;
    }

    public void setCandidateAnswer(CandidateAnswer candidateAnswer) {
        this.candidateAnswer = candidateAnswer;
    }

    public BindListAnswer candidateAnswer(CandidateAnswer candidateAnswer) {
        this.setCandidateAnswer(candidateAnswer);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BindListAnswer)) {
            return false;
        }
        return getId() != null && getId().equals(((BindListAnswer) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BindListAnswer{" +
            "id=" + getId() +
            ", leftItemId=" + getLeftItemId() +
            ", rightItemId=" + getRightItemId() +
            ", isCorrect='" + getIsCorrect() + "'" +
            "}";
    }
}
