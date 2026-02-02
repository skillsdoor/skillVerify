import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import AnswerOption from './answer-option';
import AnswerOptionDetail from './answer-option-detail';
import AnswerOptionUpdate from './answer-option-update';
import AnswerOptionDeleteDialog from './answer-option-delete-dialog';

const AnswerOptionRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<AnswerOption />} />
    <Route path="new" element={<AnswerOptionUpdate />} />
    <Route path=":id">
      <Route index element={<AnswerOptionDetail />} />
      <Route path="edit" element={<AnswerOptionUpdate />} />
      <Route path="delete" element={<AnswerOptionDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AnswerOptionRoutes;
