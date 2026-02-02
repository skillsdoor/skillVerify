import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ListItem from './list-item';
import ListItemDetail from './list-item-detail';
import ListItemUpdate from './list-item-update';
import ListItemDeleteDialog from './list-item-delete-dialog';

const ListItemRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ListItem />} />
    <Route path="new" element={<ListItemUpdate />} />
    <Route path=":id">
      <Route index element={<ListItemDetail />} />
      <Route path="edit" element={<ListItemUpdate />} />
      <Route path="delete" element={<ListItemDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ListItemRoutes;
