package com.skillsdoor.skillverify.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AnswerOption.
 */
@Entity
@Table(name = "answer_option")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AnswerOption implements Serializable {

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

    public AnswerOption id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return this.content;
    }

    public AnswerOption content(String content) {
        this.setContent(content);
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getIsCorrect() {
        return this.isCorrect;
    }

    public AnswerOption isCorrect(Boolean isCorrect) {
        this.setIsCorrect(isCorrect);
        return this;
    }

    public void setIsCorrect(Boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public Integer getOrderIndex() {
        return this.orderIndex;
    }

    public AnswerOption orderIndex(Integer orderIndex) {
        this.setOrderIndex(orderIndex);
        return this;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

    public Question getQuestion() {
        return this.question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public AnswerOption question(Question question) {
        this.setQuestion(question);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnswerOption)) {
            return false;
        }
        return getId() != null && getId().equals(((AnswerOption) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AnswerOption{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            ", isCorrect='" + getIsCorrect() + "'" +
            ", orderIndex=" + getOrderIndex() +
            "}";
    }
}
