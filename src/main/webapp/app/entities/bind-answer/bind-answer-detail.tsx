import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './bind-answer.reducer';

export const BindAnswerDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const bindAnswerEntity = useAppSelector(state => state.bindAnswer.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bindAnswerDetailsHeading">
          <Translate contentKey="skillVerifyApp.bindAnswer.detail.title">BindAnswer</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{bindAnswerEntity.id}</dd>
          <dt>
            <span id="leftItemId">
              <Translate contentKey="skillVerifyApp.bindAnswer.leftItemId">Left Item Id</Translate>
            </span>
          </dt>
          <dd>{bindAnswerEntity.leftItemId}</dd>
          <dt>
            <span id="rightItemId">
              <Translate contentKey="skillVerifyApp.bindAnswer.rightItemId">Right Item Id</Translate>
            </span>
          </dt>
          <dd>{bindAnswerEntity.rightItemId}</dd>
          <dt>
            <span id="isCorrect">
              <Translate contentKey="skillVerifyApp.bindAnswer.isCorrect">Is Correct</Translate>
            </span>
          </dt>
          <dd>{bindAnswerEntity.isCorrect ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="skillVerifyApp.bindAnswer.candidateAnswer">Candidate Answer</Translate>
          </dt>
          <dd>{bindAnswerEntity.candidateAnswer ? bindAnswerEntity.candidateAnswer.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/bind-answer" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/bind-answer/${bindAnswerEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BindAnswerDetail;
