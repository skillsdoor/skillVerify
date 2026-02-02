import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import CandidateAnswer from './candidate-answer';
import CandidateAnswerDetail from './candidate-answer-detail';
import CandidateAnswerUpdate from './candidate-answer-update';
import CandidateAnswerDeleteDialog from './candidate-answer-delete-dialog';

const CandidateAnswerRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<CandidateAnswer />} />
    <Route path="new" element={<CandidateAnswerUpdate />} />
    <Route path=":id">
      <Route index element={<CandidateAnswerDetail />} />
      <Route path="edit" element={<CandidateAnswerUpdate />} />
      <Route path="delete" element={<CandidateAnswerDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CandidateAnswerRoutes;
