import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './question.reducer';

export const QuestionDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const questionEntity = useAppSelector(state => state.question.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="questionDetailsHeading">
          <Translate contentKey="skillVerifyApp.question.detail.title">Question</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{questionEntity.id}</dd>
          <dt>
            <span id="content">
              <Translate contentKey="skillVerifyApp.question.content">Content</Translate>
            </span>
          </dt>
          <dd>{questionEntity.content}</dd>
          <dt>
            <span id="explanation">
              <Translate contentKey="skillVerifyApp.question.explanation">Explanation</Translate>
            </span>
          </dt>
          <dd>{questionEntity.explanation}</dd>
          <dt>
            <span id="points">
              <Translate contentKey="skillVerifyApp.question.points">Points</Translate>
            </span>
          </dt>
          <dd>{questionEntity.points}</dd>
          <dt>
            <span id="orderIndex">
              <Translate contentKey="skillVerifyApp.question.orderIndex">Order Index</Translate>
            </span>
          </dt>
          <dd>{questionEntity.orderIndex}</dd>
          <dt>
            <Translate contentKey="skillVerifyApp.question.exercise">Exercise</Translate>
          </dt>
          <dd>{questionEntity.exercise ? questionEntity.exercise.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/question" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/question/${questionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default QuestionDetail;
