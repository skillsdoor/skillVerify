import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import BindList from './bind-list';
import BindListDetail from './bind-list-detail';
import BindListUpdate from './bind-list-update';
import BindListDeleteDialog from './bind-list-delete-dialog';

const BindListRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<BindList />} />
    <Route path="new" element={<BindListUpdate />} />
    <Route path=":id">
      <Route index element={<BindListDetail />} />
      <Route path="edit" element={<BindListUpdate />} />
      <Route path="delete" element={<BindListDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BindListRoutes;
