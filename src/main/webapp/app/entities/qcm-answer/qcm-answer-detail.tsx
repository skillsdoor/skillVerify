import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './qcm-answer.reducer';

export const QcmAnswerDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const qcmAnswerEntity = useAppSelector(state => state.qcmAnswer.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="qcmAnswerDetailsHeading">
          <Translate contentKey="skillVerifyApp.qcmAnswer.detail.title">QcmAnswer</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{qcmAnswerEntity.id}</dd>
          <dt>
            <span id="isCorrect">
              <Translate contentKey="skillVerifyApp.qcmAnswer.isCorrect">Is Correct</Translate>
            </span>
          </dt>
          <dd>{qcmAnswerEntity.isCorrect ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="skillVerifyApp.qcmAnswer.candidateAnswer">Candidate Answer</Translate>
          </dt>
          <dd>{qcmAnswerEntity.candidateAnswer ? qcmAnswerEntity.candidateAnswer.id : ''}</dd>
          <dt>
            <Translate contentKey="skillVerifyApp.qcmAnswer.qcmOption">Qcm Option</Translate>
          </dt>
          <dd>{qcmAnswerEntity.qcmOption ? qcmAnswerEntity.qcmOption.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/qcm-answer" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/qcm-answer/${qcmAnswerEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default QcmAnswerDetail;
