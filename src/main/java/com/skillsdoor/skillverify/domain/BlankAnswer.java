package com.skillsdoor.skillverify.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A BlankAnswer.
 */
@Entity
@Table(name = "blank_answer")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BlankAnswer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "answer_text", nullable = false)
    private String answerText;

    @Column(name = "is_correct")
    private Boolean isCorrect;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "qcmAnswers", "blankAnswers", "bindAnswers", "examSession", "exercise", "question", "bindListAnswers" },
        allowSetters = true
    )
    private CandidateAnswer candidateAnswer;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "blankAnswers", "exercise" }, allowSetters = true)
    private BlankSpace blankSpace;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BlankAnswer id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnswerText() {
        return this.answerText;
    }

    public BlankAnswer answerText(String answerText) {
        this.setAnswerText(answerText);
        return this;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public Boolean getIsCorrect() {
        return this.isCorrect;
    }

    public BlankAnswer isCorrect(Boolean isCorrect) {
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

    public BlankAnswer candidateAnswer(CandidateAnswer candidateAnswer) {
        this.setCandidateAnswer(candidateAnswer);
        return this;
    }

    public BlankSpace getBlankSpace() {
        return this.blankSpace;
    }

    public void setBlankSpace(BlankSpace blankSpace) {
        this.blankSpace = blankSpace;
    }

    public BlankAnswer blankSpace(BlankSpace blankSpace) {
        this.setBlankSpace(blankSpace);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BlankAnswer)) {
            return false;
        }
        return getId() != null && getId().equals(((BlankAnswer) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BlankAnswer{" +
            "id=" + getId() +
            ", answerText='" + getAnswerText() + "'" +
            ", isCorrect='" + getIsCorrect() + "'" +
            "}";
    }
}
