import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './recruiter.reducer';

export const RecruiterDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const recruiterEntity = useAppSelector(state => state.recruiter.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="recruiterDetailsHeading">
          <Translate contentKey="skillVerifyApp.recruiter.detail.title">Recruiter</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{recruiterEntity.id}</dd>
          <dt>
            <span id="firstName">
              <Translate contentKey="skillVerifyApp.recruiter.firstName">First Name</Translate>
            </span>
          </dt>
          <dd>{recruiterEntity.firstName}</dd>
          <dt>
            <span id="lastName">
              <Translate contentKey="skillVerifyApp.recruiter.lastName">Last Name</Translate>
            </span>
          </dt>
          <dd>{recruiterEntity.lastName}</dd>
          <dt>
            <span id="phone">
              <Translate contentKey="skillVerifyApp.recruiter.phone">Phone</Translate>
            </span>
          </dt>
          <dd>{recruiterEntity.phone}</dd>
          <dt>
            <span id="bio">
              <Translate contentKey="skillVerifyApp.recruiter.bio">Bio</Translate>
            </span>
          </dt>
          <dd>{recruiterEntity.bio}</dd>
          <dt>
            <span id="avatarUrl">
              <Translate contentKey="skillVerifyApp.recruiter.avatarUrl">Avatar Url</Translate>
            </span>
          </dt>
          <dd>{recruiterEntity.avatarUrl}</dd>
          <dt>
            <span id="company">
              <Translate contentKey="skillVerifyApp.recruiter.company">Company</Translate>
            </span>
          </dt>
          <dd>{recruiterEntity.company}</dd>
          <dt>
            <span id="position">
              <Translate contentKey="skillVerifyApp.recruiter.position">Position</Translate>
            </span>
          </dt>
          <dd>{recruiterEntity.position}</dd>
          <dt>
            <span id="website">
              <Translate contentKey="skillVerifyApp.recruiter.website">Website</Translate>
            </span>
          </dt>
          <dd>{recruiterEntity.website}</dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="skillVerifyApp.recruiter.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>
            {recruiterEntity.createdAt ? <TextFormat value={recruiterEntity.createdAt} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedAt">
              <Translate contentKey="skillVerifyApp.recruiter.updatedAt">Updated At</Translate>
            </span>
          </dt>
          <dd>
            {recruiterEntity.updatedAt ? <TextFormat value={recruiterEntity.updatedAt} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="skillVerifyApp.recruiter.user">User</Translate>
          </dt>
          <dd>{recruiterEntity.user ? recruiterEntity.user.login : ''}</dd>
        </dl>
        <Button tag={Link} to="/recruiter" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/recruiter/${recruiterEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default RecruiterDetail;
