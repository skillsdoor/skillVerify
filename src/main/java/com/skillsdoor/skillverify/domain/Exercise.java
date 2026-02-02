package com.skillsdoor.skillverify.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.skillsdoor.skillverify.domain.enumeration.ExerciseType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Exercise.
 */
@Entity
@Table(name = "exercise")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Exercise implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ExerciseType type;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @Lob
    @Column(name = "instruction")
    private String instruction;

    @NotNull
    @Column(name = "points", nullable = false)
    private Integer points;

    @NotNull
    @Column(name = "order_index", nullable = false)
    private Integer orderIndex;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "exercise")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "media", "answerOptions", "fillInTheBlanks", "bindLists", "candidateAnswers", "exercise" },
        allowSetters = true
    )
    private Set<Question> questions = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "exercises", "examSessions", "recruiter", "trainingProvider" }, allowSetters = true)
    private Exam exam;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Exercise id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ExerciseType getType() {
        return this.type;
    }

    public Exercise type(ExerciseType type) {
        this.setType(type);
        return this;
    }

    public void setType(ExerciseType type) {
        this.type = type;
    }

    public String getTitle() {
        return this.title;
    }

    public Exercise title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInstruction() {
        return this.instruction;
    }

    public Exercise instruction(String instruction) {
        this.setInstruction(instruction);
        return this;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public Integer getPoints() {
        return this.points;
    }

    public Exercise points(Integer points) {
        this.setPoints(points);
        return this;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getOrderIndex() {
        return this.orderIndex;
    }

    public Exercise orderIndex(Integer orderIndex) {
        this.setOrderIndex(orderIndex);
        return this;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

    public Set<Question> getQuestions() {
        return this.questions;
    }

    public void setQuestions(Set<Question> questions) {
        if (this.questions != null) {
            this.questions.forEach(i -> i.setExercise(null));
        }
        if (questions != null) {
            questions.forEach(i -> i.setExercise(this));
        }
        this.questions = questions;
    }

    public Exercise questions(Set<Question> questions) {
        this.setQuestions(questions);
        return this;
    }

    public Exercise addQuestions(Question question) {
        this.questions.add(question);
        question.setExercise(this);
        return this;
    }

    public Exercise removeQuestions(Question question) {
        this.questions.remove(question);
        question.setExercise(null);
        return this;
    }

    public Exam getExam() {
        return this.exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public Exercise exam(Exam exam) {
        this.setExam(exam);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Exercise)) {
            return false;
        }
        return getId() != null && getId().equals(((Exercise) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Exercise{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", title='" + getTitle() + "'" +
            ", instruction='" + getInstruction() + "'" +
            ", points=" + getPoints() +
            ", orderIndex=" + getOrderIndex() +
            "}";
    }
}
