import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, FormText, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, isNumber, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getQuestions } from 'app/entities/question/question.reducer';
import { createEntity, getEntity, reset, updateEntity } from './fill-in-the-blank.reducer';

export const FillInTheBlankUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const questions = useAppSelector(state => state.question.entities);
  const fillInTheBlankEntity = useAppSelector(state => state.fillInTheBlank.entity);
  const loading = useAppSelector(state => state.fillInTheBlank.loading);
  const updating = useAppSelector(state => state.fillInTheBlank.updating);
  const updateSuccess = useAppSelector(state => state.fillInTheBlank.updateSuccess);

  const handleClose = () => {
    navigate(`/fill-in-the-blank${location.search}`);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getQuestions({}));
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
    if (values.blankIndex !== undefined && typeof values.blankIndex !== 'number') {
      values.blankIndex = Number(values.blankIndex);
    }

    const entity = {
      ...fillInTheBlankEntity,
      ...values,
      question: questions.find(it => it.id.toString() === values.question?.toString()),
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
          ...fillInTheBlankEntity,
          question: fillInTheBlankEntity?.question?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="skillVerifyApp.fillInTheBlank.home.createOrEditLabel" data-cy="FillInTheBlankCreateUpdateHeading">
            <Translate contentKey="skillVerifyApp.fillInTheBlank.home.createOrEditLabel">Create or edit a FillInTheBlank</Translate>
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
                  id="fill-in-the-blank-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('skillVerifyApp.fillInTheBlank.textWithBlanks')}
                id="fill-in-the-blank-textWithBlanks"
                name="textWithBlanks"
                data-cy="textWithBlanks"
                type="textarea"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('skillVerifyApp.fillInTheBlank.blankIndex')}
                id="fill-in-the-blank-blankIndex"
                name="blankIndex"
                data-cy="blankIndex"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('skillVerifyApp.fillInTheBlank.correctAnswer')}
                id="fill-in-the-blank-correctAnswer"
                name="correctAnswer"
                data-cy="correctAnswer"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('skillVerifyApp.fillInTheBlank.caseSensitive')}
                id="fill-in-the-blank-caseSensitive"
                name="caseSensitive"
                data-cy="caseSensitive"
                check
                type="checkbox"
              />
              <ValidatedField
                id="fill-in-the-blank-question"
                name="question"
                data-cy="question"
                label={translate('skillVerifyApp.fillInTheBlank.question')}
                type="select"
                required
              >
                <option value="" key="0" />
                {questions
                  ? questions.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/fill-in-the-blank" replace color="info">
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

export default FillInTheBlankUpdate;
