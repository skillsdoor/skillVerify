import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, FormText, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getCandidateAnswers } from 'app/entities/candidate-answer/candidate-answer.reducer';
import { getEntities as getBlankSpaces } from 'app/entities/blank-space/blank-space.reducer';
import { createEntity, getEntity, reset, updateEntity } from './blank-answer.reducer';

export const BlankAnswerUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const candidateAnswers = useAppSelector(state => state.candidateAnswer.entities);
  const blankSpaces = useAppSelector(state => state.blankSpace.entities);
  const blankAnswerEntity = useAppSelector(state => state.blankAnswer.entity);
  const loading = useAppSelector(state => state.blankAnswer.loading);
  const updating = useAppSelector(state => state.blankAnswer.updating);
  const updateSuccess = useAppSelector(state => state.blankAnswer.updateSuccess);

  const handleClose = () => {
    navigate(`/blank-answer${location.search}`);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getCandidateAnswers({}));
    dispatch(getBlankSpaces({}));
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
      ...blankAnswerEntity,
      ...values,
      candidateAnswer: candidateAnswers.find(it => it.id.toString() === values.candidateAnswer?.toString()),
      blankSpace: blankSpaces.find(it => it.id.toString() === values.blankSpace?.toString()),
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
          ...blankAnswerEntity,
          candidateAnswer: blankAnswerEntity?.candidateAnswer?.id,
          blankSpace: blankAnswerEntity?.blankSpace?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="skillVerifyApp.blankAnswer.home.createOrEditLabel" data-cy="BlankAnswerCreateUpdateHeading">
            <Translate contentKey="skillVerifyApp.blankAnswer.home.createOrEditLabel">Create or edit a BlankAnswer</Translate>
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
                  id="blank-answer-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('skillVerifyApp.blankAnswer.answerText')}
                id="blank-answer-answerText"
                name="answerText"
                data-cy="answerText"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('skillVerifyApp.blankAnswer.isCorrect')}
                id="blank-answer-isCorrect"
                name="isCorrect"
                data-cy="isCorrect"
                check
                type="checkbox"
              />
              <ValidatedField
                id="blank-answer-candidateAnswer"
                name="candidateAnswer"
                data-cy="candidateAnswer"
                label={translate('skillVerifyApp.blankAnswer.candidateAnswer')}
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
                id="blank-answer-blankSpace"
                name="blankSpace"
                data-cy="blankSpace"
                label={translate('skillVerifyApp.blankAnswer.blankSpace')}
                type="select"
                required
              >
                <option value="" key="0" />
                {blankSpaces
                  ? blankSpaces.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/blank-answer" replace color="info">
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

export default BlankAnswerUpdate;
