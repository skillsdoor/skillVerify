import recruiter from 'app/entities/recruiter/recruiter.reducer';
import candidate from 'app/entities/candidate/candidate.reducer';
import trainingProvider from 'app/entities/training-provider/training-provider.reducer';
import exam from 'app/entities/exam/exam.reducer';
import exercise from 'app/entities/exercise/exercise.reducer';
import question from 'app/entities/question/question.reducer';
import media from 'app/entities/media/media.reducer';
import answerOption from 'app/entities/answer-option/answer-option.reducer';
import fillInTheBlank from 'app/entities/fill-in-the-blank/fill-in-the-blank.reducer';
import bindList from 'app/entities/bind-list/bind-list.reducer';
import examSession from 'app/entities/exam-session/exam-session.reducer';
import candidateAnswer from 'app/entities/candidate-answer/candidate-answer.reducer';
import bindListAnswer from 'app/entities/bind-list-answer/bind-list-answer.reducer';
import qcmOption from 'app/entities/qcm-option/qcm-option.reducer';
import blankSpace from 'app/entities/blank-space/blank-space.reducer';
import listItem from 'app/entities/list-item/list-item.reducer';
import qcmAnswer from 'app/entities/qcm-answer/qcm-answer.reducer';
import blankAnswer from 'app/entities/blank-answer/blank-answer.reducer';
import bindAnswer from 'app/entities/bind-answer/bind-answer.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  recruiter,
  candidate,
  trainingProvider,
  exam,
  exercise,
  question,
  media,
  answerOption,
  fillInTheBlank,
  bindList,
  examSession,
  candidateAnswer,
  bindListAnswer,
  qcmOption,
  blankSpace,
  listItem,
  qcmAnswer,
  blankAnswer,
  bindAnswer,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
