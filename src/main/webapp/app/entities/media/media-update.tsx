import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getExercises } from 'app/entities/exercise/exercise.reducer';
import { getEntities as getQuestions } from 'app/entities/question/question.reducer';
import { MediaType } from 'app/shared/model/enumerations/media-type.model';
import { createEntity, getEntity, reset, updateEntity } from './media.reducer';

export const MediaUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const exercises = useAppSelector(state => state.exercise.entities);
  const questions = useAppSelector(state => state.question.entities);
  const mediaEntity = useAppSelector(state => state.media.entity);
  const loading = useAppSelector(state => state.media.loading);
  const updating = useAppSelector(state => state.media.updating);
  const updateSuccess = useAppSelector(state => state.media.updateSuccess);
  const mediaTypeValues = Object.keys(MediaType);

  const handleClose = () => {
    navigate(`/media${location.search}`);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getExercises({}));
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
    if (values.fileSize !== undefined && typeof values.fileSize !== 'number') {
      values.fileSize = Number(values.fileSize);
    }
    values.uploadedAt = convertDateTimeToServer(values.uploadedAt);

    const entity = {
      ...mediaEntity,
      ...values,
      exercise: exercises.find(it => it.id.toString() === values.exercise?.toString()),
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
      ? {
          uploadedAt: displayDefaultDateTime(),
        }
      : {
          type: 'IMAGE',
          ...mediaEntity,
          uploadedAt: convertDateTimeFromServer(mediaEntity.uploadedAt),
          exercise: mediaEntity?.exercise?.id,
          question: mediaEntity?.question?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="skillVerifyApp.media.home.createOrEditLabel" data-cy="MediaCreateUpdateHeading">
            <Translate contentKey="skillVerifyApp.media.home.createOrEditLabel">Create or edit a Media</Translate>
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
                  id="media-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('skillVerifyApp.media.type')} id="media-type" name="type" data-cy="type" type="select">
                {mediaTypeValues.map(mediaType => (
                  <option value={mediaType} key={mediaType}>
                    {translate(`skillVerifyApp.MediaType.${mediaType}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('skillVerifyApp.media.s3Key')}
                id="media-s3Key"
                name="s3Key"
                data-cy="s3Key"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('skillVerifyApp.media.s3Url')}
                id="media-s3Url"
                name="s3Url"
                data-cy="s3Url"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('skillVerifyApp.media.fileName')}
                id="media-fileName"
                name="fileName"
                data-cy="fileName"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('skillVerifyApp.media.fileSize')}
                id="media-fileSize"
                name="fileSize"
                data-cy="fileSize"
                type="text"
              />
              <ValidatedField
                label={translate('skillVerifyApp.media.mimeType')}
                id="media-mimeType"
                name="mimeType"
                data-cy="mimeType"
                type="text"
              />
              <ValidatedField
                label={translate('skillVerifyApp.media.uploadedAt')}
                id="media-uploadedAt"
                name="uploadedAt"
                data-cy="uploadedAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="media-exercise"
                name="exercise"
                data-cy="exercise"
                label={translate('skillVerifyApp.media.exercise')}
                type="select"
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
              <ValidatedField
                id="media-question"
                name="question"
                data-cy="question"
                label={translate('skillVerifyApp.media.question')}
                type="select"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/media" replace color="info">
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

export default MediaUpdate;
