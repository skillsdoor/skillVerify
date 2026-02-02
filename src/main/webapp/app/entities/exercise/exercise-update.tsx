import React, { useEffect, useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Card, CardBody, Col, FormText, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, isNumber, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { useForm } from 'react-hook-form';

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

  const [selectedType, setSelectedType] = useState<string>('QCM');
  const [formData, setFormData] = useState({
    title: '',
    instruction: '',
    content: '',
    explanation: '',
    points: 0,
    orderIndex: 0,
    exam: '',
  });
  const [qcmOptions, setQcmOptions] = useState([{ id: null, content: '', isCorrect: false, orderIndex: 0 }]);
  const [blankSpaces, setBlankSpaces] = useState([{ id: null, position: 0, correctAnswer: '', caseSensitive: false, orderIndex: 0 }]);
  const [listItems, setListItems] = useState([{ id: null, content: '', matchingContent: '', isLeftSide: true, orderIndex: 0 }]);

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
  }, [id]);

  useEffect(() => {
    if (!isNew && exerciseEntity?.id) {
      // Entity loaded, populate form
      setSelectedType(exerciseEntity.type);
      setFormData({
        title: exerciseEntity.title || '',
        instruction: exerciseEntity.instruction || '',
        content: exerciseEntity.content || '',
        explanation: exerciseEntity.explanation || '',
        points: exerciseEntity.points ?? 0,
        orderIndex: exerciseEntity.orderIndex ?? 0,
        exam: exerciseEntity.exam?.id?.toString() || '',
      });

      // Load existing data
      if (exerciseEntity.qcmOptions && exerciseEntity.qcmOptions.length > 0) {
        setQcmOptions(
          exerciseEntity.qcmOptions.map(opt => ({
            id: opt.id,
            content: opt.content,
            isCorrect: opt.isCorrect,
            orderIndex: opt.orderIndex,
          })),
        );
      }

      if (exerciseEntity.blankSpaces && exerciseEntity.blankSpaces.length > 0) {
        setBlankSpaces(
          exerciseEntity.blankSpaces.map(blank => ({
            id: blank.id,
            position: blank.position,
            correctAnswer: blank.correctAnswer,
            caseSensitive: blank.caseSensitive,
            orderIndex: blank.orderIndex,
          })),
        );
      }

      if (exerciseEntity.listItems && exerciseEntity.listItems.length > 0) {
        setListItems(
          exerciseEntity.listItems.map(item => ({
            id: item.id,
            content: item.content,
            matchingContent: item.matchingContent,
            isLeftSide: item.isLeftSide,
            orderIndex: item.orderIndex,
          })),
        );
      }
    }
  }, [exerciseEntity?.id]);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...exerciseEntity,
      id: exerciseEntity?.id,
      type: values.type,
      title: formData.title,
      instruction: formData.instruction,
      content: formData.content,
      explanation: formData.explanation,
      points: Number(formData.points),
      orderIndex: Number(formData.orderIndex),
      exam: exams.find(it => it.id.toString() === formData.exam),
      qcmOptions: values.type === 'QCM' || values.type === 'TRUE_FALSE' ? qcmOptions : undefined,
      blankSpaces: values.type === 'FILL_IN_THE_BLANK' ? blankSpaces : undefined,
      listItems: values.type === 'BIND_LISTS' ? listItems : undefined,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  if (!isNew && loading) {
    return (
      <div className="p-3">
        <p>Loading...</p>
      </div>
    );
  }

  const defaultValues = isNew
    ? { type: 'QCM' }
    : {
        id: exerciseEntity?.id,
        type: exerciseEntity?.type || 'QCM',
      };

  return (
    <div className="p-3">
      <Row className="justify-content-center">
        <Col md="10">
          <h2 id="skillVerifyApp.exercise.home.createOrEditLabel" data-cy="ExerciseCreateUpdateHeading">
            <Translate contentKey="skillVerifyApp.exercise.home.createOrEditLabel">Create or edit a Exercise</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="10">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues} onSubmit={saveEntity}>
              <Card className="mb-3">
                <CardBody>
                  <h5 className="mb-3">Basic Information</h5>
                  <Row>
                    {!isNew && (
                      <Col md="2">
                        <ValidatedField
                          name="id"
                          required
                          readOnly
                          id="exercise-id"
                          label={translate('global.field.id')}
                          validate={{ required: true }}
                        />
                      </Col>
                    )}
                    <Col md={isNew ? '6' : '5'}>
                      <ValidatedField
                        label={translate('skillVerifyApp.exercise.type')}
                        id="exercise-type"
                        name="type"
                        data-cy="type"
                        type="select"
                        onChange={e => setSelectedType(e.target.value)}
                      >
                        {exerciseTypeValues.map(exerciseType => (
                          <option value={exerciseType} key={exerciseType}>
                            {translate(`skillVerifyApp.ExerciseType.${exerciseType}`)}
                          </option>
                        ))}
                      </ValidatedField>
                    </Col>
                    <Col md={isNew ? '6' : '5'}>
                      <ValidatedField
                        id="exercise-exam"
                        name="exam"
                        data-cy="exam"
                        label={translate('skillVerifyApp.exercise.exam')}
                        type="select"
                        value={formData.exam}
                        onChange={e => setFormData({ ...formData, exam: e.target.value })}
                        required
                      >
                        <option value="" key="0" />
                        {exams?.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.title || otherEntity.id}
                          </option>
                        ))}
                      </ValidatedField>
                      <FormText>
                        <Translate contentKey="entity.validation.required">This field is required.</Translate>
                      </FormText>
                    </Col>
                  </Row>
                  <Row>
                    <Col md="12">
                      <ValidatedField
                        label={translate('skillVerifyApp.exercise.title')}
                        id="exercise-title"
                        name="title"
                        data-cy="title"
                        type="text"
                        value={formData.title}
                        onChange={e => setFormData({ ...formData, title: e.target.value })}
                        validate={{
                          required: { value: true, message: translate('entity.validation.required') },
                        }}
                      />
                    </Col>
                  </Row>
                  <Row>
                    <Col md="3">
                      <ValidatedField
                        label={translate('skillVerifyApp.exercise.points')}
                        id="exercise-points"
                        name="points"
                        data-cy="points"
                        type="number"
                        value={formData.points}
                        onChange={e => setFormData({ ...formData, points: Number(e.target.value) })}
                        validate={{
                          required: { value: true, message: translate('entity.validation.required') },
                          validate: v => isNumber(v) || translate('entity.validation.number'),
                        }}
                      />
                    </Col>
                    <Col md="3">
                      <ValidatedField
                        label={translate('skillVerifyApp.exercise.orderIndex')}
                        id="exercise-orderIndex"
                        name="orderIndex"
                        data-cy="orderIndex"
                        type="number"
                        value={formData.orderIndex}
                        onChange={e => setFormData({ ...formData, orderIndex: Number(e.target.value) })}
                        validate={{
                          required: { value: true, message: translate('entity.validation.required') },
                          validate: v => isNumber(v) || translate('entity.validation.number'),
                        }}
                      />
                    </Col>
                  </Row>
                </CardBody>
              </Card>

              <Card className="mb-3">
                <CardBody>
                  <h5 className="mb-3">Content & Instructions</h5>
                  <ValidatedField
                    label={translate('skillVerifyApp.exercise.instruction')}
                    id="exercise-instruction"
                    name="instruction"
                    data-cy="instruction"
                    type="textarea"
                    rows="3"
                    value={formData.instruction}
                    onChange={e => setFormData({ ...formData, instruction: e.target.value })}
                  />
                  <ValidatedField
                    label={translate('skillVerifyApp.exercise.content')}
                    id="exercise-content"
                    name="content"
                    data-cy="content"
                    type="textarea"
                    rows="4"
                    value={formData.content}
                    onChange={e => setFormData({ ...formData, content: e.target.value })}
                  />
                  <ValidatedField
                    label={translate('skillVerifyApp.exercise.explanation')}
                    id="exercise-explanation"
                    name="explanation"
                    data-cy="explanation"
                    type="textarea"
                    rows="3"
                    value={formData.explanation}
                    onChange={e => setFormData({ ...formData, explanation: e.target.value })}
                  />
                </CardBody>
              </Card>

              {(selectedType === 'QCM' || selectedType === 'TRUE_FALSE') && (
                <Card className="mb-3">
                  <CardBody>
                    <h5 className="mb-3">{selectedType === 'TRUE_FALSE' ? 'True/False Options' : 'Multiple Choice Options'}</h5>
                    {qcmOptions.map((option, index) => (
                      <Card key={index} className="mb-2">
                        <CardBody className="p-3">
                          <Row className="align-items-center">
                            <Col md="1" className="text-center">
                              <strong>#{index + 1}</strong>
                            </Col>
                            <Col md="7">
                              <ValidatedField
                                name={`qcmOption-${index}-content`}
                                label="Option Content"
                                value={option.content}
                                onChange={e => {
                                  const newOptions = [...qcmOptions];
                                  newOptions[index].content = e.target.value;
                                  setQcmOptions(newOptions);
                                }}
                                type="text"
                                validate={{
                                  required: { value: true, message: 'Option content is required' },
                                }}
                              />
                            </Col>
                            <Col md="2">
                              <div className="form-check mt-4">
                                <input
                                  type="checkbox"
                                  className="form-check-input"
                                  id={`qcmOption-${index}-isCorrect`}
                                  checked={option.isCorrect}
                                  onChange={e => {
                                    const newOptions = [...qcmOptions];
                                    newOptions[index].isCorrect = e.target.checked;
                                    setQcmOptions(newOptions);
                                  }}
                                />
                                <label className="form-check-label" htmlFor={`qcmOption-${index}-isCorrect`}>
                                  <strong>Correct Answer</strong>
                                </label>
                              </div>
                            </Col>
                            <Col md="2" className="text-end">
                              <Button
                                color="danger"
                                size="sm"
                                onClick={() => setQcmOptions(qcmOptions.filter((_, i) => i !== index))}
                                disabled={qcmOptions.length === 1}
                              >
                                <FontAwesomeIcon icon="trash" /> Remove
                              </Button>
                            </Col>
                          </Row>
                        </CardBody>
                      </Card>
                    ))}
                    <Button
                      color="success"
                      size="sm"
                      onClick={() =>
                        setQcmOptions([...qcmOptions, { id: null, content: '', isCorrect: false, orderIndex: qcmOptions.length }])
                      }
                    >
                      <FontAwesomeIcon icon="plus" /> Add Option
                    </Button>
                  </CardBody>
                </Card>
              )}

              {selectedType === 'FILL_IN_THE_BLANK' && (
                <Card className="mb-3">
                  <CardBody>
                    <h5 className="mb-3">Fill in the Blank Spaces</h5>
                    <p className="text-muted small">Define the blank spaces and their correct answers</p>
                    {blankSpaces.map((blank, index) => (
                      <Card key={index} className="mb-2">
                        <CardBody className="p-3">
                          <Row className="align-items-center">
                            <Col md="1" className="text-center">
                              <strong>#{index + 1}</strong>
                            </Col>
                            <Col md="2">
                              <ValidatedField
                                name={`blankSpace-${index}-position`}
                                label="Position"
                                value={blank.position}
                                onChange={e => {
                                  const newBlanks = [...blankSpaces];
                                  newBlanks[index].position = Number(e.target.value);
                                  setBlankSpaces(newBlanks);
                                }}
                                type="number"
                                validate={{
                                  required: { value: true, message: 'Position is required' },
                                }}
                              />
                            </Col>
                            <Col md="5">
                              <ValidatedField
                                name={`blankSpace-${index}-correctAnswer`}
                                label="Correct Answer"
                                value={blank.correctAnswer}
                                onChange={e => {
                                  const newBlanks = [...blankSpaces];
                                  newBlanks[index].correctAnswer = e.target.value;
                                  setBlankSpaces(newBlanks);
                                }}
                                type="text"
                                validate={{
                                  required: { value: true, message: 'Correct answer is required' },
                                }}
                              />
                            </Col>
                            <Col md="2">
                              <div className="form-check mt-4">
                                <input
                                  type="checkbox"
                                  className="form-check-input"
                                  id={`blankSpace-${index}-caseSensitive`}
                                  checked={blank.caseSensitive}
                                  onChange={e => {
                                    const newBlanks = [...blankSpaces];
                                    newBlanks[index].caseSensitive = e.target.checked;
                                    setBlankSpaces(newBlanks);
                                  }}
                                />
                                <label className="form-check-label" htmlFor={`blankSpace-${index}-caseSensitive`}>
                                  Case Sensitive
                                </label>
                              </div>
                            </Col>
                            <Col md="2" className="text-end">
                              <Button
                                color="danger"
                                size="sm"
                                onClick={() => setBlankSpaces(blankSpaces.filter((_, i) => i !== index))}
                                disabled={blankSpaces.length === 1}
                              >
                                <FontAwesomeIcon icon="trash" /> Remove
                              </Button>
                            </Col>
                          </Row>
                        </CardBody>
                      </Card>
                    ))}
                    <Button
                      color="success"
                      size="sm"
                      onClick={() =>
                        setBlankSpaces([
                          ...blankSpaces,
                          {
                            id: null,
                            position: blankSpaces.length,
                            correctAnswer: '',
                            caseSensitive: false,
                            orderIndex: blankSpaces.length,
                          },
                        ])
                      }
                    >
                      <FontAwesomeIcon icon="plus" /> Add Blank Space
                    </Button>
                  </CardBody>
                </Card>
              )}

              {selectedType === 'BIND_LISTS' && (
                <Card className="mb-3">
                  <CardBody>
                    <h5 className="mb-3">Matching Lists</h5>
                    <p className="text-muted small">Create pairs of items to match</p>
                    {listItems.map((item, index) => (
                      <Card key={index} className="mb-2">
                        <CardBody className="p-3">
                          <Row className="align-items-center">
                            <Col md="1" className="text-center">
                              <strong>#{index + 1}</strong>
                            </Col>
                            <Col md="4">
                              <ValidatedField
                                name={`listItem-${index}-content`}
                                label="Left Item"
                                value={item.content}
                                onChange={e => {
                                  const newItems = [...listItems];
                                  newItems[index].content = e.target.value;
                                  setListItems(newItems);
                                }}
                                type="text"
                                validate={{
                                  required: { value: true, message: 'Left item is required' },
                                }}
                              />
                            </Col>
                            <Col md="1" className="text-center">
                              <FontAwesomeIcon icon="arrows-alt-h" size="lg" className="text-muted" />
                            </Col>
                            <Col md="4">
                              <ValidatedField
                                name={`listItem-${index}-matchingContent`}
                                label="Right Item (Match)"
                                value={item.matchingContent}
                                onChange={e => {
                                  const newItems = [...listItems];
                                  newItems[index].matchingContent = e.target.value;
                                  setListItems(newItems);
                                }}
                                type="text"
                                validate={{
                                  required: { value: true, message: 'Matching item is required' },
                                }}
                              />
                            </Col>
                            <Col md="2" className="text-end">
                              <Button
                                color="danger"
                                size="sm"
                                onClick={() => setListItems(listItems.filter((_, i) => i !== index))}
                                disabled={listItems.length === 1}
                              >
                                <FontAwesomeIcon icon="trash" /> Remove
                              </Button>
                            </Col>
                          </Row>
                        </CardBody>
                      </Card>
                    ))}
                    <Button
                      color="success"
                      size="sm"
                      onClick={() =>
                        setListItems([
                          ...listItems,
                          { id: null, content: '', matchingContent: '', isLeftSide: true, orderIndex: listItems.length },
                        ])
                      }
                    >
                      <FontAwesomeIcon icon="plus" /> Add Matching Pair
                    </Button>
                  </CardBody>
                </Card>
              )}

              {selectedType === 'SHORT_ANSWER' && (
                <Card className="mb-3">
                  <CardBody>
                    <div className="alert alert-info">
                      <FontAwesomeIcon icon="info-circle" /> Short answer exercises are evaluated manually. Use the content field above to
                      provide the question.
                    </div>
                  </CardBody>
                </Card>
              )}

              <div className="d-flex justify-content-between mt-4">
                <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/exercise" replace color="secondary">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">
                    <Translate contentKey="entity.action.back">Back</Translate>
                  </span>
                </Button>
                <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp;
                  <Translate contentKey="entity.action.save">Save</Translate>
                </Button>
              </div>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default ExerciseUpdate;
