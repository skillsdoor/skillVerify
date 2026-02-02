import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Recruiter from './recruiter';
import RecruiterDetail from './recruiter-detail';
import RecruiterUpdate from './recruiter-update';
import RecruiterDeleteDialog from './recruiter-delete-dialog';

const RecruiterRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Recruiter />} />
    <Route path="new" element={<RecruiterUpdate />} />
    <Route path=":id">
      <Route index element={<RecruiterDetail />} />
      <Route path="edit" element={<RecruiterUpdate />} />
      <Route path="delete" element={<RecruiterDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RecruiterRoutes;
