package com.skillsdoor.skillverify.service.dto;

import com.skillsdoor.skillverify.domain.enumeration.MediaType;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.skillsdoor.skillverify.domain.Media} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MediaDTO implements Serializable {

    private Long id;

    @NotNull
    private MediaType type;

    @NotNull
    private String s3Key;

    @NotNull
    private String s3Url;

    @NotNull
    private String fileName;

    private Long fileSize;

    private String mimeType;

    private Instant uploadedAt;

    private QuestionDTO question;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MediaType getType() {
        return type;
    }

    public void setType(MediaType type) {
        this.type = type;
    }

    public String gets3Key() {
        return s3Key;
    }

    public void sets3Key(String s3Key) {
        this.s3Key = s3Key;
    }

    public String gets3Url() {
        return s3Url;
    }

    public void sets3Url(String s3Url) {
        this.s3Url = s3Url;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public Instant getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(Instant uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    public QuestionDTO getQuestion() {
        return question;
    }

    public void setQuestion(QuestionDTO question) {
        this.question = question;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MediaDTO)) {
            return false;
        }

        MediaDTO mediaDTO = (MediaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, mediaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MediaDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", s3Key='" + gets3Key() + "'" +
            ", s3Url='" + gets3Url() + "'" +
            ", fileName='" + getFileName() + "'" +
            ", fileSize=" + getFileSize() +
            ", mimeType='" + getMimeType() + "'" +
            ", uploadedAt='" + getUploadedAt() + "'" +
            ", question=" + getQuestion() +
            "}";
    }
}
