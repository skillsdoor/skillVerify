import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './blank-space.reducer';

export const BlankSpaceDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const blankSpaceEntity = useAppSelector(state => state.blankSpace.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="blankSpaceDetailsHeading">
          <Translate contentKey="skillVerifyApp.blankSpace.detail.title">BlankSpace</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{blankSpaceEntity.id}</dd>
          <dt>
            <span id="position">
              <Translate contentKey="skillVerifyApp.blankSpace.position">Position</Translate>
            </span>
          </dt>
          <dd>{blankSpaceEntity.position}</dd>
          <dt>
            <span id="correctAnswer">
              <Translate contentKey="skillVerifyApp.blankSpace.correctAnswer">Correct Answer</Translate>
            </span>
          </dt>
          <dd>{blankSpaceEntity.correctAnswer}</dd>
          <dt>
            <span id="caseSensitive">
              <Translate contentKey="skillVerifyApp.blankSpace.caseSensitive">Case Sensitive</Translate>
            </span>
          </dt>
          <dd>{blankSpaceEntity.caseSensitive ? 'true' : 'false'}</dd>
          <dt>
            <span id="orderIndex">
              <Translate contentKey="skillVerifyApp.blankSpace.orderIndex">Order Index</Translate>
            </span>
          </dt>
          <dd>{blankSpaceEntity.orderIndex}</dd>
          <dt>
            <Translate contentKey="skillVerifyApp.blankSpace.exercise">Exercise</Translate>
          </dt>
          <dd>{blankSpaceEntity.exercise ? blankSpaceEntity.exercise.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/blank-space" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/blank-space/${blankSpaceEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BlankSpaceDetail;
