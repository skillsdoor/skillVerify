import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './blank-answer.reducer';

export const BlankAnswerDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const blankAnswerEntity = useAppSelector(state => state.blankAnswer.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="blankAnswerDetailsHeading">
          <Translate contentKey="skillVerifyApp.blankAnswer.detail.title">BlankAnswer</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{blankAnswerEntity.id}</dd>
          <dt>
            <span id="answerText">
              <Translate contentKey="skillVerifyApp.blankAnswer.answerText">Answer Text</Translate>
            </span>
          </dt>
          <dd>{blankAnswerEntity.answerText}</dd>
          <dt>
            <span id="isCorrect">
              <Translate contentKey="skillVerifyApp.blankAnswer.isCorrect">Is Correct</Translate>
            </span>
          </dt>
          <dd>{blankAnswerEntity.isCorrect ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="skillVerifyApp.blankAnswer.candidateAnswer">Candidate Answer</Translate>
          </dt>
          <dd>{blankAnswerEntity.candidateAnswer ? blankAnswerEntity.candidateAnswer.id : ''}</dd>
          <dt>
            <Translate contentKey="skillVerifyApp.blankAnswer.blankSpace">Blank Space</Translate>
          </dt>
          <dd>{blankAnswerEntity.blankSpace ? blankAnswerEntity.blankSpace.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/blank-answer" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/blank-answer/${blankAnswerEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BlankAnswerDetail;
