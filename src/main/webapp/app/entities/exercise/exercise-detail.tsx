import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './exercise.reducer';

export const ExerciseDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const exerciseEntity = useAppSelector(state => state.exercise.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="exerciseDetailsHeading">
          <Translate contentKey="skillVerifyApp.exercise.detail.title">Exercise</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{exerciseEntity.id}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="skillVerifyApp.exercise.type">Type</Translate>
            </span>
          </dt>
          <dd>{exerciseEntity.type}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="skillVerifyApp.exercise.title">Title</Translate>
            </span>
          </dt>
          <dd>{exerciseEntity.title}</dd>
          <dt>
            <span id="instruction">
              <Translate contentKey="skillVerifyApp.exercise.instruction">Instruction</Translate>
            </span>
          </dt>
          <dd>{exerciseEntity.instruction}</dd>
          <dt>
            <span id="points">
              <Translate contentKey="skillVerifyApp.exercise.points">Points</Translate>
            </span>
          </dt>
          <dd>{exerciseEntity.points}</dd>
          <dt>
            <span id="orderIndex">
              <Translate contentKey="skillVerifyApp.exercise.orderIndex">Order Index</Translate>
            </span>
          </dt>
          <dd>{exerciseEntity.orderIndex}</dd>
          <dt>
            <Translate contentKey="skillVerifyApp.exercise.exam">Exam</Translate>
          </dt>
          <dd>{exerciseEntity.exam ? exerciseEntity.exam.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/exercise" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/exercise/${exerciseEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ExerciseDetail;
