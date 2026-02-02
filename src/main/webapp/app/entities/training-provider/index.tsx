import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import TrainingProvider from './training-provider';
import TrainingProviderDetail from './training-provider-detail';
import TrainingProviderUpdate from './training-provider-update';
import TrainingProviderDeleteDialog from './training-provider-delete-dialog';

const TrainingProviderRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<TrainingProvider />} />
    <Route path="new" element={<TrainingProviderUpdate />} />
    <Route path=":id">
      <Route index element={<TrainingProviderDetail />} />
      <Route path="edit" element={<TrainingProviderUpdate />} />
      <Route path="delete" element={<TrainingProviderDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TrainingProviderRoutes;
