import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, FormText, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { createEntity, getEntity, reset, updateEntity } from './recruiter.reducer';

export const RecruiterUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const users = useAppSelector(state => state.userManagement.users);
  const recruiterEntity = useAppSelector(state => state.recruiter.entity);
  const loading = useAppSelector(state => state.recruiter.loading);
  const updating = useAppSelector(state => state.recruiter.updating);
  const updateSuccess = useAppSelector(state => state.recruiter.updateSuccess);

  const handleClose = () => {
    navigate(`/recruiter${location.search}`);
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
    values.createdAt = convertDateTimeToServer(values.createdAt);
    values.updatedAt = convertDateTimeToServer(values.updatedAt);

    const entity = {
      ...recruiterEntity,
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
          ...recruiterEntity,
          createdAt: convertDateTimeFromServer(recruiterEntity.createdAt),
          updatedAt: convertDateTimeFromServer(recruiterEntity.updatedAt),
          user: recruiterEntity?.user?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="skillVerifyApp.recruiter.home.createOrEditLabel" data-cy="RecruiterCreateUpdateHeading">
            <Translate contentKey="skillVerifyApp.recruiter.home.createOrEditLabel">Create or edit a Recruiter</Translate>
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
                  id="recruiter-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('skillVerifyApp.recruiter.firstName')}
                id="recruiter-firstName"
                name="firstName"
                data-cy="firstName"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('skillVerifyApp.recruiter.lastName')}
                id="recruiter-lastName"
                name="lastName"
                data-cy="lastName"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('skillVerifyApp.recruiter.phone')}
                id="recruiter-phone"
                name="phone"
                data-cy="phone"
                type="text"
              />
              <ValidatedField
                label={translate('skillVerifyApp.recruiter.bio')}
                id="recruiter-bio"
                name="bio"
                data-cy="bio"
                type="textarea"
              />
              <ValidatedField
                label={translate('skillVerifyApp.recruiter.avatarUrl')}
                id="recruiter-avatarUrl"
                name="avatarUrl"
                data-cy="avatarUrl"
                type="text"
              />
              <ValidatedField
                label={translate('skillVerifyApp.recruiter.company')}
                id="recruiter-company"
                name="company"
                data-cy="company"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('skillVerifyApp.recruiter.position')}
                id="recruiter-position"
                name="position"
                data-cy="position"
                type="text"
              />
              <ValidatedField
                label={translate('skillVerifyApp.recruiter.website')}
                id="recruiter-website"
                name="website"
                data-cy="website"
                type="text"
              />
              <ValidatedField
                label={translate('skillVerifyApp.recruiter.createdAt')}
                id="recruiter-createdAt"
                name="createdAt"
                data-cy="createdAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('skillVerifyApp.recruiter.updatedAt')}
                id="recruiter-updatedAt"
                name="updatedAt"
                data-cy="updatedAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="recruiter-user"
                name="user"
                data-cy="user"
                label={translate('skillVerifyApp.recruiter.user')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/recruiter" replace color="info">
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

export default RecruiterUpdate;
