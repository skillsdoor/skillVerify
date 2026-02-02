import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import BlankSpace from './blank-space';
import BlankSpaceDetail from './blank-space-detail';
import BlankSpaceUpdate from './blank-space-update';
import BlankSpaceDeleteDialog from './blank-space-delete-dialog';

const BlankSpaceRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<BlankSpace />} />
    <Route path="new" element={<BlankSpaceUpdate />} />
    <Route path=":id">
      <Route index element={<BlankSpaceDetail />} />
      <Route path="edit" element={<BlankSpaceUpdate />} />
      <Route path="delete" element={<BlankSpaceDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BlankSpaceRoutes;
