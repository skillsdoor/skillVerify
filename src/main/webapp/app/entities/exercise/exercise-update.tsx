import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, FormText, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, isNumber, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getExams } from 'app/entities/exam/exam.reducer';
import { ExerciseType } from 'app/shared/model/enumerations/exercise-type.model';
import { createEntity, getEntity, reset, updateEntity } from './exercise.reducer';

export const ExerciseUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const exams = useAppSelector(state => state.exam.entities);
  const exerciseEntity = useAppSelector(state => state.exercise.entity);
  const loading = useAppSelector(state => state.exercise.loading);
  const updating = useAppSelector(state => state.exercise.updating);
  const updateSuccess = useAppSelector(state => state.exercise.updateSuccess);
  const exerciseTypeValues = Object.keys(ExerciseType);

  const handleClose = () => {
    navigate(`/exercise${location.search}`);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getExams({}));
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
    if (values.points !== undefined && typeof values.points !== 'number') {
      values.points = Number(values.points);
    }
    if (values.orderIndex !== undefined && typeof values.orderIndex !== 'number') {
      values.orderIndex = Number(values.orderIndex);
    }

    const entity = {
      ...exerciseEntity,
      ...values,
      exam: exams.find(it => it.id.toString() === values.exam?.toString()),
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
          type: 'QCM',
          ...exerciseEntity,
          exam: exerciseEntity?.exam?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="skillVerifyApp.exercise.home.createOrEditLabel" data-cy="ExerciseCreateUpdateHeading">
            <Translate contentKey="skillVerifyApp.exercise.home.createOrEditLabel">Create or edit a Exercise</Translate>
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
                  id="exercise-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('skillVerifyApp.exercise.type')} id="exercise-type" name="type" data-cy="type" type="select">
                {exerciseTypeValues.map(exerciseType => (
                  <option value={exerciseType} key={exerciseType}>
                    {translate(`skillVerifyApp.ExerciseType.${exerciseType}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('skillVerifyApp.exercise.title')}
                id="exercise-title"
                name="title"
                data-cy="title"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('skillVerifyApp.exercise.instruction')}
                id="exercise-instruction"
                name="instruction"
                data-cy="instruction"
                type="textarea"
              />
              <ValidatedField
                label={translate('skillVerifyApp.exercise.points')}
                id="exercise-points"
                name="points"
                data-cy="points"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('skillVerifyApp.exercise.orderIndex')}
                id="exercise-orderIndex"
                name="orderIndex"
                data-cy="orderIndex"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                id="exercise-exam"
                name="exam"
                data-cy="exam"
                label={translate('skillVerifyApp.exercise.exam')}
                type="select"
                required
              >
                <option value="" key="0" />
                {exams
                  ? exams.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/exercise" replace color="info">
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

export default ExerciseUpdate;
