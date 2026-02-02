package com.skillsdoor.skillverify.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.skillsdoor.skillverify.domain.enumeration.ExamStatus;
import com.skillsdoor.skillverify.domain.enumeration.TestType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Exam.
 */
@Entity
@Table(name = "exam")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Exam implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @Lob
    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "duration", nullable = false)
    private Integer duration;

    @NotNull
    @Column(name = "passing_score", nullable = false)
    private Integer passingScore;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ExamStatus status;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "test_type", nullable = false)
    private TestType testType;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "published_at")
    private Instant publishedAt;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "exam")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "media", "qcmOptions", "blankSpaces", "listItems", "candidateAnswers", "exam", "questions" },
        allowSetters = true
    )
    private Set<Exercise> exercises = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "exam")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "candidateAnswers", "candidate", "exam" }, allowSetters = true)
    private Set<ExamSession> examSessions = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "user", "exams" }, allowSetters = true)
    private Recruiter recruiter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "user", "exams" }, allowSetters = true)
    private TrainingProvider trainingProvider;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Exam id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Exam title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public Exam description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDuration() {
        return this.duration;
    }

    public Exam duration(Integer duration) {
        this.setDuration(duration);
        return this;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getPassingScore() {
        return this.passingScore;
    }

    public Exam passingScore(Integer passingScore) {
        this.setPassingScore(passingScore);
        return this;
    }

    public void setPassingScore(Integer passingScore) {
        this.passingScore = passingScore;
    }

    public ExamStatus getStatus() {
        return this.status;
    }

    public Exam status(ExamStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(ExamStatus status) {
        this.status = status;
    }

    public TestType getTestType() {
        return this.testType;
    }

    public Exam testType(TestType testType) {
        this.setTestType(testType);
        return this;
    }

    public void setTestType(TestType testType) {
        this.testType = testType;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Exam createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public Exam updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getPublishedAt() {
        return this.publishedAt;
    }

    public Exam publishedAt(Instant publishedAt) {
        this.setPublishedAt(publishedAt);
        return this;
    }

    public void setPublishedAt(Instant publishedAt) {
        this.publishedAt = publishedAt;
    }

    public Set<Exercise> getExercises() {
        return this.exercises;
    }

    public void setExercises(Set<Exercise> exercises) {
        if (this.exercises != null) {
            this.exercises.forEach(i -> i.setExam(null));
        }
        if (exercises != null) {
            exercises.forEach(i -> i.setExam(this));
        }
        this.exercises = exercises;
    }

    public Exam exercises(Set<Exercise> exercises) {
        this.setExercises(exercises);
        return this;
    }

    public Exam addExercises(Exercise exercise) {
        this.exercises.add(exercise);
        exercise.setExam(this);
        return this;
    }

    public Exam removeExercises(Exercise exercise) {
        this.exercises.remove(exercise);
        exercise.setExam(null);
        return this;
    }

    public Set<ExamSession> getExamSessions() {
        return this.examSessions;
    }

    public void setExamSessions(Set<ExamSession> examSessions) {
        if (this.examSessions != null) {
            this.examSessions.forEach(i -> i.setExam(null));
        }
        if (examSessions != null) {
            examSessions.forEach(i -> i.setExam(this));
        }
        this.examSessions = examSessions;
    }

    public Exam examSessions(Set<ExamSession> examSessions) {
        this.setExamSessions(examSessions);
        return this;
    }

    public Exam addExamSessions(ExamSession examSession) {
        this.examSessions.add(examSession);
        examSession.setExam(this);
        return this;
    }

    public Exam removeExamSessions(ExamSession examSession) {
        this.examSessions.remove(examSession);
        examSession.setExam(null);
        return this;
    }

    public Recruiter getRecruiter() {
        return this.recruiter;
    }

    public void setRecruiter(Recruiter recruiter) {
        this.recruiter = recruiter;
    }

    public Exam recruiter(Recruiter recruiter) {
        this.setRecruiter(recruiter);
        return this;
    }

    public TrainingProvider getTrainingProvider() {
        return this.trainingProvider;
    }

    public void setTrainingProvider(TrainingProvider trainingProvider) {
        this.trainingProvider = trainingProvider;
    }

    public Exam trainingProvider(TrainingProvider trainingProvider) {
        this.setTrainingProvider(trainingProvider);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Exam)) {
            return false;
        }
        return getId() != null && getId().equals(((Exam) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Exam{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", duration=" + getDuration() +
            ", passingScore=" + getPassingScore() +
            ", status='" + getStatus() + "'" +
            ", testType='" + getTestType() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", publishedAt='" + getPublishedAt() + "'" +
            "}";
    }
}
