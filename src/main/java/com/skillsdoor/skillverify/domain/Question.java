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
 * A Question.
 */
@Entity
@Table(name = "question")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @Lob
    @Column(name = "explanation")
    private String explanation;

    @NotNull
    @Column(name = "points", nullable = false)
    private Integer points;

    @NotNull
    @Column(name = "order_index", nullable = false)
    private Integer orderIndex;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "question")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "question" }, allowSetters = true)
    private Set<Media> media = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "question")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "question" }, allowSetters = true)
    private Set<AnswerOption> answerOptions = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "question")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "question" }, allowSetters = true)
    private Set<FillInTheBlank> fillInTheBlanks = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "question")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "question" }, allowSetters = true)
    private Set<BindList> bindLists = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "question")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "bindListAnswers", "examSession", "question" }, allowSetters = true)
    private Set<CandidateAnswer> candidateAnswers = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "questions", "exam" }, allowSetters = true)
    private Exercise exercise;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Question id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return this.content;
    }

    public Question content(String content) {
        this.setContent(content);
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExplanation() {
        return this.explanation;
    }

    public Question explanation(String explanation) {
        this.setExplanation(explanation);
        return this;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public Integer getPoints() {
        return this.points;
    }

    public Question points(Integer points) {
        this.setPoints(points);
        return this;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getOrderIndex() {
        return this.orderIndex;
    }

    public Question orderIndex(Integer orderIndex) {
        this.setOrderIndex(orderIndex);
        return this;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

    public Set<Media> getMedia() {
        return this.media;
    }

    public void setMedia(Set<Media> media) {
        if (this.media != null) {
            this.media.forEach(i -> i.setQuestion(null));
        }
        if (media != null) {
            media.forEach(i -> i.setQuestion(this));
        }
        this.media = media;
    }

    public Question media(Set<Media> media) {
        this.setMedia(media);
        return this;
    }

    public Question addMedia(Media media) {
        this.media.add(media);
        media.setQuestion(this);
        return this;
    }

    public Question removeMedia(Media media) {
        this.media.remove(media);
        media.setQuestion(null);
        return this;
    }

    public Set<AnswerOption> getAnswerOptions() {
        return this.answerOptions;
    }

    public void setAnswerOptions(Set<AnswerOption> answerOptions) {
        if (this.answerOptions != null) {
            this.answerOptions.forEach(i -> i.setQuestion(null));
        }
        if (answerOptions != null) {
            answerOptions.forEach(i -> i.setQuestion(this));
        }
        this.answerOptions = answerOptions;
    }

    public Question answerOptions(Set<AnswerOption> answerOptions) {
        this.setAnswerOptions(answerOptions);
        return this;
    }

    public Question addAnswerOptions(AnswerOption answerOption) {
        this.answerOptions.add(answerOption);
        answerOption.setQuestion(this);
        return this;
    }

    public Question removeAnswerOptions(AnswerOption answerOption) {
        this.answerOptions.remove(answerOption);
        answerOption.setQuestion(null);
        return this;
    }

    public Set<FillInTheBlank> getFillInTheBlanks() {
        return this.fillInTheBlanks;
    }

    public void setFillInTheBlanks(Set<FillInTheBlank> fillInTheBlanks) {
        if (this.fillInTheBlanks != null) {
            this.fillInTheBlanks.forEach(i -> i.setQuestion(null));
        }
        if (fillInTheBlanks != null) {
            fillInTheBlanks.forEach(i -> i.setQuestion(this));
        }
        this.fillInTheBlanks = fillInTheBlanks;
    }

    public Question fillInTheBlanks(Set<FillInTheBlank> fillInTheBlanks) {
        this.setFillInTheBlanks(fillInTheBlanks);
        return this;
    }

    public Question addFillInTheBlanks(FillInTheBlank fillInTheBlank) {
        this.fillInTheBlanks.add(fillInTheBlank);
        fillInTheBlank.setQuestion(this);
        return this;
    }

    public Question removeFillInTheBlanks(FillInTheBlank fillInTheBlank) {
        this.fillInTheBlanks.remove(fillInTheBlank);
        fillInTheBlank.setQuestion(null);
        return this;
    }

    public Set<BindList> getBindLists() {
        return this.bindLists;
    }

    public void setBindLists(Set<BindList> bindLists) {
        if (this.bindLists != null) {
            this.bindLists.forEach(i -> i.setQuestion(null));
        }
        if (bindLists != null) {
            bindLists.forEach(i -> i.setQuestion(this));
        }
        this.bindLists = bindLists;
    }

    public Question bindLists(Set<BindList> bindLists) {
        this.setBindLists(bindLists);
        return this;
    }

    public Question addBindLists(BindList bindList) {
        this.bindLists.add(bindList);
        bindList.setQuestion(this);
        return this;
    }

    public Question removeBindLists(BindList bindList) {
        this.bindLists.remove(bindList);
        bindList.setQuestion(null);
        return this;
    }

    public Set<CandidateAnswer> getCandidateAnswers() {
        return this.candidateAnswers;
    }

    public void setCandidateAnswers(Set<CandidateAnswer> candidateAnswers) {
        if (this.candidateAnswers != null) {
            this.candidateAnswers.forEach(i -> i.setQuestion(null));
        }
        if (candidateAnswers != null) {
            candidateAnswers.forEach(i -> i.setQuestion(this));
        }
        this.candidateAnswers = candidateAnswers;
    }

    public Question candidateAnswers(Set<CandidateAnswer> candidateAnswers) {
        this.setCandidateAnswers(candidateAnswers);
        return this;
    }

    public Question addCandidateAnswers(CandidateAnswer candidateAnswer) {
        this.candidateAnswers.add(candidateAnswer);
        candidateAnswer.setQuestion(this);
        return this;
    }

    public Question removeCandidateAnswers(CandidateAnswer candidateAnswer) {
        this.candidateAnswers.remove(candidateAnswer);
        candidateAnswer.setQuestion(null);
        return this;
    }

    public Exercise getExercise() {
        return this.exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public Question exercise(Exercise exercise) {
        this.setExercise(exercise);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Question)) {
            return false;
        }
        return getId() != null && getId().equals(((Question) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Question{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            ", explanation='" + getExplanation() + "'" +
            ", points=" + getPoints() +
            ", orderIndex=" + getOrderIndex() +
            "}";
    }
}
