import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import BlankAnswer from './blank-answer';
import BlankAnswerDetail from './blank-answer-detail';
import BlankAnswerUpdate from './blank-answer-update';
import BlankAnswerDeleteDialog from './blank-answer-delete-dialog';

const BlankAnswerRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<BlankAnswer />} />
    <Route path="new" element={<BlankAnswerUpdate />} />
    <Route path=":id">
      <Route index element={<BlankAnswerDetail />} />
      <Route path="edit" element={<BlankAnswerUpdate />} />
      <Route path="delete" element={<BlankAnswerDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BlankAnswerRoutes;
