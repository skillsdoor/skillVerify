import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, FormText, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, isNumber, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getExercises } from 'app/entities/exercise/exercise.reducer';
import { createEntity, getEntity, reset, updateEntity } from './blank-space.reducer';

export const BlankSpaceUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const exercises = useAppSelector(state => state.exercise.entities);
  const blankSpaceEntity = useAppSelector(state => state.blankSpace.entity);
  const loading = useAppSelector(state => state.blankSpace.loading);
  const updating = useAppSelector(state => state.blankSpace.updating);
  const updateSuccess = useAppSelector(state => state.blankSpace.updateSuccess);

  const handleClose = () => {
    navigate(`/blank-space${location.search}`);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getExercises({}));
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
    if (values.position !== undefined && typeof values.position !== 'number') {
      values.position = Number(values.position);
    }
    if (values.orderIndex !== undefined && typeof values.orderIndex !== 'number') {
      values.orderIndex = Number(values.orderIndex);
    }

    const entity = {
      ...blankSpaceEntity,
      ...values,
      exercise: exercises.find(it => it.id.toString() === values.exercise?.toString()),
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
          ...blankSpaceEntity,
          exercise: blankSpaceEntity?.exercise?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="skillVerifyApp.blankSpace.home.createOrEditLabel" data-cy="BlankSpaceCreateUpdateHeading">
            <Translate contentKey="skillVerifyApp.blankSpace.home.createOrEditLabel">Create or edit a BlankSpace</Translate>
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
                  id="blank-space-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('skillVerifyApp.blankSpace.position')}
                id="blank-space-position"
                name="position"
                data-cy="position"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('skillVerifyApp.blankSpace.correctAnswer')}
                id="blank-space-correctAnswer"
                name="correctAnswer"
                data-cy="correctAnswer"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('skillVerifyApp.blankSpace.caseSensitive')}
                id="blank-space-caseSensitive"
                name="caseSensitive"
                data-cy="caseSensitive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('skillVerifyApp.blankSpace.orderIndex')}
                id="blank-space-orderIndex"
                name="orderIndex"
                data-cy="orderIndex"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                id="blank-space-exercise"
                name="exercise"
                data-cy="exercise"
                label={translate('skillVerifyApp.blankSpace.exercise')}
                type="select"
                required
              >
                <option value="" key="0" />
                {exercises
                  ? exercises.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/blank-space" replace color="info">
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

export default BlankSpaceUpdate;
