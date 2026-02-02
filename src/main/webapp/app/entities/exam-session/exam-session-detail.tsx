import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './exam-session.reducer';

export const ExamSessionDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const examSessionEntity = useAppSelector(state => state.examSession.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="examSessionDetailsHeading">
          <Translate contentKey="skillVerifyApp.examSession.detail.title">ExamSession</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{examSessionEntity.id}</dd>
          <dt>
            <span id="startedAt">
              <Translate contentKey="skillVerifyApp.examSession.startedAt">Started At</Translate>
            </span>
          </dt>
          <dd>
            {examSessionEntity.startedAt ? <TextFormat value={examSessionEntity.startedAt} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="submittedAt">
              <Translate contentKey="skillVerifyApp.examSession.submittedAt">Submitted At</Translate>
            </span>
          </dt>
          <dd>
            {examSessionEntity.submittedAt ? (
              <TextFormat value={examSessionEntity.submittedAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="status">
              <Translate contentKey="skillVerifyApp.examSession.status">Status</Translate>
            </span>
          </dt>
          <dd>{examSessionEntity.status}</dd>
          <dt>
            <span id="score">
              <Translate contentKey="skillVerifyApp.examSession.score">Score</Translate>
            </span>
          </dt>
          <dd>{examSessionEntity.score}</dd>
          <dt>
            <span id="totalPoints">
              <Translate contentKey="skillVerifyApp.examSession.totalPoints">Total Points</Translate>
            </span>
          </dt>
          <dd>{examSessionEntity.totalPoints}</dd>
          <dt>
            <span id="timeSpent">
              <Translate contentKey="skillVerifyApp.examSession.timeSpent">Time Spent</Translate>
            </span>
          </dt>
          <dd>{examSessionEntity.timeSpent}</dd>
          <dt>
            <Translate contentKey="skillVerifyApp.examSession.candidate">Candidate</Translate>
          </dt>
          <dd>{examSessionEntity.candidate ? examSessionEntity.candidate.id : ''}</dd>
          <dt>
            <Translate contentKey="skillVerifyApp.examSession.exam">Exam</Translate>
          </dt>
          <dd>{examSessionEntity.exam ? examSessionEntity.exam.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/exam-session" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/exam-session/${examSessionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ExamSessionDetail;
