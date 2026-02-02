import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './answer-option.reducer';

export const AnswerOptionDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const answerOptionEntity = useAppSelector(state => state.answerOption.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="answerOptionDetailsHeading">
          <Translate contentKey="skillVerifyApp.answerOption.detail.title">AnswerOption</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{answerOptionEntity.id}</dd>
          <dt>
            <span id="content">
              <Translate contentKey="skillVerifyApp.answerOption.content">Content</Translate>
            </span>
          </dt>
          <dd>{answerOptionEntity.content}</dd>
          <dt>
            <span id="isCorrect">
              <Translate contentKey="skillVerifyApp.answerOption.isCorrect">Is Correct</Translate>
            </span>
          </dt>
          <dd>{answerOptionEntity.isCorrect ? 'true' : 'false'}</dd>
          <dt>
            <span id="orderIndex">
              <Translate contentKey="skillVerifyApp.answerOption.orderIndex">Order Index</Translate>
            </span>
          </dt>
          <dd>{answerOptionEntity.orderIndex}</dd>
          <dt>
            <Translate contentKey="skillVerifyApp.answerOption.question">Question</Translate>
          </dt>
          <dd>{answerOptionEntity.question ? answerOptionEntity.question.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/answer-option" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/answer-option/${answerOptionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AnswerOptionDetail;
