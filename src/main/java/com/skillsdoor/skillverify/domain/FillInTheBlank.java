package com.skillsdoor.skillverify.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FillInTheBlank.
 */
@Entity
@Table(name = "fill_in_the_blank")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FillInTheBlank implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Lob
    @Column(name = "text_with_blanks", nullable = false)
    private String textWithBlanks;

    @NotNull
    @Column(name = "blank_index", nullable = false)
    private Integer blankIndex;

    @NotNull
    @Column(name = "correct_answer", nullable = false)
    private String correctAnswer;

    @NotNull
    @Column(name = "case_sensitive", nullable = false)
    private Boolean caseSensitive;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "media", "answerOptions", "fillInTheBlanks", "bindLists", "candidateAnswers", "exercise" },
        allowSetters = true
    )
    private Question question;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FillInTheBlank id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTextWithBlanks() {
        return this.textWithBlanks;
    }

    public FillInTheBlank textWithBlanks(String textWithBlanks) {
        this.setTextWithBlanks(textWithBlanks);
        return this;
    }

    public void setTextWithBlanks(String textWithBlanks) {
        this.textWithBlanks = textWithBlanks;
    }

    public Integer getBlankIndex() {
        return this.blankIndex;
    }

    public FillInTheBlank blankIndex(Integer blankIndex) {
        this.setBlankIndex(blankIndex);
        return this;
    }

    public void setBlankIndex(Integer blankIndex) {
        this.blankIndex = blankIndex;
    }

    public String getCorrectAnswer() {
        return this.correctAnswer;
    }

    public FillInTheBlank correctAnswer(String correctAnswer) {
        this.setCorrectAnswer(correctAnswer);
        return this;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public Boolean getCaseSensitive() {
        return this.caseSensitive;
    }

    public FillInTheBlank caseSensitive(Boolean caseSensitive) {
        this.setCaseSensitive(caseSensitive);
        return this;
    }

    public void setCaseSensitive(Boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
    }

    public Question getQuestion() {
        return this.question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public FillInTheBlank question(Question question) {
        this.setQuestion(question);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FillInTheBlank)) {
            return false;
        }
        return getId() != null && getId().equals(((FillInTheBlank) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FillInTheBlank{" +
            "id=" + getId() +
            ", textWithBlanks='" + getTextWithBlanks() + "'" +
            ", blankIndex=" + getBlankIndex() +
            ", correctAnswer='" + getCorrectAnswer() + "'" +
            ", caseSensitive='" + getCaseSensitive() + "'" +
            "}";
    }
}
