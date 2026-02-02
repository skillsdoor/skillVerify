package com.skillsdoor.skillverify.config;

import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(
                Object.class,
                Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries())
            )
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build()
        );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.skillsdoor.skillverify.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.skillsdoor.skillverify.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.skillsdoor.skillverify.domain.User.class.getName());
            createCache(cm, com.skillsdoor.skillverify.domain.Authority.class.getName());
            createCache(cm, com.skillsdoor.skillverify.domain.User.class.getName() + ".authorities");
            createCache(cm, com.skillsdoor.skillverify.domain.Recruiter.class.getName());
            createCache(cm, com.skillsdoor.skillverify.domain.Recruiter.class.getName() + ".exams");
            createCache(cm, com.skillsdoor.skillverify.domain.Candidate.class.getName());
            createCache(cm, com.skillsdoor.skillverify.domain.Candidate.class.getName() + ".examSessions");
            createCache(cm, com.skillsdoor.skillverify.domain.TrainingProvider.class.getName());
            createCache(cm, com.skillsdoor.skillverify.domain.TrainingProvider.class.getName() + ".exams");
            createCache(cm, com.skillsdoor.skillverify.domain.Exam.class.getName());
            createCache(cm, com.skillsdoor.skillverify.domain.Exam.class.getName() + ".exercises");
            createCache(cm, com.skillsdoor.skillverify.domain.Exam.class.getName() + ".examSessions");
            createCache(cm, com.skillsdoor.skillverify.domain.Exercise.class.getName());
            createCache(cm, com.skillsdoor.skillverify.domain.Exercise.class.getName() + ".questions");
            createCache(cm, com.skillsdoor.skillverify.domain.Question.class.getName());
            createCache(cm, com.skillsdoor.skillverify.domain.Question.class.getName() + ".media");
            createCache(cm, com.skillsdoor.skillverify.domain.Question.class.getName() + ".answerOptions");
            createCache(cm, com.skillsdoor.skillverify.domain.Question.class.getName() + ".fillInTheBlanks");
            createCache(cm, com.skillsdoor.skillverify.domain.Question.class.getName() + ".bindLists");
            createCache(cm, com.skillsdoor.skillverify.domain.Question.class.getName() + ".candidateAnswers");
            createCache(cm, com.skillsdoor.skillverify.domain.Media.class.getName());
            createCache(cm, com.skillsdoor.skillverify.domain.AnswerOption.class.getName());
            createCache(cm, com.skillsdoor.skillverify.domain.FillInTheBlank.class.getName());
            createCache(cm, com.skillsdoor.skillverify.domain.BindList.class.getName());
            createCache(cm, com.skillsdoor.skillverify.domain.ExamSession.class.getName());
            createCache(cm, com.skillsdoor.skillverify.domain.ExamSession.class.getName() + ".candidateAnswers");
            createCache(cm, com.skillsdoor.skillverify.domain.CandidateAnswer.class.getName());
            createCache(cm, com.skillsdoor.skillverify.domain.CandidateAnswer.class.getName() + ".bindListAnswers");
            createCache(cm, com.skillsdoor.skillverify.domain.BindListAnswer.class.getName());
            createCache(cm, com.skillsdoor.skillverify.domain.Exercise.class.getName() + ".media");
            createCache(cm, com.skillsdoor.skillverify.domain.Exercise.class.getName() + ".qcmOptions");
            createCache(cm, com.skillsdoor.skillverify.domain.Exercise.class.getName() + ".blankSpaces");
            createCache(cm, com.skillsdoor.skillverify.domain.Exercise.class.getName() + ".listItems");
            createCache(cm, com.skillsdoor.skillverify.domain.Exercise.class.getName() + ".candidateAnswers");
            createCache(cm, com.skillsdoor.skillverify.domain.CandidateAnswer.class.getName() + ".qcmAnswers");
            createCache(cm, com.skillsdoor.skillverify.domain.CandidateAnswer.class.getName() + ".blankAnswers");
            createCache(cm, com.skillsdoor.skillverify.domain.CandidateAnswer.class.getName() + ".bindAnswers");
            createCache(cm, com.skillsdoor.skillverify.domain.QcmOption.class.getName());
            createCache(cm, com.skillsdoor.skillverify.domain.QcmOption.class.getName() + ".qcmAnswers");
            createCache(cm, com.skillsdoor.skillverify.domain.BlankSpace.class.getName());
            createCache(cm, com.skillsdoor.skillverify.domain.BlankSpace.class.getName() + ".blankAnswers");
            createCache(cm, com.skillsdoor.skillverify.domain.ListItem.class.getName());
            createCache(cm, com.skillsdoor.skillverify.domain.QcmAnswer.class.getName());
            createCache(cm, com.skillsdoor.skillverify.domain.BlankAnswer.class.getName());
            createCache(cm, com.skillsdoor.skillverify.domain.BindAnswer.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
