import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './media.reducer';

export const MediaDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const mediaEntity = useAppSelector(state => state.media.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="mediaDetailsHeading">
          <Translate contentKey="skillVerifyApp.media.detail.title">Media</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{mediaEntity.id}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="skillVerifyApp.media.type">Type</Translate>
            </span>
          </dt>
          <dd>{mediaEntity.type}</dd>
          <dt>
            <span id="s3Key">
              <Translate contentKey="skillVerifyApp.media.s3Key">S 3 Key</Translate>
            </span>
          </dt>
          <dd>{mediaEntity.s3Key}</dd>
          <dt>
            <span id="s3Url">
              <Translate contentKey="skillVerifyApp.media.s3Url">S 3 Url</Translate>
            </span>
          </dt>
          <dd>{mediaEntity.s3Url}</dd>
          <dt>
            <span id="fileName">
              <Translate contentKey="skillVerifyApp.media.fileName">File Name</Translate>
            </span>
          </dt>
          <dd>{mediaEntity.fileName}</dd>
          <dt>
            <span id="fileSize">
              <Translate contentKey="skillVerifyApp.media.fileSize">File Size</Translate>
            </span>
          </dt>
          <dd>{mediaEntity.fileSize}</dd>
          <dt>
            <span id="mimeType">
              <Translate contentKey="skillVerifyApp.media.mimeType">Mime Type</Translate>
            </span>
          </dt>
          <dd>{mediaEntity.mimeType}</dd>
          <dt>
            <span id="uploadedAt">
              <Translate contentKey="skillVerifyApp.media.uploadedAt">Uploaded At</Translate>
            </span>
          </dt>
          <dd>{mediaEntity.uploadedAt ? <TextFormat value={mediaEntity.uploadedAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="skillVerifyApp.media.question">Question</Translate>
          </dt>
          <dd>{mediaEntity.question ? mediaEntity.question.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/media" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/media/${mediaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default MediaDetail;
