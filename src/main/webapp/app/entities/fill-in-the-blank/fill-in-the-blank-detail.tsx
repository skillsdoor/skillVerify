import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './fill-in-the-blank.reducer';

export const FillInTheBlankDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const fillInTheBlankEntity = useAppSelector(state => state.fillInTheBlank.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="fillInTheBlankDetailsHeading">
          <Translate contentKey="skillVerifyApp.fillInTheBlank.detail.title">FillInTheBlank</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{fillInTheBlankEntity.id}</dd>
          <dt>
            <span id="textWithBlanks">
              <Translate contentKey="skillVerifyApp.fillInTheBlank.textWithBlanks">Text With Blanks</Translate>
            </span>
          </dt>
          <dd>{fillInTheBlankEntity.textWithBlanks}</dd>
          <dt>
            <span id="blankIndex">
              <Translate contentKey="skillVerifyApp.fillInTheBlank.blankIndex">Blank Index</Translate>
            </span>
          </dt>
          <dd>{fillInTheBlankEntity.blankIndex}</dd>
          <dt>
            <span id="correctAnswer">
              <Translate contentKey="skillVerifyApp.fillInTheBlank.correctAnswer">Correct Answer</Translate>
            </span>
          </dt>
          <dd>{fillInTheBlankEntity.correctAnswer}</dd>
          <dt>
            <span id="caseSensitive">
              <Translate contentKey="skillVerifyApp.fillInTheBlank.caseSensitive">Case Sensitive</Translate>
            </span>
          </dt>
          <dd>{fillInTheBlankEntity.caseSensitive ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="skillVerifyApp.fillInTheBlank.question">Question</Translate>
          </dt>
          <dd>{fillInTheBlankEntity.question ? fillInTheBlankEntity.question.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/fill-in-the-blank" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/fill-in-the-blank/${fillInTheBlankEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default FillInTheBlankDetail;
