import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ExamSession from './exam-session';
import ExamSessionDetail from './exam-session-detail';
import ExamSessionUpdate from './exam-session-update';
import ExamSessionDeleteDialog from './exam-session-delete-dialog';

const ExamSessionRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ExamSession />} />
    <Route path="new" element={<ExamSessionUpdate />} />
    <Route path=":id">
      <Route index element={<ExamSessionDetail />} />
      <Route path="edit" element={<ExamSessionUpdate />} />
      <Route path="delete" element={<ExamSessionDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ExamSessionRoutes;
