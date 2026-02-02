package com.skillsdoor.skillverify.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CandidateAnswer.
 */
@Entity
@Table(name = "candidate_answer")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CandidateAnswer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Lob
    @Column(name = "answer_text")
    private String answerText;

    @Column(name = "is_correct")
    private Boolean isCorrect;

    @Column(name = "points_awarded")
    private Integer pointsAwarded;

    @Column(name = "answered_at")
    private Instant answeredAt;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "candidateAnswer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "candidateAnswer", "qcmOption" }, allowSetters = true)
    private Set<QcmAnswer> qcmAnswers = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "candidateAnswer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "candidateAnswer", "blankSpace" }, allowSetters = true)
    private Set<BlankAnswer> blankAnswers = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "candidateAnswer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "candidateAnswer" }, allowSetters = true)
    private Set<BindAnswer> bindAnswers = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "candidateAnswers", "candidate", "exam" }, allowSetters = true)
    private ExamSession examSession;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "media", "qcmOptions", "blankSpaces", "listItems", "candidateAnswers", "exam", "questions" },
        allowSetters = true
    )
    private Exercise exercise;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "media", "answerOptions", "fillInTheBlanks", "bindLists", "candidateAnswers", "exercise" },
        allowSetters = true
    )
    private Question question;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "candidateAnswer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "candidateAnswer" }, allowSetters = true)
    private Set<BindListAnswer> bindListAnswers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CandidateAnswer id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnswerText() {
        return this.answerText;
    }

    public CandidateAnswer answerText(String answerText) {
        this.setAnswerText(answerText);
        return this;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public Boolean getIsCorrect() {
        return this.isCorrect;
    }

    public CandidateAnswer isCorrect(Boolean isCorrect) {
        this.setIsCorrect(isCorrect);
        return this;
    }

    public void setIsCorrect(Boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public Integer getPointsAwarded() {
        return this.pointsAwarded;
    }

    public CandidateAnswer pointsAwarded(Integer pointsAwarded) {
        this.setPointsAwarded(pointsAwarded);
        return this;
    }

    public void setPointsAwarded(Integer pointsAwarded) {
        this.pointsAwarded = pointsAwarded;
    }

    public Instant getAnsweredAt() {
        return this.answeredAt;
    }

    public CandidateAnswer answeredAt(Instant answeredAt) {
        this.setAnsweredAt(answeredAt);
        return this;
    }

    public void setAnsweredAt(Instant answeredAt) {
        this.answeredAt = answeredAt;
    }

    public Set<QcmAnswer> getQcmAnswers() {
        return this.qcmAnswers;
    }

    public void setQcmAnswers(Set<QcmAnswer> qcmAnswers) {
        if (this.qcmAnswers != null) {
            this.qcmAnswers.forEach(i -> i.setCandidateAnswer(null));
        }
        if (qcmAnswers != null) {
            qcmAnswers.forEach(i -> i.setCandidateAnswer(this));
        }
        this.qcmAnswers = qcmAnswers;
    }

    public CandidateAnswer qcmAnswers(Set<QcmAnswer> qcmAnswers) {
        this.setQcmAnswers(qcmAnswers);
        return this;
    }

    public CandidateAnswer addQcmAnswers(QcmAnswer qcmAnswer) {
        this.qcmAnswers.add(qcmAnswer);
        qcmAnswer.setCandidateAnswer(this);
        return this;
    }

    public CandidateAnswer removeQcmAnswers(QcmAnswer qcmAnswer) {
        this.qcmAnswers.remove(qcmAnswer);
        qcmAnswer.setCandidateAnswer(null);
        return this;
    }

    public Set<BlankAnswer> getBlankAnswers() {
        return this.blankAnswers;
    }

    public void setBlankAnswers(Set<BlankAnswer> blankAnswers) {
        if (this.blankAnswers != null) {
            this.blankAnswers.forEach(i -> i.setCandidateAnswer(null));
        }
        if (blankAnswers != null) {
            blankAnswers.forEach(i -> i.setCandidateAnswer(this));
        }
        this.blankAnswers = blankAnswers;
    }

    public CandidateAnswer blankAnswers(Set<BlankAnswer> blankAnswers) {
        this.setBlankAnswers(blankAnswers);
        return this;
    }

    public CandidateAnswer addBlankAnswers(BlankAnswer blankAnswer) {
        this.blankAnswers.add(blankAnswer);
        blankAnswer.setCandidateAnswer(this);
        return this;
    }

    public CandidateAnswer removeBlankAnswers(BlankAnswer blankAnswer) {
        this.blankAnswers.remove(blankAnswer);
        blankAnswer.setCandidateAnswer(null);
        return this;
    }

    public Set<BindAnswer> getBindAnswers() {
        return this.bindAnswers;
    }

    public void setBindAnswers(Set<BindAnswer> bindAnswers) {
        if (this.bindAnswers != null) {
            this.bindAnswers.forEach(i -> i.setCandidateAnswer(null));
        }
        if (bindAnswers != null) {
            bindAnswers.forEach(i -> i.setCandidateAnswer(this));
        }
        this.bindAnswers = bindAnswers;
    }

    public CandidateAnswer bindAnswers(Set<BindAnswer> bindAnswers) {
        this.setBindAnswers(bindAnswers);
        return this;
    }

    public CandidateAnswer addBindAnswers(BindAnswer bindAnswer) {
        this.bindAnswers.add(bindAnswer);
        bindAnswer.setCandidateAnswer(this);
        return this;
    }

    public CandidateAnswer removeBindAnswers(BindAnswer bindAnswer) {
        this.bindAnswers.remove(bindAnswer);
        bindAnswer.setCandidateAnswer(null);
        return this;
    }

    public ExamSession getExamSession() {
        return this.examSession;
    }

    public void setExamSession(ExamSession examSession) {
        this.examSession = examSession;
    }

    public CandidateAnswer examSession(ExamSession examSession) {
        this.setExamSession(examSession);
        return this;
    }

    public Exercise getExercise() {
        return this.exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public CandidateAnswer exercise(Exercise exercise) {
        this.setExercise(exercise);
        return this;
    }

    public Question getQuestion() {
        return this.question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public CandidateAnswer question(Question question) {
        this.setQuestion(question);
        return this;
    }

    public Set<BindListAnswer> getBindListAnswers() {
        return this.bindListAnswers;
    }

    public void setBindListAnswers(Set<BindListAnswer> bindListAnswers) {
        if (this.bindListAnswers != null) {
            this.bindListAnswers.forEach(i -> i.setCandidateAnswer(null));
        }
        if (bindListAnswers != null) {
            bindListAnswers.forEach(i -> i.setCandidateAnswer(this));
        }
        this.bindListAnswers = bindListAnswers;
    }

    public CandidateAnswer bindListAnswers(Set<BindListAnswer> bindListAnswers) {
        this.setBindListAnswers(bindListAnswers);
        return this;
    }

    public CandidateAnswer addBindListAnswers(BindListAnswer bindListAnswer) {
        this.bindListAnswers.add(bindListAnswer);
        bindListAnswer.setCandidateAnswer(this);
        return this;
    }

    public CandidateAnswer removeBindListAnswers(BindListAnswer bindListAnswer) {
        this.bindListAnswers.remove(bindListAnswer);
        bindListAnswer.setCandidateAnswer(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CandidateAnswer)) {
            return false;
        }
        return getId() != null && getId().equals(((CandidateAnswer) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CandidateAnswer{" +
            "id=" + getId() +
            ", answerText='" + getAnswerText() + "'" +
            ", isCorrect='" + getIsCorrect() + "'" +
            ", pointsAwarded=" + getPointsAwarded() +
            ", answeredAt='" + getAnsweredAt() + "'" +
            "}";
    }
}
