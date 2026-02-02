import React, { useEffect, useState } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { JhiItemCount, JhiPagination, Translate, getPaginationState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortDown, faSortUp } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './fill-in-the-blank.reducer';

export const FillInTheBlank = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );

  const fillInTheBlankList = useAppSelector(state => state.fillInTheBlank.entities);
  const loading = useAppSelector(state => state.fillInTheBlank.loading);
  const totalItems = useAppSelector(state => state.fillInTheBlank.totalItems);

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
      <h2 id="fill-in-the-blank-heading" data-cy="FillInTheBlankHeading">
        <Translate contentKey="skillVerifyApp.fillInTheBlank.home.title">Fill In The Blanks</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="skillVerifyApp.fillInTheBlank.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/fill-in-the-blank/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="skillVerifyApp.fillInTheBlank.home.createLabel">Create new Fill In The Blank</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {fillInTheBlankList && fillInTheBlankList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="skillVerifyApp.fillInTheBlank.id">ID</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('textWithBlanks')}>
                  <Translate contentKey="skillVerifyApp.fillInTheBlank.textWithBlanks">Text With Blanks</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('textWithBlanks')} />
                </th>
                <th className="hand" onClick={sort('blankIndex')}>
                  <Translate contentKey="skillVerifyApp.fillInTheBlank.blankIndex">Blank Index</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('blankIndex')} />
                </th>
                <th className="hand" onClick={sort('correctAnswer')}>
                  <Translate contentKey="skillVerifyApp.fillInTheBlank.correctAnswer">Correct Answer</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('correctAnswer')} />
                </th>
                <th className="hand" onClick={sort('caseSensitive')}>
                  <Translate contentKey="skillVerifyApp.fillInTheBlank.caseSensitive">Case Sensitive</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('caseSensitive')} />
                </th>
                <th>
                  <Translate contentKey="skillVerifyApp.fillInTheBlank.question">Question</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {fillInTheBlankList.map((fillInTheBlank, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/fill-in-the-blank/${fillInTheBlank.id}`} color="link" size="sm">
                      {fillInTheBlank.id}
                    </Button>
                  </td>
                  <td>{fillInTheBlank.textWithBlanks}</td>
                  <td>{fillInTheBlank.blankIndex}</td>
                  <td>{fillInTheBlank.correctAnswer}</td>
                  <td>{fillInTheBlank.caseSensitive ? 'true' : 'false'}</td>
                  <td>
                    {fillInTheBlank.question ? (
                      <Link to={`/question/${fillInTheBlank.question.id}`}>{fillInTheBlank.question.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/fill-in-the-blank/${fillInTheBlank.id}`}
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
                        to={`/fill-in-the-blank/${fillInTheBlank.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                          (window.location.href = `/fill-in-the-blank/${fillInTheBlank.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`)
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
              <Translate contentKey="skillVerifyApp.fillInTheBlank.home.notFound">No Fill In The Blanks found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={fillInTheBlankList && fillInTheBlankList.length > 0 ? '' : 'd-none'}>
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

export default FillInTheBlank;
