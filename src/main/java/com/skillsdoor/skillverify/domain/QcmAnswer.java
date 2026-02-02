package com.skillsdoor.skillverify.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A QcmAnswer.
 */
@Entity
@Table(name = "qcm_answer")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class QcmAnswer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

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
    @JsonIgnoreProperties(value = { "qcmAnswers", "exercise" }, allowSetters = true)
    private QcmOption qcmOption;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public QcmAnswer id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsCorrect() {
        return this.isCorrect;
    }

    public QcmAnswer isCorrect(Boolean isCorrect) {
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

    public QcmAnswer candidateAnswer(CandidateAnswer candidateAnswer) {
        this.setCandidateAnswer(candidateAnswer);
        return this;
    }

    public QcmOption getQcmOption() {
        return this.qcmOption;
    }

    public void setQcmOption(QcmOption qcmOption) {
        this.qcmOption = qcmOption;
    }

    public QcmAnswer qcmOption(QcmOption qcmOption) {
        this.setQcmOption(qcmOption);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QcmAnswer)) {
            return false;
        }
        return getId() != null && getId().equals(((QcmAnswer) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QcmAnswer{" +
            "id=" + getId() +
            ", isCorrect='" + getIsCorrect() + "'" +
            "}";
    }
}
