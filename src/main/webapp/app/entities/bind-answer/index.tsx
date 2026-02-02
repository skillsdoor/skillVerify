import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import BindAnswer from './bind-answer';
import BindAnswerDetail from './bind-answer-detail';
import BindAnswerUpdate from './bind-answer-update';
import BindAnswerDeleteDialog from './bind-answer-delete-dialog';

const BindAnswerRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<BindAnswer />} />
    <Route path="new" element={<BindAnswerUpdate />} />
    <Route path=":id">
      <Route index element={<BindAnswerDetail />} />
      <Route path="edit" element={<BindAnswerUpdate />} />
      <Route path="delete" element={<BindAnswerDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BindAnswerRoutes;
