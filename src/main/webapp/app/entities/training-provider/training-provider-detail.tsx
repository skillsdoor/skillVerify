import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './training-provider.reducer';

export const TrainingProviderDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const trainingProviderEntity = useAppSelector(state => state.trainingProvider.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="trainingProviderDetailsHeading">
          <Translate contentKey="skillVerifyApp.trainingProvider.detail.title">TrainingProvider</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{trainingProviderEntity.id}</dd>
          <dt>
            <span id="firstName">
              <Translate contentKey="skillVerifyApp.trainingProvider.firstName">First Name</Translate>
            </span>
          </dt>
          <dd>{trainingProviderEntity.firstName}</dd>
          <dt>
            <span id="lastName">
              <Translate contentKey="skillVerifyApp.trainingProvider.lastName">Last Name</Translate>
            </span>
          </dt>
          <dd>{trainingProviderEntity.lastName}</dd>
          <dt>
            <span id="phone">
              <Translate contentKey="skillVerifyApp.trainingProvider.phone">Phone</Translate>
            </span>
          </dt>
          <dd>{trainingProviderEntity.phone}</dd>
          <dt>
            <span id="bio">
              <Translate contentKey="skillVerifyApp.trainingProvider.bio">Bio</Translate>
            </span>
          </dt>
          <dd>{trainingProviderEntity.bio}</dd>
          <dt>
            <span id="avatarUrl">
              <Translate contentKey="skillVerifyApp.trainingProvider.avatarUrl">Avatar Url</Translate>
            </span>
          </dt>
          <dd>{trainingProviderEntity.avatarUrl}</dd>
          <dt>
            <span id="organizationName">
              <Translate contentKey="skillVerifyApp.trainingProvider.organizationName">Organization Name</Translate>
            </span>
          </dt>
          <dd>{trainingProviderEntity.organizationName}</dd>
          <dt>
            <span id="website">
              <Translate contentKey="skillVerifyApp.trainingProvider.website">Website</Translate>
            </span>
          </dt>
          <dd>{trainingProviderEntity.website}</dd>
          <dt>
            <span id="accreditation">
              <Translate contentKey="skillVerifyApp.trainingProvider.accreditation">Accreditation</Translate>
            </span>
          </dt>
          <dd>{trainingProviderEntity.accreditation}</dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="skillVerifyApp.trainingProvider.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>
            {trainingProviderEntity.createdAt ? (
              <TextFormat value={trainingProviderEntity.createdAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedAt">
              <Translate contentKey="skillVerifyApp.trainingProvider.updatedAt">Updated At</Translate>
            </span>
          </dt>
          <dd>
            {trainingProviderEntity.updatedAt ? (
              <TextFormat value={trainingProviderEntity.updatedAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="skillVerifyApp.trainingProvider.user">User</Translate>
          </dt>
          <dd>{trainingProviderEntity.user ? trainingProviderEntity.user.login : ''}</dd>
        </dl>
        <Button tag={Link} to="/training-provider" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/training-provider/${trainingProviderEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TrainingProviderDetail;
