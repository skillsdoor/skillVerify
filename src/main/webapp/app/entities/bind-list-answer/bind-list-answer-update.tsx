import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, FormText, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, isNumber, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getCandidateAnswers } from 'app/entities/candidate-answer/candidate-answer.reducer';
import { createEntity, getEntity, reset, updateEntity } from './bind-list-answer.reducer';

export const BindListAnswerUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const candidateAnswers = useAppSelector(state => state.candidateAnswer.entities);
  const bindListAnswerEntity = useAppSelector(state => state.bindListAnswer.entity);
  const loading = useAppSelector(state => state.bindListAnswer.loading);
  const updating = useAppSelector(state => state.bindListAnswer.updating);
  const updateSuccess = useAppSelector(state => state.bindListAnswer.updateSuccess);

  const handleClose = () => {
    navigate(`/bind-list-answer${location.search}`);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getCandidateAnswers({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }
    if (values.leftItemId !== undefined && typeof values.leftItemId !== 'number') {
      values.leftItemId = Number(values.leftItemId);
    }
    if (values.rightItemId !== undefined && typeof values.rightItemId !== 'number') {
      values.rightItemId = Number(values.rightItemId);
    }

    const entity = {
      ...bindListAnswerEntity,
      ...values,
      candidateAnswer: candidateAnswers.find(it => it.id.toString() === values.candidateAnswer?.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...bindListAnswerEntity,
          candidateAnswer: bindListAnswerEntity?.candidateAnswer?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="skillVerifyApp.bindListAnswer.home.createOrEditLabel" data-cy="BindListAnswerCreateUpdateHeading">
            <Translate contentKey="skillVerifyApp.bindListAnswer.home.createOrEditLabel">Create or edit a BindListAnswer</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="bind-list-answer-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('skillVerifyApp.bindListAnswer.leftItemId')}
                id="bind-list-answer-leftItemId"
                name="leftItemId"
                data-cy="leftItemId"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('skillVerifyApp.bindListAnswer.rightItemId')}
                id="bind-list-answer-rightItemId"
                name="rightItemId"
                data-cy="rightItemId"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('skillVerifyApp.bindListAnswer.isCorrect')}
                id="bind-list-answer-isCorrect"
                name="isCorrect"
                data-cy="isCorrect"
                check
                type="checkbox"
              />
              <ValidatedField
                id="bind-list-answer-candidateAnswer"
                name="candidateAnswer"
                data-cy="candidateAnswer"
                label={translate('skillVerifyApp.bindListAnswer.candidateAnswer')}
                type="select"
                required
              >
                <option value="" key="0" />
                {candidateAnswers
                  ? candidateAnswers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/bind-list-answer" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default BindListAnswerUpdate;
