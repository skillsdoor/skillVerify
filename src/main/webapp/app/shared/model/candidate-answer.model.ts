import dayjs from 'dayjs';
import { IExamSession } from 'app/shared/model/exam-session.model';
import { IQuestion } from 'app/shared/model/question.model';

export interface ICandidateAnswer {
  id?: number;
  answerText?: string | null;
  selectedOptionId?: number | null;
  isCorrect?: boolean | null;
  pointsAwarded?: number | null;
  answeredAt?: dayjs.Dayjs | null;
  examSession?: IExamSession;
  question?: IQuestion;
}

export const defaultValue: Readonly<ICandidateAnswer> = {
  isCorrect: false,
};
