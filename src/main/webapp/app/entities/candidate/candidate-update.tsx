import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, FormText, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { createEntity, getEntity, reset, updateEntity } from './candidate.reducer';

export const CandidateUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const users = useAppSelector(state => state.userManagement.users);
  const candidateEntity = useAppSelector(state => state.candidate.entity);
  const loading = useAppSelector(state => state.candidate.loading);
  const updating = useAppSelector(state => state.candidate.updating);
  const updateSuccess = useAppSelector(state => state.candidate.updateSuccess);

  const handleClose = () => {
    navigate(`/candidate${location.search}`);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getUsers({}));
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
    if (values.yearsOfExperience !== undefined && typeof values.yearsOfExperience !== 'number') {
      values.yearsOfExperience = Number(values.yearsOfExperience);
    }
    values.createdAt = convertDateTimeToServer(values.createdAt);
    values.updatedAt = convertDateTimeToServer(values.updatedAt);

    const entity = {
      ...candidateEntity,
      ...values,
      user: users.find(it => it.id.toString() === values.user?.toString()),
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
        }
      : {
          ...candidateEntity,
          createdAt: convertDateTimeFromServer(candidateEntity.createdAt),
          updatedAt: convertDateTimeFromServer(candidateEntity.updatedAt),
          user: candidateEntity?.user?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="skillVerifyApp.candidate.home.createOrEditLabel" data-cy="CandidateCreateUpdateHeading">
            <Translate contentKey="skillVerifyApp.candidate.home.createOrEditLabel">Create or edit a Candidate</Translate>
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
                  id="candidate-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('skillVerifyApp.candidate.firstName')}
                id="candidate-firstName"
                name="firstName"
                data-cy="firstName"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('skillVerifyApp.candidate.lastName')}
                id="candidate-lastName"
                name="lastName"
                data-cy="lastName"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('skillVerifyApp.candidate.phone')}
                id="candidate-phone"
                name="phone"
                data-cy="phone"
                type="text"
              />
              <ValidatedField
                label={translate('skillVerifyApp.candidate.bio')}
                id="candidate-bio"
                name="bio"
                data-cy="bio"
                type="textarea"
              />
              <ValidatedField
                label={translate('skillVerifyApp.candidate.avatarUrl')}
                id="candidate-avatarUrl"
                name="avatarUrl"
                data-cy="avatarUrl"
                type="text"
              />
              <ValidatedField
                label={translate('skillVerifyApp.candidate.resumeUrl')}
                id="candidate-resumeUrl"
                name="resumeUrl"
                data-cy="resumeUrl"
                type="text"
              />
              <ValidatedField
                label={translate('skillVerifyApp.candidate.linkedinUrl')}
                id="candidate-linkedinUrl"
                name="linkedinUrl"
                data-cy="linkedinUrl"
                type="text"
              />
              <ValidatedField
                label={translate('skillVerifyApp.candidate.githubUrl')}
                id="candidate-githubUrl"
                name="githubUrl"
                data-cy="githubUrl"
                type="text"
              />
              <ValidatedField
                label={translate('skillVerifyApp.candidate.yearsOfExperience')}
                id="candidate-yearsOfExperience"
                name="yearsOfExperience"
                data-cy="yearsOfExperience"
                type="text"
              />
              <ValidatedField
                label={translate('skillVerifyApp.candidate.createdAt')}
                id="candidate-createdAt"
                name="createdAt"
                data-cy="createdAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('skillVerifyApp.candidate.updatedAt')}
                id="candidate-updatedAt"
                name="updatedAt"
                data-cy="updatedAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="candidate-user"
                name="user"
                data-cy="user"
                label={translate('skillVerifyApp.candidate.user')}
                type="select"
                required
              >
                <option value="" key="0" />
                {users
                  ? users.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.login}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/candidate" replace color="info">
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

export default CandidateUpdate;
