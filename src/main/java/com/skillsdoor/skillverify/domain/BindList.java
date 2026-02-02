package com.skillsdoor.skillverify.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A BindList.
 */
@Entity
@Table(name = "bind_list")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BindList implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "left_item", nullable = false)
    private String leftItem;

    @NotNull
    @Column(name = "right_item", nullable = false)
    private String rightItem;

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

    public BindList id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLeftItem() {
        return this.leftItem;
    }

    public BindList leftItem(String leftItem) {
        this.setLeftItem(leftItem);
        return this;
    }

    public void setLeftItem(String leftItem) {
        this.leftItem = leftItem;
    }

    public String getRightItem() {
        return this.rightItem;
    }

    public BindList rightItem(String rightItem) {
        this.setRightItem(rightItem);
        return this;
    }

    public void setRightItem(String rightItem) {
        this.rightItem = rightItem;
    }

    public Integer getOrderIndex() {
        return this.orderIndex;
    }

    public BindList orderIndex(Integer orderIndex) {
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

    public BindList question(Question question) {
        this.setQuestion(question);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BindList)) {
            return false;
        }
        return getId() != null && getId().equals(((BindList) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BindList{" +
            "id=" + getId() +
            ", leftItem='" + getLeftItem() + "'" +
            ", rightItem='" + getRightItem() + "'" +
            ", orderIndex=" + getOrderIndex() +
            "}";
    }
}
