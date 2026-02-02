import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Recruiter from './recruiter';
import Candidate from './candidate';
import TrainingProvider from './training-provider';
import Exam from './exam';
import Exercise from './exercise';
import Question from './question';
import Media from './media';
import AnswerOption from './answer-option';
import FillInTheBlank from './fill-in-the-blank';
import BindList from './bind-list';
import ExamSession from './exam-session';
import CandidateAnswer from './candidate-answer';
import BindListAnswer from './bind-list-answer';
import QcmOption from './qcm-option';
import BlankSpace from './blank-space';
import ListItem from './list-item';
import QcmAnswer from './qcm-answer';
import BlankAnswer from './blank-answer';
import BindAnswer from './bind-answer';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="recruiter/*" element={<Recruiter />} />
        <Route path="candidate/*" element={<Candidate />} />
        <Route path="training-provider/*" element={<TrainingProvider />} />
        <Route path="exam/*" element={<Exam />} />
        <Route path="exercise/*" element={<Exercise />} />
        <Route path="question/*" element={<Question />} />
        <Route path="media/*" element={<Media />} />
        <Route path="answer-option/*" element={<AnswerOption />} />
        <Route path="fill-in-the-blank/*" element={<FillInTheBlank />} />
        <Route path="bind-list/*" element={<BindList />} />
        <Route path="exam-session/*" element={<ExamSession />} />
        <Route path="candidate-answer/*" element={<CandidateAnswer />} />
        <Route path="bind-list-answer/*" element={<BindListAnswer />} />
        <Route path="qcm-option/*" element={<QcmOption />} />
        <Route path="blank-space/*" element={<BlankSpace />} />
        <Route path="list-item/*" element={<ListItem />} />
        <Route path="qcm-answer/*" element={<QcmAnswer />} />
        <Route path="blank-answer/*" element={<BlankAnswer />} />
        <Route path="bind-answer/*" element={<BindAnswer />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
