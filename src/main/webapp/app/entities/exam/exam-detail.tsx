import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './exam.reducer';

export const ExamDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const examEntity = useAppSelector(state => state.exam.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="examDetailsHeading">
          <Translate contentKey="skillVerifyApp.exam.detail.title">Exam</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{examEntity.id}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="skillVerifyApp.exam.title">Title</Translate>
            </span>
          </dt>
          <dd>{examEntity.title}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="skillVerifyApp.exam.description">Description</Translate>
            </span>
          </dt>
          <dd>{examEntity.description}</dd>
          <dt>
            <span id="duration">
              <Translate contentKey="skillVerifyApp.exam.duration">Duration</Translate>
            </span>
          </dt>
          <dd>{examEntity.duration}</dd>
          <dt>
            <span id="passingScore">
              <Translate contentKey="skillVerifyApp.exam.passingScore">Passing Score</Translate>
            </span>
          </dt>
          <dd>{examEntity.passingScore}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="skillVerifyApp.exam.status">Status</Translate>
            </span>
          </dt>
          <dd>{examEntity.status}</dd>
          <dt>
            <span id="testType">
              <Translate contentKey="skillVerifyApp.exam.testType">Test Type</Translate>
            </span>
          </dt>
          <dd>{examEntity.testType}</dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="skillVerifyApp.exam.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>{examEntity.createdAt ? <TextFormat value={examEntity.createdAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedAt">
              <Translate contentKey="skillVerifyApp.exam.updatedAt">Updated At</Translate>
            </span>
          </dt>
          <dd>{examEntity.updatedAt ? <TextFormat value={examEntity.updatedAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="publishedAt">
              <Translate contentKey="skillVerifyApp.exam.publishedAt">Published At</Translate>
            </span>
          </dt>
          <dd>{examEntity.publishedAt ? <TextFormat value={examEntity.publishedAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="skillVerifyApp.exam.recruiter">Recruiter</Translate>
          </dt>
          <dd>{examEntity.recruiter ? examEntity.recruiter.id : ''}</dd>
          <dt>
            <Translate contentKey="skillVerifyApp.exam.trainingProvider">Training Provider</Translate>
          </dt>
          <dd>{examEntity.trainingProvider ? examEntity.trainingProvider.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/exam" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/exam/${examEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ExamDetail;
