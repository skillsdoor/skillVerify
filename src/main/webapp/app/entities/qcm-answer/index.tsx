import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import QcmAnswer from './qcm-answer';
import QcmAnswerDetail from './qcm-answer-detail';
import QcmAnswerUpdate from './qcm-answer-update';
import QcmAnswerDeleteDialog from './qcm-answer-delete-dialog';

const QcmAnswerRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<QcmAnswer />} />
    <Route path="new" element={<QcmAnswerUpdate />} />
    <Route path=":id">
      <Route index element={<QcmAnswerDetail />} />
      <Route path="edit" element={<QcmAnswerUpdate />} />
      <Route path="delete" element={<QcmAnswerDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default QcmAnswerRoutes;
