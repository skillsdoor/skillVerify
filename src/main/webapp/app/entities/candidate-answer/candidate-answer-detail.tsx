import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './candidate-answer.reducer';

export const CandidateAnswerDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const candidateAnswerEntity = useAppSelector(state => state.candidateAnswer.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="candidateAnswerDetailsHeading">
          <Translate contentKey="skillVerifyApp.candidateAnswer.detail.title">CandidateAnswer</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{candidateAnswerEntity.id}</dd>
          <dt>
            <span id="answerText">
              <Translate contentKey="skillVerifyApp.candidateAnswer.answerText">Answer Text</Translate>
            </span>
          </dt>
          <dd>{candidateAnswerEntity.answerText}</dd>
          <dt>
            <span id="selectedOptionId">
              <Translate contentKey="skillVerifyApp.candidateAnswer.selectedOptionId">Selected Option Id</Translate>
            </span>
          </dt>
          <dd>{candidateAnswerEntity.selectedOptionId}</dd>
          <dt>
            <span id="isCorrect">
              <Translate contentKey="skillVerifyApp.candidateAnswer.isCorrect">Is Correct</Translate>
            </span>
          </dt>
          <dd>{candidateAnswerEntity.isCorrect ? 'true' : 'false'}</dd>
          <dt>
            <span id="pointsAwarded">
              <Translate contentKey="skillVerifyApp.candidateAnswer.pointsAwarded">Points Awarded</Translate>
            </span>
          </dt>
          <dd>{candidateAnswerEntity.pointsAwarded}</dd>
          <dt>
            <span id="answeredAt">
              <Translate contentKey="skillVerifyApp.candidateAnswer.answeredAt">Answered At</Translate>
            </span>
          </dt>
          <dd>
            {candidateAnswerEntity.answeredAt ? (
              <TextFormat value={candidateAnswerEntity.answeredAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="skillVerifyApp.candidateAnswer.examSession">Exam Session</Translate>
          </dt>
          <dd>{candidateAnswerEntity.examSession ? candidateAnswerEntity.examSession.id : ''}</dd>
          <dt>
            <Translate contentKey="skillVerifyApp.candidateAnswer.question">Question</Translate>
          </dt>
          <dd>{candidateAnswerEntity.question ? candidateAnswerEntity.question.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/candidate-answer" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/candidate-answer/${candidateAnswerEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CandidateAnswerDetail;
