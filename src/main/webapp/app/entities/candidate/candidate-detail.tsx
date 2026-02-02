import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './candidate.reducer';

export const CandidateDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const candidateEntity = useAppSelector(state => state.candidate.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="candidateDetailsHeading">
          <Translate contentKey="skillVerifyApp.candidate.detail.title">Candidate</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.id}</dd>
          <dt>
            <span id="firstName">
              <Translate contentKey="skillVerifyApp.candidate.firstName">First Name</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.firstName}</dd>
          <dt>
            <span id="lastName">
              <Translate contentKey="skillVerifyApp.candidate.lastName">Last Name</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.lastName}</dd>
          <dt>
            <span id="phone">
              <Translate contentKey="skillVerifyApp.candidate.phone">Phone</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.phone}</dd>
          <dt>
            <span id="bio">
              <Translate contentKey="skillVerifyApp.candidate.bio">Bio</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.bio}</dd>
          <dt>
            <span id="avatarUrl">
              <Translate contentKey="skillVerifyApp.candidate.avatarUrl">Avatar Url</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.avatarUrl}</dd>
          <dt>
            <span id="resumeUrl">
              <Translate contentKey="skillVerifyApp.candidate.resumeUrl">Resume Url</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.resumeUrl}</dd>
          <dt>
            <span id="linkedinUrl">
              <Translate contentKey="skillVerifyApp.candidate.linkedinUrl">Linkedin Url</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.linkedinUrl}</dd>
          <dt>
            <span id="githubUrl">
              <Translate contentKey="skillVerifyApp.candidate.githubUrl">Github Url</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.githubUrl}</dd>
          <dt>
            <span id="yearsOfExperience">
              <Translate contentKey="skillVerifyApp.candidate.yearsOfExperience">Years Of Experience</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.yearsOfExperience}</dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="skillVerifyApp.candidate.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>
            {candidateEntity.createdAt ? <TextFormat value={candidateEntity.createdAt} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedAt">
              <Translate contentKey="skillVerifyApp.candidate.updatedAt">Updated At</Translate>
            </span>
          </dt>
          <dd>
            {candidateEntity.updatedAt ? <TextFormat value={candidateEntity.updatedAt} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="skillVerifyApp.candidate.user">User</Translate>
          </dt>
          <dd>{candidateEntity.user ? candidateEntity.user.login : ''}</dd>
        </dl>
        <Button tag={Link} to="/candidate" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/candidate/${candidateEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CandidateDetail;
