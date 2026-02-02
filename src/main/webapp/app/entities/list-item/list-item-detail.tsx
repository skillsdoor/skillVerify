import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './list-item.reducer';

export const ListItemDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const listItemEntity = useAppSelector(state => state.listItem.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="listItemDetailsHeading">
          <Translate contentKey="skillVerifyApp.listItem.detail.title">ListItem</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{listItemEntity.id}</dd>
          <dt>
            <span id="content">
              <Translate contentKey="skillVerifyApp.listItem.content">Content</Translate>
            </span>
          </dt>
          <dd>{listItemEntity.content}</dd>
          <dt>
            <span id="matchingContent">
              <Translate contentKey="skillVerifyApp.listItem.matchingContent">Matching Content</Translate>
            </span>
          </dt>
          <dd>{listItemEntity.matchingContent}</dd>
          <dt>
            <span id="isLeftSide">
              <Translate contentKey="skillVerifyApp.listItem.isLeftSide">Is Left Side</Translate>
            </span>
          </dt>
          <dd>{listItemEntity.isLeftSide ? 'true' : 'false'}</dd>
          <dt>
            <span id="orderIndex">
              <Translate contentKey="skillVerifyApp.listItem.orderIndex">Order Index</Translate>
            </span>
          </dt>
          <dd>{listItemEntity.orderIndex}</dd>
          <dt>
            <Translate contentKey="skillVerifyApp.listItem.exercise">Exercise</Translate>
          </dt>
          <dd>{listItemEntity.exercise ? listItemEntity.exercise.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/list-item" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/list-item/${listItemEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ListItemDetail;
