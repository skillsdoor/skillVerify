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

import { getEntities } from './candidate-answer.reducer';

export const CandidateAnswer = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );

  const candidateAnswerList = useAppSelector(state => state.candidateAnswer.entities);
  const loading = useAppSelector(state => state.candidateAnswer.loading);
  const totalItems = useAppSelector(state => state.candidateAnswer.totalItems);

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
      <h2 id="candidate-answer-heading" data-cy="CandidateAnswerHeading">
        <Translate contentKey="skillVerifyApp.candidateAnswer.home.title">Candidate Answers</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="skillVerifyApp.candidateAnswer.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/candidate-answer/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="skillVerifyApp.candidateAnswer.home.createLabel">Create new Candidate Answer</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {candidateAnswerList && candidateAnswerList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="skillVerifyApp.candidateAnswer.id">ID</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('answerText')}>
                  <Translate contentKey="skillVerifyApp.candidateAnswer.answerText">Answer Text</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('answerText')} />
                </th>
                <th className="hand" onClick={sort('selectedOptionId')}>
                  <Translate contentKey="skillVerifyApp.candidateAnswer.selectedOptionId">Selected Option Id</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('selectedOptionId')} />
                </th>
                <th className="hand" onClick={sort('isCorrect')}>
                  <Translate contentKey="skillVerifyApp.candidateAnswer.isCorrect">Is Correct</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('isCorrect')} />
                </th>
                <th className="hand" onClick={sort('pointsAwarded')}>
                  <Translate contentKey="skillVerifyApp.candidateAnswer.pointsAwarded">Points Awarded</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('pointsAwarded')} />
                </th>
                <th className="hand" onClick={sort('answeredAt')}>
                  <Translate contentKey="skillVerifyApp.candidateAnswer.answeredAt">Answered At</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('answeredAt')} />
                </th>
                <th>
                  <Translate contentKey="skillVerifyApp.candidateAnswer.examSession">Exam Session</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="skillVerifyApp.candidateAnswer.question">Question</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {candidateAnswerList.map((candidateAnswer, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/candidate-answer/${candidateAnswer.id}`} color="link" size="sm">
                      {candidateAnswer.id}
                    </Button>
                  </td>
                  <td>{candidateAnswer.answerText}</td>
                  <td>{candidateAnswer.selectedOptionId}</td>
                  <td>{candidateAnswer.isCorrect ? 'true' : 'false'}</td>
                  <td>{candidateAnswer.pointsAwarded}</td>
                  <td>
                    {candidateAnswer.answeredAt ? (
                      <TextFormat type="date" value={candidateAnswer.answeredAt} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {candidateAnswer.examSession ? (
                      <Link to={`/exam-session/${candidateAnswer.examSession.id}`}>{candidateAnswer.examSession.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {candidateAnswer.question ? (
                      <Link to={`/question/${candidateAnswer.question.id}`}>{candidateAnswer.question.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/candidate-answer/${candidateAnswer.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/candidate-answer/${candidateAnswer.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                          (window.location.href = `/candidate-answer/${candidateAnswer.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`)
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
              <Translate contentKey="skillVerifyApp.candidateAnswer.home.notFound">No Candidate Answers found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={candidateAnswerList && candidateAnswerList.length > 0 ? '' : 'd-none'}>
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

export default CandidateAnswer;
