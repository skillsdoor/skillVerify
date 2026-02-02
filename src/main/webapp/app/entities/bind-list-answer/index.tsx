import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import BindListAnswer from './bind-list-answer';
import BindListAnswerDetail from './bind-list-answer-detail';
import BindListAnswerUpdate from './bind-list-answer-update';
import BindListAnswerDeleteDialog from './bind-list-answer-delete-dialog';

const BindListAnswerRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<BindListAnswer />} />
    <Route path="new" element={<BindListAnswerUpdate />} />
    <Route path=":id">
      <Route index element={<BindListAnswerDetail />} />
      <Route path="edit" element={<BindListAnswerUpdate />} />
      <Route path="delete" element={<BindListAnswerDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BindListAnswerRoutes;
