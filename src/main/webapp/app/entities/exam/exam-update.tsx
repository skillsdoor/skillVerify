import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, isNumber, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getRecruiters } from 'app/entities/recruiter/recruiter.reducer';
import { getEntities as getTrainingProviders } from 'app/entities/training-provider/training-provider.reducer';
import { ExamStatus } from 'app/shared/model/enumerations/exam-status.model';
import { TestType } from 'app/shared/model/enumerations/test-type.model';
import { createEntity, getEntity, reset, updateEntity } from './exam.reducer';

export const ExamUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const recruiters = useAppSelector(state => state.recruiter.entities);
  const trainingProviders = useAppSelector(state => state.trainingProvider.entities);
  const examEntity = useAppSelector(state => state.exam.entity);
  const loading = useAppSelector(state => state.exam.loading);
  const updating = useAppSelector(state => state.exam.updating);
  const updateSuccess = useAppSelector(state => state.exam.updateSuccess);
  const examStatusValues = Object.keys(ExamStatus);
  const testTypeValues = Object.keys(TestType);

  const handleClose = () => {
    navigate(`/exam${location.search}`);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getRecruiters({}));
    dispatch(getTrainingProviders({}));
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
    if (values.duration !== undefined && typeof values.duration !== 'number') {
      values.duration = Number(values.duration);
    }
    if (values.passingScore !== undefined && typeof values.passingScore !== 'number') {
      values.passingScore = Number(values.passingScore);
    }
    values.createdAt = convertDateTimeToServer(values.createdAt);
    values.updatedAt = convertDateTimeToServer(values.updatedAt);
    values.publishedAt = convertDateTimeToServer(values.publishedAt);

    const entity = {
      ...examEntity,
      ...values,
      recruiter: recruiters.find(it => it.id.toString() === values.recruiter?.toString()),
      trainingProvider: trainingProviders.find(it => it.id.toString() === values.trainingProvider?.toString()),
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
          createdAt: displayDefaultDateTime(),
          updatedAt: displayDefaultDateTime(),
          publishedAt: displayDefaultDateTime(),
        }
      : {
          status: 'DRAFT',
          testType: 'RECRUITMENT',
          ...examEntity,
          createdAt: convertDateTimeFromServer(examEntity.createdAt),
          updatedAt: convertDateTimeFromServer(examEntity.updatedAt),
          publishedAt: convertDateTimeFromServer(examEntity.publishedAt),
          recruiter: examEntity?.recruiter?.id,
          trainingProvider: examEntity?.trainingProvider?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="skillVerifyApp.exam.home.createOrEditLabel" data-cy="ExamCreateUpdateHeading">
            <Translate contentKey="skillVerifyApp.exam.home.createOrEditLabel">Create or edit a Exam</Translate>
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
                  id="exam-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('skillVerifyApp.exam.title')}
                id="exam-title"
                name="title"
                data-cy="title"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('skillVerifyApp.exam.description')}
                id="exam-description"
                name="description"
                data-cy="description"
                type="textarea"
              />
              <ValidatedField
                label={translate('skillVerifyApp.exam.duration')}
                id="exam-duration"
                name="duration"
                data-cy="duration"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('skillVerifyApp.exam.passingScore')}
                id="exam-passingScore"
                name="passingScore"
                data-cy="passingScore"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField label={translate('skillVerifyApp.exam.status')} id="exam-status" name="status" data-cy="status" type="select">
                {examStatusValues.map(examStatus => (
                  <option value={examStatus} key={examStatus}>
                    {translate(`skillVerifyApp.ExamStatus.${examStatus}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('skillVerifyApp.exam.testType')}
                id="exam-testType"
                name="testType"
                data-cy="testType"
                type="select"
              >
                {testTypeValues.map(testType => (
                  <option value={testType} key={testType}>
                    {translate(`skillVerifyApp.TestType.${testType}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('skillVerifyApp.exam.createdAt')}
                id="exam-createdAt"
                name="createdAt"
                data-cy="createdAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('skillVerifyApp.exam.updatedAt')}
                id="exam-updatedAt"
                name="updatedAt"
                data-cy="updatedAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('skillVerifyApp.exam.publishedAt')}
                id="exam-publishedAt"
                name="publishedAt"
                data-cy="publishedAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="exam-recruiter"
                name="recruiter"
                data-cy="recruiter"
                label={translate('skillVerifyApp.exam.recruiter')}
                type="select"
              >
                <option value="" key="0" />
                {recruiters
                  ? recruiters.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="exam-trainingProvider"
                name="trainingProvider"
                data-cy="trainingProvider"
                label={translate('skillVerifyApp.exam.trainingProvider')}
                type="select"
              >
                <option value="" key="0" />
                {trainingProviders
                  ? trainingProviders.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/exam" replace color="info">
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

export default ExamUpdate;
