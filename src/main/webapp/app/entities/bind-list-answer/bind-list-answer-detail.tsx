import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './bind-list-answer.reducer';

export const BindListAnswerDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const bindListAnswerEntity = useAppSelector(state => state.bindListAnswer.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bindListAnswerDetailsHeading">
          <Translate contentKey="skillVerifyApp.bindListAnswer.detail.title">BindListAnswer</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{bindListAnswerEntity.id}</dd>
          <dt>
            <span id="leftItemId">
              <Translate contentKey="skillVerifyApp.bindListAnswer.leftItemId">Left Item Id</Translate>
            </span>
          </dt>
          <dd>{bindListAnswerEntity.leftItemId}</dd>
          <dt>
            <span id="rightItemId">
              <Translate contentKey="skillVerifyApp.bindListAnswer.rightItemId">Right Item Id</Translate>
            </span>
          </dt>
          <dd>{bindListAnswerEntity.rightItemId}</dd>
          <dt>
            <span id="isCorrect">
              <Translate contentKey="skillVerifyApp.bindListAnswer.isCorrect">Is Correct</Translate>
            </span>
          </dt>
          <dd>{bindListAnswerEntity.isCorrect ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="skillVerifyApp.bindListAnswer.candidateAnswer">Candidate Answer</Translate>
          </dt>
          <dd>{bindListAnswerEntity.candidateAnswer ? bindListAnswerEntity.candidateAnswer.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/bind-list-answer" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/bind-list-answer/${bindListAnswerEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BindListAnswerDetail;
