package com.skillsdoor.skillverify.domain;

import static com.skillsdoor.skillverify.domain.MediaTestSamples.*;
import static com.skillsdoor.skillverify.domain.QuestionTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.skillsdoor.skillverify.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MediaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Media.class);
        Media media1 = getMediaSample1();
        Media media2 = new Media();
        assertThat(media1).isNotEqualTo(media2);

        media2.setId(media1.getId());
        assertThat(media1).isEqualTo(media2);

        media2 = getMediaSample2();
        assertThat(media1).isNotEqualTo(media2);
    }

    @Test
    void questionTest() {
        Media media = getMediaRandomSampleGenerator();
        Question questionBack = getQuestionRandomSampleGenerator();

        media.setQuestion(questionBack);
        assertThat(media.getQuestion()).isEqualTo(questionBack);

        media.question(null);
        assertThat(media.getQuestion()).isNull();
    }
}
