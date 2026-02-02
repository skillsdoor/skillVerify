import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, FormText, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getExamSessions } from 'app/entities/exam-session/exam-session.reducer';
import { getEntities as getQuestions } from 'app/entities/question/question.reducer';
import { createEntity, getEntity, reset, updateEntity } from './candidate-answer.reducer';

export const CandidateAnswerUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const examSessions = useAppSelector(state => state.examSession.entities);
  const questions = useAppSelector(state => state.question.entities);
  const candidateAnswerEntity = useAppSelector(state => state.candidateAnswer.entity);
  const loading = useAppSelector(state => state.candidateAnswer.loading);
  const updating = useAppSelector(state => state.candidateAnswer.updating);
  const updateSuccess = useAppSelector(state => state.candidateAnswer.updateSuccess);

  const handleClose = () => {
    navigate(`/candidate-answer${location.search}`);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getExamSessions({}));
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
    if (values.selectedOptionId !== undefined && typeof values.selectedOptionId !== 'number') {
      values.selectedOptionId = Number(values.selectedOptionId);
    }
    if (values.pointsAwarded !== undefined && typeof values.pointsAwarded !== 'number') {
      values.pointsAwarded = Number(values.pointsAwarded);
    }
    values.answeredAt = convertDateTimeToServer(values.answeredAt);

    const entity = {
      ...candidateAnswerEntity,
      ...values,
      examSession: examSessions.find(it => it.id.toString() === values.examSession?.toString()),
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
          answeredAt: displayDefaultDateTime(),
        }
      : {
          ...candidateAnswerEntity,
          answeredAt: convertDateTimeFromServer(candidateAnswerEntity.answeredAt),
          examSession: candidateAnswerEntity?.examSession?.id,
          question: candidateAnswerEntity?.question?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="skillVerifyApp.candidateAnswer.home.createOrEditLabel" data-cy="CandidateAnswerCreateUpdateHeading">
            <Translate contentKey="skillVerifyApp.candidateAnswer.home.createOrEditLabel">Create or edit a CandidateAnswer</Translate>
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
                  id="candidate-answer-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('skillVerifyApp.candidateAnswer.answerText')}
                id="candidate-answer-answerText"
                name="answerText"
                data-cy="answerText"
                type="textarea"
              />
              <ValidatedField
                label={translate('skillVerifyApp.candidateAnswer.selectedOptionId')}
                id="candidate-answer-selectedOptionId"
                name="selectedOptionId"
                data-cy="selectedOptionId"
                type="text"
              />
              <ValidatedField
                label={translate('skillVerifyApp.candidateAnswer.isCorrect')}
                id="candidate-answer-isCorrect"
                name="isCorrect"
                data-cy="isCorrect"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('skillVerifyApp.candidateAnswer.pointsAwarded')}
                id="candidate-answer-pointsAwarded"
                name="pointsAwarded"
                data-cy="pointsAwarded"
                type="text"
              />
              <ValidatedField
                label={translate('skillVerifyApp.candidateAnswer.answeredAt')}
                id="candidate-answer-answeredAt"
                name="answeredAt"
                data-cy="answeredAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="candidate-answer-examSession"
                name="examSession"
                data-cy="examSession"
                label={translate('skillVerifyApp.candidateAnswer.examSession')}
                type="select"
                required
              >
                <option value="" key="0" />
                {examSessions
                  ? examSessions.map(otherEntity => (
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
                id="candidate-answer-question"
                name="question"
                data-cy="question"
                label={translate('skillVerifyApp.candidateAnswer.question')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/candidate-answer" replace color="info">
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

export default CandidateAnswerUpdate;
