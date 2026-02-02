import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, FormText, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { createEntity, getEntity, reset, updateEntity } from './training-provider.reducer';

export const TrainingProviderUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const users = useAppSelector(state => state.userManagement.users);
  const trainingProviderEntity = useAppSelector(state => state.trainingProvider.entity);
  const loading = useAppSelector(state => state.trainingProvider.loading);
  const updating = useAppSelector(state => state.trainingProvider.updating);
  const updateSuccess = useAppSelector(state => state.trainingProvider.updateSuccess);

  const handleClose = () => {
    navigate(`/training-provider${location.search}`);
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
      ...trainingProviderEntity,
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
          ...trainingProviderEntity,
          createdAt: convertDateTimeFromServer(trainingProviderEntity.createdAt),
          updatedAt: convertDateTimeFromServer(trainingProviderEntity.updatedAt),
          user: trainingProviderEntity?.user?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="skillVerifyApp.trainingProvider.home.createOrEditLabel" data-cy="TrainingProviderCreateUpdateHeading">
            <Translate contentKey="skillVerifyApp.trainingProvider.home.createOrEditLabel">Create or edit a TrainingProvider</Translate>
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
                  id="training-provider-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('skillVerifyApp.trainingProvider.firstName')}
                id="training-provider-firstName"
                name="firstName"
                data-cy="firstName"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('skillVerifyApp.trainingProvider.lastName')}
                id="training-provider-lastName"
                name="lastName"
                data-cy="lastName"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('skillVerifyApp.trainingProvider.phone')}
                id="training-provider-phone"
                name="phone"
                data-cy="phone"
                type="text"
              />
              <ValidatedField
                label={translate('skillVerifyApp.trainingProvider.bio')}
                id="training-provider-bio"
                name="bio"
                data-cy="bio"
                type="textarea"
              />
              <ValidatedField
                label={translate('skillVerifyApp.trainingProvider.avatarUrl')}
                id="training-provider-avatarUrl"
                name="avatarUrl"
                data-cy="avatarUrl"
                type="text"
              />
              <ValidatedField
                label={translate('skillVerifyApp.trainingProvider.organizationName')}
                id="training-provider-organizationName"
                name="organizationName"
                data-cy="organizationName"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('skillVerifyApp.trainingProvider.website')}
                id="training-provider-website"
                name="website"
                data-cy="website"
                type="text"
              />
              <ValidatedField
                label={translate('skillVerifyApp.trainingProvider.accreditation')}
                id="training-provider-accreditation"
                name="accreditation"
                data-cy="accreditation"
                type="text"
              />
              <ValidatedField
                label={translate('skillVerifyApp.trainingProvider.createdAt')}
                id="training-provider-createdAt"
                name="createdAt"
                data-cy="createdAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('skillVerifyApp.trainingProvider.updatedAt')}
                id="training-provider-updatedAt"
                name="updatedAt"
                data-cy="updatedAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="training-provider-user"
                name="user"
                data-cy="user"
                label={translate('skillVerifyApp.trainingProvider.user')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/training-provider" replace color="info">
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

export default TrainingProviderUpdate;
