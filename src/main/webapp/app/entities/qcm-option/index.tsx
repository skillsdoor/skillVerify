import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import QcmOption from './qcm-option';
import QcmOptionDetail from './qcm-option-detail';
import QcmOptionUpdate from './qcm-option-update';
import QcmOptionDeleteDialog from './qcm-option-delete-dialog';

const QcmOptionRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<QcmOption />} />
    <Route path="new" element={<QcmOptionUpdate />} />
    <Route path=":id">
      <Route index element={<QcmOptionDetail />} />
      <Route path="edit" element={<QcmOptionUpdate />} />
      <Route path="delete" element={<QcmOptionDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default QcmOptionRoutes;
