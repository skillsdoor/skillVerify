import React, { useEffect, useState } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { JhiItemCount, JhiPagination, TextFormat, Translate, getPaginationState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortDown, faSortUp } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './exam.reducer';

export const Exam = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );

  const examList = useAppSelector(state => state.exam.entities);
  const loading = useAppSelector(state => state.exam.loading);
  const totalItems = useAppSelector(state => state.exam.totalItems);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      }),
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (pageLocation.search !== endURL) {
      navigate(`${pageLocation.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(pageLocation.search);
    const page = params.get('page');
    const sort = params.get(SORT);
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [pageLocation.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = paginationState.sort;
    const order = paginationState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    }
    return order === ASC ? faSortUp : faSortDown;
  };

  return (
    <div>
      <h2 id="exam-heading" data-cy="ExamHeading">
        <Translate contentKey="skillVerifyApp.exam.home.title">Exams</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="skillVerifyApp.exam.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/exam/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="skillVerifyApp.exam.home.createLabel">Create new Exam</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {examList && examList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="skillVerifyApp.exam.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('title')}>
                  <Translate contentKey="skillVerifyApp.exam.title">Title</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('title')} />
                </th>
                <th className="hand" onClick={sort('description')}>
                  <Translate contentKey="skillVerifyApp.exam.description">Description</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('description')} />
                </th>
                <th className="hand" onClick={sort('duration')}>
                  <Translate contentKey="skillVerifyApp.exam.duration">Duration</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('duration')} />
                </th>
                <th className="hand" onClick={sort('passingScore')}>
                  <Translate contentKey="skillVerifyApp.exam.passingScore">Passing Score</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('passingScore')} />
                </th>
                <th className="hand" onClick={sort('status')}>
                  <Translate contentKey="skillVerifyApp.exam.status">Status</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('status')} />
                </th>
                <th className="hand" onClick={sort('testType')}>
                  <Translate contentKey="skillVerifyApp.exam.testType">Test Type</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('testType')} />
                </th>
                <th className="hand" onClick={sort('createdAt')}>
                  <Translate contentKey="skillVerifyApp.exam.createdAt">Created At</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('createdAt')} />
                </th>
                <th className="hand" onClick={sort('updatedAt')}>
                  <Translate contentKey="skillVerifyApp.exam.updatedAt">Updated At</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('updatedAt')} />
                </th>
                <th className="hand" onClick={sort('publishedAt')}>
                  <Translate contentKey="skillVerifyApp.exam.publishedAt">Published At</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('publishedAt')} />
                </th>
                <th>
                  <Translate contentKey="skillVerifyApp.exam.recruiter">Recruiter</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="skillVerifyApp.exam.trainingProvider">Training Provider</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {examList.map((exam, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/exam/${exam.id}`} color="link" size="sm">
                      {exam.id}
                    </Button>
                  </td>
                  <td>{exam.title}</td>
                  <td>{exam.description}</td>
                  <td>{exam.duration}</td>
                  <td>{exam.passingScore}</td>
                  <td>
                    <Translate contentKey={`skillVerifyApp.ExamStatus.${exam.status}`} />
                  </td>
                  <td>
                    <Translate contentKey={`skillVerifyApp.TestType.${exam.testType}`} />
                  </td>
                  <td>{exam.createdAt ? <TextFormat type="date" value={exam.createdAt} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{exam.updatedAt ? <TextFormat type="date" value={exam.updatedAt} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{exam.publishedAt ? <TextFormat type="date" value={exam.publishedAt} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{exam.recruiter ? <Link to={`/recruiter/${exam.recruiter.id}`}>{exam.recruiter.id}</Link> : ''}</td>
                  <td>
                    {exam.trainingProvider ? (
                      <Link to={`/training-provider/${exam.trainingProvider.id}`}>{exam.trainingProvider.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/exam/${exam.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/exam/${exam.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        onClick={() =>
                          (window.location.href = `/exam/${exam.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`)
                        }
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="skillVerifyApp.exam.home.notFound">No Exams found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={examList && examList.length > 0 ? '' : 'd-none'}>
          <div className="justify-content-center d-flex">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </div>
          <div className="justify-content-center d-flex">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={totalItems}
            />
          </div>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

export default Exam;
