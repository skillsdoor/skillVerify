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

import { getEntities } from './exam-session.reducer';

export const ExamSession = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );

  const examSessionList = useAppSelector(state => state.examSession.entities);
  const loading = useAppSelector(state => state.examSession.loading);
  const totalItems = useAppSelector(state => state.examSession.totalItems);

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
      <h2 id="exam-session-heading" data-cy="ExamSessionHeading">
        <Translate contentKey="skillVerifyApp.examSession.home.title">Exam Sessions</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="skillVerifyApp.examSession.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/exam-session/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="skillVerifyApp.examSession.home.createLabel">Create new Exam Session</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {examSessionList && examSessionList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="skillVerifyApp.examSession.id">ID</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('startedAt')}>
                  <Translate contentKey="skillVerifyApp.examSession.startedAt">Started At</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('startedAt')} />
                </th>
                <th className="hand" onClick={sort('submittedAt')}>
                  <Translate contentKey="skillVerifyApp.examSession.submittedAt">Submitted At</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('submittedAt')} />
                </th>
                <th className="hand" onClick={sort('status')}>
                  <Translate contentKey="skillVerifyApp.examSession.status">Status</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('status')} />
                </th>
                <th className="hand" onClick={sort('score')}>
                  <Translate contentKey="skillVerifyApp.examSession.score">Score</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('score')} />
                </th>
                <th className="hand" onClick={sort('totalPoints')}>
                  <Translate contentKey="skillVerifyApp.examSession.totalPoints">Total Points</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('totalPoints')} />
                </th>
                <th className="hand" onClick={sort('timeSpent')}>
                  <Translate contentKey="skillVerifyApp.examSession.timeSpent">Time Spent</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('timeSpent')} />
                </th>
                <th>
                  <Translate contentKey="skillVerifyApp.examSession.candidate">Candidate</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="skillVerifyApp.examSession.exam">Exam</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {examSessionList.map((examSession, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/exam-session/${examSession.id}`} color="link" size="sm">
                      {examSession.id}
                    </Button>
                  </td>
                  <td>
                    {examSession.startedAt ? <TextFormat type="date" value={examSession.startedAt} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {examSession.submittedAt ? <TextFormat type="date" value={examSession.submittedAt} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    <Translate contentKey={`skillVerifyApp.SubmissionStatus.${examSession.status}`} />
                  </td>
                  <td>{examSession.score}</td>
                  <td>{examSession.totalPoints}</td>
                  <td>{examSession.timeSpent}</td>
                  <td>
                    {examSession.candidate ? <Link to={`/candidate/${examSession.candidate.id}`}>{examSession.candidate.id}</Link> : ''}
                  </td>
                  <td>{examSession.exam ? <Link to={`/exam/${examSession.exam.id}`}>{examSession.exam.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/exam-session/${examSession.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/exam-session/${examSession.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                          (window.location.href = `/exam-session/${examSession.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`)
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
              <Translate contentKey="skillVerifyApp.examSession.home.notFound">No Exam Sessions found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={examSessionList && examSessionList.length > 0 ? '' : 'd-none'}>
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

export default ExamSession;
