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

import { getEntities } from './candidate.reducer';

export const Candidate = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );

  const candidateList = useAppSelector(state => state.candidate.entities);
  const loading = useAppSelector(state => state.candidate.loading);
  const totalItems = useAppSelector(state => state.candidate.totalItems);

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
      <h2 id="candidate-heading" data-cy="CandidateHeading">
        <Translate contentKey="skillVerifyApp.candidate.home.title">Candidates</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="skillVerifyApp.candidate.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/candidate/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="skillVerifyApp.candidate.home.createLabel">Create new Candidate</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {candidateList && candidateList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="skillVerifyApp.candidate.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('firstName')}>
                  <Translate contentKey="skillVerifyApp.candidate.firstName">First Name</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('firstName')} />
                </th>
                <th className="hand" onClick={sort('lastName')}>
                  <Translate contentKey="skillVerifyApp.candidate.lastName">Last Name</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('lastName')} />
                </th>
                <th className="hand" onClick={sort('phone')}>
                  <Translate contentKey="skillVerifyApp.candidate.phone">Phone</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('phone')} />
                </th>
                <th className="hand" onClick={sort('bio')}>
                  <Translate contentKey="skillVerifyApp.candidate.bio">Bio</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('bio')} />
                </th>
                <th className="hand" onClick={sort('avatarUrl')}>
                  <Translate contentKey="skillVerifyApp.candidate.avatarUrl">Avatar Url</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('avatarUrl')} />
                </th>
                <th className="hand" onClick={sort('resumeUrl')}>
                  <Translate contentKey="skillVerifyApp.candidate.resumeUrl">Resume Url</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('resumeUrl')} />
                </th>
                <th className="hand" onClick={sort('linkedinUrl')}>
                  <Translate contentKey="skillVerifyApp.candidate.linkedinUrl">Linkedin Url</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('linkedinUrl')} />
                </th>
                <th className="hand" onClick={sort('githubUrl')}>
                  <Translate contentKey="skillVerifyApp.candidate.githubUrl">Github Url</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('githubUrl')} />
                </th>
                <th className="hand" onClick={sort('yearsOfExperience')}>
                  <Translate contentKey="skillVerifyApp.candidate.yearsOfExperience">Years Of Experience</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('yearsOfExperience')} />
                </th>
                <th className="hand" onClick={sort('createdAt')}>
                  <Translate contentKey="skillVerifyApp.candidate.createdAt">Created At</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('createdAt')} />
                </th>
                <th className="hand" onClick={sort('updatedAt')}>
                  <Translate contentKey="skillVerifyApp.candidate.updatedAt">Updated At</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('updatedAt')} />
                </th>
                <th>
                  <Translate contentKey="skillVerifyApp.candidate.user">User</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {candidateList.map((candidate, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/candidate/${candidate.id}`} color="link" size="sm">
                      {candidate.id}
                    </Button>
                  </td>
                  <td>{candidate.firstName}</td>
                  <td>{candidate.lastName}</td>
                  <td>{candidate.phone}</td>
                  <td>{candidate.bio}</td>
                  <td>{candidate.avatarUrl}</td>
                  <td>{candidate.resumeUrl}</td>
                  <td>{candidate.linkedinUrl}</td>
                  <td>{candidate.githubUrl}</td>
                  <td>{candidate.yearsOfExperience}</td>
                  <td>{candidate.createdAt ? <TextFormat type="date" value={candidate.createdAt} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{candidate.updatedAt ? <TextFormat type="date" value={candidate.updatedAt} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{candidate.user ? candidate.user.login : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/candidate/${candidate.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/candidate/${candidate.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                          (window.location.href = `/candidate/${candidate.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`)
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
              <Translate contentKey="skillVerifyApp.candidate.home.notFound">No Candidates found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={candidateList && candidateList.length > 0 ? '' : 'd-none'}>
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

export default Candidate;
