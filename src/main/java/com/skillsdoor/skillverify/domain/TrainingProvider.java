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
 * A TrainingProvider.
 */
@Entity
@Table(name = "training_provider")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TrainingProvider implements Serializable {

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

    @NotNull
    @Column(name = "organization_name", nullable = false)
    private String organizationName;

    @Column(name = "website")
    private String website;

    @Column(name = "accreditation")
    private String accreditation;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "trainingProvider")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "exercises", "examSessions", "recruiter", "trainingProvider" }, allowSetters = true)
    private Set<Exam> exams = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TrainingProvider id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public TrainingProvider firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public TrainingProvider lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return this.phone;
    }

    public TrainingProvider phone(String phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBio() {
        return this.bio;
    }

    public TrainingProvider bio(String bio) {
        this.setBio(bio);
        return this;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getAvatarUrl() {
        return this.avatarUrl;
    }

    public TrainingProvider avatarUrl(String avatarUrl) {
        this.setAvatarUrl(avatarUrl);
        return this;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getOrganizationName() {
        return this.organizationName;
    }

    public TrainingProvider organizationName(String organizationName) {
        this.setOrganizationName(organizationName);
        return this;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getWebsite() {
        return this.website;
    }

    public TrainingProvider website(String website) {
        this.setWebsite(website);
        return this;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getAccreditation() {
        return this.accreditation;
    }

    public TrainingProvider accreditation(String accreditation) {
        this.setAccreditation(accreditation);
        return this;
    }

    public void setAccreditation(String accreditation) {
        this.accreditation = accreditation;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public TrainingProvider createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public TrainingProvider updatedAt(Instant updatedAt) {
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

    public TrainingProvider user(User user) {
        this.setUser(user);
        return this;
    }

    public Set<Exam> getExams() {
        return this.exams;
    }

    public void setExams(Set<Exam> exams) {
        if (this.exams != null) {
            this.exams.forEach(i -> i.setTrainingProvider(null));
        }
        if (exams != null) {
            exams.forEach(i -> i.setTrainingProvider(this));
        }
        this.exams = exams;
    }

    public TrainingProvider exams(Set<Exam> exams) {
        this.setExams(exams);
        return this;
    }

    public TrainingProvider addExams(Exam exam) {
        this.exams.add(exam);
        exam.setTrainingProvider(this);
        return this;
    }

    public TrainingProvider removeExams(Exam exam) {
        this.exams.remove(exam);
        exam.setTrainingProvider(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TrainingProvider)) {
            return false;
        }
        return getId() != null && getId().equals(((TrainingProvider) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TrainingProvider{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", phone='" + getPhone() + "'" +
            ", bio='" + getBio() + "'" +
            ", avatarUrl='" + getAvatarUrl() + "'" +
            ", organizationName='" + getOrganizationName() + "'" +
            ", website='" + getWebsite() + "'" +
            ", accreditation='" + getAccreditation() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
