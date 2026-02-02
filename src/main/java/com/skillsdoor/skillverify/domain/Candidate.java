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
 * A Candidate.
 */
@Entity
@Table(name = "candidate")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Candidate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "phone")
    private String phone;

    @Lob
    @Column(name = "bio")
    private String bio;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "resume_url")
    private String resumeUrl;

    @Column(name = "linkedin_url")
    private String linkedinUrl;

    @Column(name = "github_url")
    private String githubUrl;

    @Column(name = "years_of_experience")
    private Integer yearsOfExperience;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "candidate")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "candidateAnswers", "candidate", "exam" }, allowSetters = true)
    private Set<ExamSession> examSessions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Candidate id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Candidate firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Candidate lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return this.phone;
    }

    public Candidate phone(String phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBio() {
        return this.bio;
    }

    public Candidate bio(String bio) {
        this.setBio(bio);
        return this;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getAvatarUrl() {
        return this.avatarUrl;
    }

    public Candidate avatarUrl(String avatarUrl) {
        this.setAvatarUrl(avatarUrl);
        return this;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getResumeUrl() {
        return this.resumeUrl;
    }

    public Candidate resumeUrl(String resumeUrl) {
        this.setResumeUrl(resumeUrl);
        return this;
    }

    public void setResumeUrl(String resumeUrl) {
        this.resumeUrl = resumeUrl;
    }

    public String getLinkedinUrl() {
        return this.linkedinUrl;
    }

    public Candidate linkedinUrl(String linkedinUrl) {
        this.setLinkedinUrl(linkedinUrl);
        return this;
    }

    public void setLinkedinUrl(String linkedinUrl) {
        this.linkedinUrl = linkedinUrl;
    }

    public String getGithubUrl() {
        return this.githubUrl;
    }

    public Candidate githubUrl(String githubUrl) {
        this.setGithubUrl(githubUrl);
        return this;
    }

    public void setGithubUrl(String githubUrl) {
        this.githubUrl = githubUrl;
    }

    public Integer getYearsOfExperience() {
        return this.yearsOfExperience;
    }

    public Candidate yearsOfExperience(Integer yearsOfExperience) {
        this.setYearsOfExperience(yearsOfExperience);
        return this;
    }

    public void setYearsOfExperience(Integer yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Candidate createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public Candidate updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Candidate user(User user) {
        this.setUser(user);
        return this;
    }

    public Set<ExamSession> getExamSessions() {
        return this.examSessions;
    }

    public void setExamSessions(Set<ExamSession> examSessions) {
        if (this.examSessions != null) {
            this.examSessions.forEach(i -> i.setCandidate(null));
        }
        if (examSessions != null) {
            examSessions.forEach(i -> i.setCandidate(this));
        }
        this.examSessions = examSessions;
    }

    public Candidate examSessions(Set<ExamSession> examSessions) {
        this.setExamSessions(examSessions);
        return this;
    }

    public Candidate addExamSessions(ExamSession examSession) {
        this.examSessions.add(examSession);
        examSession.setCandidate(this);
        return this;
    }

    public Candidate removeExamSessions(ExamSession examSession) {
        this.examSessions.remove(examSession);
        examSession.setCandidate(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Candidate)) {
            return false;
        }
        return getId() != null && getId().equals(((Candidate) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Candidate{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", phone='" + getPhone() + "'" +
            ", bio='" + getBio() + "'" +
            ", avatarUrl='" + getAvatarUrl() + "'" +
            ", resumeUrl='" + getResumeUrl() + "'" +
            ", linkedinUrl='" + getLinkedinUrl() + "'" +
            ", githubUrl='" + getGithubUrl() + "'" +
            ", yearsOfExperience=" + getYearsOfExperience() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
