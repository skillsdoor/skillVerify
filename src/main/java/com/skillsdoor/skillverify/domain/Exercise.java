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

    @Lob
    @Column(name = "content")
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "exercise")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "exercise", "question" }, allowSetters = true)
    private Set<Media> media = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "exercise", cascade = CascadeType.ALL, orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "qcmAnswers", "exercise" }, allowSetters = true)
    private Set<QcmOption> qcmOptions = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "exercise", cascade = CascadeType.ALL, orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "blankAnswers", "exercise" }, allowSetters = true)
    private Set<BlankSpace> blankSpaces = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "exercise", cascade = CascadeType.ALL, orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "exercise" }, allowSetters = true)
    private Set<ListItem> listItems = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "exercise")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "qcmAnswers", "blankAnswers", "bindAnswers", "examSession", "exercise", "question", "bindListAnswers" },
        allowSetters = true
    )
    private Set<CandidateAnswer> candidateAnswers = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "exercises", "examSessions", "recruiter", "trainingProvider" }, allowSetters = true)
    private Exam exam;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "exercise")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "media", "answerOptions", "fillInTheBlanks", "bindLists", "candidateAnswers", "exercise" },
        allowSetters = true
    )
    private Set<Question> questions = new HashSet<>();

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

    public String getContent() {
        return this.content;
    }

    public Exercise content(String content) {
        this.setContent(content);
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExplanation() {
        return this.explanation;
    }

    public Exercise explanation(String explanation) {
        this.setExplanation(explanation);
        return this;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
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

    public Set<Media> getMedia() {
        return this.media;
    }

    public void setMedia(Set<Media> media) {
        if (this.media != null) {
            this.media.forEach(i -> i.setExercise(null));
        }
        if (media != null) {
            media.forEach(i -> i.setExercise(this));
        }
        this.media = media;
    }

    public Exercise media(Set<Media> media) {
        this.setMedia(media);
        return this;
    }

    public Exercise addMedia(Media media) {
        this.media.add(media);
        media.setExercise(this);
        return this;
    }

    public Exercise removeMedia(Media media) {
        this.media.remove(media);
        media.setExercise(null);
        return this;
    }

    public Set<QcmOption> getQcmOptions() {
        return this.qcmOptions;
    }

    public void setQcmOptions(Set<QcmOption> qcmOptions) {
        if (this.qcmOptions != null) {
            this.qcmOptions.forEach(i -> i.setExercise(null));
        }
        if (qcmOptions != null) {
            qcmOptions.forEach(i -> i.setExercise(this));
        }
        this.qcmOptions = qcmOptions;
    }

    public Exercise qcmOptions(Set<QcmOption> qcmOptions) {
        this.setQcmOptions(qcmOptions);
        return this;
    }

    public Exercise addQcmOptions(QcmOption qcmOption) {
        this.qcmOptions.add(qcmOption);
        qcmOption.setExercise(this);
        return this;
    }

    public Exercise removeQcmOptions(QcmOption qcmOption) {
        this.qcmOptions.remove(qcmOption);
        qcmOption.setExercise(null);
        return this;
    }

    public Set<BlankSpace> getBlankSpaces() {
        return this.blankSpaces;
    }

    public void setBlankSpaces(Set<BlankSpace> blankSpaces) {
        if (this.blankSpaces != null) {
            this.blankSpaces.forEach(i -> i.setExercise(null));
        }
        if (blankSpaces != null) {
            blankSpaces.forEach(i -> i.setExercise(this));
        }
        this.blankSpaces = blankSpaces;
    }

    public Exercise blankSpaces(Set<BlankSpace> blankSpaces) {
        this.setBlankSpaces(blankSpaces);
        return this;
    }

    public Exercise addBlankSpaces(BlankSpace blankSpace) {
        this.blankSpaces.add(blankSpace);
        blankSpace.setExercise(this);
        return this;
    }

    public Exercise removeBlankSpaces(BlankSpace blankSpace) {
        this.blankSpaces.remove(blankSpace);
        blankSpace.setExercise(null);
        return this;
    }

    public Set<ListItem> getListItems() {
        return this.listItems;
    }

    public void setListItems(Set<ListItem> listItems) {
        if (this.listItems != null) {
            this.listItems.forEach(i -> i.setExercise(null));
        }
        if (listItems != null) {
            listItems.forEach(i -> i.setExercise(this));
        }
        this.listItems = listItems;
    }

    public Exercise listItems(Set<ListItem> listItems) {
        this.setListItems(listItems);
        return this;
    }

    public Exercise addListItems(ListItem listItem) {
        this.listItems.add(listItem);
        listItem.setExercise(this);
        return this;
    }

    public Exercise removeListItems(ListItem listItem) {
        this.listItems.remove(listItem);
        listItem.setExercise(null);
        return this;
    }

    public Set<CandidateAnswer> getCandidateAnswers() {
        return this.candidateAnswers;
    }

    public void setCandidateAnswers(Set<CandidateAnswer> candidateAnswers) {
        if (this.candidateAnswers != null) {
            this.candidateAnswers.forEach(i -> i.setExercise(null));
        }
        if (candidateAnswers != null) {
            candidateAnswers.forEach(i -> i.setExercise(this));
        }
        this.candidateAnswers = candidateAnswers;
    }

    public Exercise candidateAnswers(Set<CandidateAnswer> candidateAnswers) {
        this.setCandidateAnswers(candidateAnswers);
        return this;
    }

    public Exercise addCandidateAnswers(CandidateAnswer candidateAnswer) {
        this.candidateAnswers.add(candidateAnswer);
        candidateAnswer.setExercise(this);
        return this;
    }

    public Exercise removeCandidateAnswers(CandidateAnswer candidateAnswer) {
        this.candidateAnswers.remove(candidateAnswer);
        candidateAnswer.setExercise(null);
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
            ", content='" + getContent() + "'" +
            ", explanation='" + getExplanation() + "'" +
            ", points=" + getPoints() +
            ", orderIndex=" + getOrderIndex() +
            "}";
    }
}
