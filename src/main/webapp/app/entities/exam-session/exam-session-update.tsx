import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, FormText, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getCandidates } from 'app/entities/candidate/candidate.reducer';
import { getEntities as getExams } from 'app/entities/exam/exam.reducer';
import { SubmissionStatus } from 'app/shared/model/enumerations/submission-status.model';
import { createEntity, getEntity, reset, updateEntity } from './exam-session.reducer';

export const ExamSessionUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const candidates = useAppSelector(state => state.candidate.entities);
  const exams = useAppSelector(state => state.exam.entities);
  const examSessionEntity = useAppSelector(state => state.examSession.entity);
  const loading = useAppSelector(state => state.examSession.loading);
  const updating = useAppSelector(state => state.examSession.updating);
  const updateSuccess = useAppSelector(state => state.examSession.updateSuccess);
  const submissionStatusValues = Object.keys(SubmissionStatus);

  const handleClose = () => {
    navigate(`/exam-session${location.search}`);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getCandidates({}));
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
    values.startedAt = convertDateTimeToServer(values.startedAt);
    values.submittedAt = convertDateTimeToServer(values.submittedAt);
    if (values.score !== undefined && typeof values.score !== 'number') {
      values.score = Number(values.score);
    }
    if (values.totalPoints !== undefined && typeof values.totalPoints !== 'number') {
      values.totalPoints = Number(values.totalPoints);
    }
    if (values.timeSpent !== undefined && typeof values.timeSpent !== 'number') {
      values.timeSpent = Number(values.timeSpent);
    }

    const entity = {
      ...examSessionEntity,
      ...values,
      candidate: candidates.find(it => it.id.toString() === values.candidate?.toString()),
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
      ? {
          startedAt: displayDefaultDateTime(),
          submittedAt: displayDefaultDateTime(),
        }
      : {
          status: 'NOT_STARTED',
          ...examSessionEntity,
          startedAt: convertDateTimeFromServer(examSessionEntity.startedAt),
          submittedAt: convertDateTimeFromServer(examSessionEntity.submittedAt),
          candidate: examSessionEntity?.candidate?.id,
          exam: examSessionEntity?.exam?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="skillVerifyApp.examSession.home.createOrEditLabel" data-cy="ExamSessionCreateUpdateHeading">
            <Translate contentKey="skillVerifyApp.examSession.home.createOrEditLabel">Create or edit a ExamSession</Translate>
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
                  id="exam-session-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('skillVerifyApp.examSession.startedAt')}
                id="exam-session-startedAt"
                name="startedAt"
                data-cy="startedAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('skillVerifyApp.examSession.submittedAt')}
                id="exam-session-submittedAt"
                name="submittedAt"
                data-cy="submittedAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('skillVerifyApp.examSession.status')}
                id="exam-session-status"
                name="status"
                data-cy="status"
                type="select"
              >
                {submissionStatusValues.map(submissionStatus => (
                  <option value={submissionStatus} key={submissionStatus}>
                    {translate(`skillVerifyApp.SubmissionStatus.${submissionStatus}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('skillVerifyApp.examSession.score')}
                id="exam-session-score"
                name="score"
                data-cy="score"
                type="text"
              />
              <ValidatedField
                label={translate('skillVerifyApp.examSession.totalPoints')}
                id="exam-session-totalPoints"
                name="totalPoints"
                data-cy="totalPoints"
                type="text"
              />
              <ValidatedField
                label={translate('skillVerifyApp.examSession.timeSpent')}
                id="exam-session-timeSpent"
                name="timeSpent"
                data-cy="timeSpent"
                type="text"
              />
              <ValidatedField
                id="exam-session-candidate"
                name="candidate"
                data-cy="candidate"
                label={translate('skillVerifyApp.examSession.candidate')}
                type="select"
                required
              >
                <option value="" key="0" />
                {candidates
                  ? candidates.map(otherEntity => (
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
                id="exam-session-exam"
                name="exam"
                data-cy="exam"
                label={translate('skillVerifyApp.examSession.exam')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/exam-session" replace color="info">
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

export default ExamSessionUpdate;
