import dayjs from 'dayjs';
import { ICandidate } from 'app/shared/model/candidate.model';
import { IExam } from 'app/shared/model/exam.model';
import { SubmissionStatus } from 'app/shared/model/enumerations/submission-status.model';

export interface IExamSession {
  id?: number;
  startedAt?: dayjs.Dayjs;
  submittedAt?: dayjs.Dayjs | null;
  status?: keyof typeof SubmissionStatus;
  score?: number | null;
  totalPoints?: number | null;
  timeSpent?: number | null;
  candidate?: ICandidate;
  exam?: IExam;
}

export const defaultValue: Readonly<IExamSession> = {};
