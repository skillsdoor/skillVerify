import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './qcm-option.reducer';

export const QcmOptionDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const qcmOptionEntity = useAppSelector(state => state.qcmOption.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="qcmOptionDetailsHeading">
          <Translate contentKey="skillVerifyApp.qcmOption.detail.title">QcmOption</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{qcmOptionEntity.id}</dd>
          <dt>
            <span id="content">
              <Translate contentKey="skillVerifyApp.qcmOption.content">Content</Translate>
            </span>
          </dt>
          <dd>{qcmOptionEntity.content}</dd>
          <dt>
            <span id="isCorrect">
              <Translate contentKey="skillVerifyApp.qcmOption.isCorrect">Is Correct</Translate>
            </span>
          </dt>
          <dd>{qcmOptionEntity.isCorrect ? 'true' : 'false'}</dd>
          <dt>
            <span id="orderIndex">
              <Translate contentKey="skillVerifyApp.qcmOption.orderIndex">Order Index</Translate>
            </span>
          </dt>
          <dd>{qcmOptionEntity.orderIndex}</dd>
          <dt>
            <Translate contentKey="skillVerifyApp.qcmOption.exercise">Exercise</Translate>
          </dt>
          <dd>{qcmOptionEntity.exercise ? qcmOptionEntity.exercise.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/qcm-option" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/qcm-option/${qcmOptionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default QcmOptionDetail;
