import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './bind-list.reducer';

export const BindListDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const bindListEntity = useAppSelector(state => state.bindList.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bindListDetailsHeading">
          <Translate contentKey="skillVerifyApp.bindList.detail.title">BindList</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{bindListEntity.id}</dd>
          <dt>
            <span id="leftItem">
              <Translate contentKey="skillVerifyApp.bindList.leftItem">Left Item</Translate>
            </span>
          </dt>
          <dd>{bindListEntity.leftItem}</dd>
          <dt>
            <span id="rightItem">
              <Translate contentKey="skillVerifyApp.bindList.rightItem">Right Item</Translate>
            </span>
          </dt>
          <dd>{bindListEntity.rightItem}</dd>
          <dt>
            <span id="orderIndex">
              <Translate contentKey="skillVerifyApp.bindList.orderIndex">Order Index</Translate>
            </span>
          </dt>
          <dd>{bindListEntity.orderIndex}</dd>
          <dt>
            <Translate contentKey="skillVerifyApp.bindList.question">Question</Translate>
          </dt>
          <dd>{bindListEntity.question ? bindListEntity.question.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/bind-list" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/bind-list/${bindListEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BindListDetail;
