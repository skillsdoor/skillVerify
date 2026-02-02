import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, FormText, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getCandidateAnswers } from 'app/entities/candidate-answer/candidate-answer.reducer';
import { getEntities as getQcmOptions } from 'app/entities/qcm-option/qcm-option.reducer';
import { createEntity, getEntity, reset, updateEntity } from './qcm-answer.reducer';

export const QcmAnswerUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const candidateAnswers = useAppSelector(state => state.candidateAnswer.entities);
  const qcmOptions = useAppSelector(state => state.qcmOption.entities);
  const qcmAnswerEntity = useAppSelector(state => state.qcmAnswer.entity);
  const loading = useAppSelector(state => state.qcmAnswer.loading);
  const updating = useAppSelector(state => state.qcmAnswer.updating);
  const updateSuccess = useAppSelector(state => state.qcmAnswer.updateSuccess);

  const handleClose = () => {
    navigate(`/qcm-answer${location.search}`);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getCandidateAnswers({}));
    dispatch(getQcmOptions({}));
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

    const entity = {
      ...qcmAnswerEntity,
      ...values,
      candidateAnswer: candidateAnswers.find(it => it.id.toString() === values.candidateAnswer?.toString()),
      qcmOption: qcmOptions.find(it => it.id.toString() === values.qcmOption?.toString()),
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
          ...qcmAnswerEntity,
          candidateAnswer: qcmAnswerEntity?.candidateAnswer?.id,
          qcmOption: qcmAnswerEntity?.qcmOption?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="skillVerifyApp.qcmAnswer.home.createOrEditLabel" data-cy="QcmAnswerCreateUpdateHeading">
            <Translate contentKey="skillVerifyApp.qcmAnswer.home.createOrEditLabel">Create or edit a QcmAnswer</Translate>
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
                  id="qcm-answer-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('skillVerifyApp.qcmAnswer.isCorrect')}
                id="qcm-answer-isCorrect"
                name="isCorrect"
                data-cy="isCorrect"
                check
                type="checkbox"
              />
              <ValidatedField
                id="qcm-answer-candidateAnswer"
                name="candidateAnswer"
                data-cy="candidateAnswer"
                label={translate('skillVerifyApp.qcmAnswer.candidateAnswer')}
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
              <ValidatedField
                id="qcm-answer-qcmOption"
                name="qcmOption"
                data-cy="qcmOption"
                label={translate('skillVerifyApp.qcmAnswer.qcmOption')}
                type="select"
                required
              >
                <option value="" key="0" />
                {qcmOptions
                  ? qcmOptions.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/qcm-answer" replace color="info">
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

export default QcmAnswerUpdate;
