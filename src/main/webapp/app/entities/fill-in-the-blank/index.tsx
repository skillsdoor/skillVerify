import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import FillInTheBlank from './fill-in-the-blank';
import FillInTheBlankDetail from './fill-in-the-blank-detail';
import FillInTheBlankUpdate from './fill-in-the-blank-update';
import FillInTheBlankDeleteDialog from './fill-in-the-blank-delete-dialog';

const FillInTheBlankRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<FillInTheBlank />} />
    <Route path="new" element={<FillInTheBlankUpdate />} />
    <Route path=":id">
      <Route index element={<FillInTheBlankDetail />} />
      <Route path="edit" element={<FillInTheBlankUpdate />} />
      <Route path="delete" element={<FillInTheBlankDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FillInTheBlankRoutes;
