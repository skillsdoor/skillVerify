import dayjs from 'dayjs';
import { IExamSession } from 'app/shared/model/exam-session.model';
import { IExercise } from 'app/shared/model/exercise.model';
import { IQuestion } from 'app/shared/model/question.model';

export interface ICandidateAnswer {
  id?: number;
  answerText?: string | null;
  isCorrect?: boolean | null;
  pointsAwarded?: number | null;
  answeredAt?: dayjs.Dayjs | null;
  examSession?: IExamSession;
  exercise?: IExercise;
  question?: IQuestion | null;
}

export const defaultValue: Readonly<ICandidateAnswer> = {
  isCorrect: false,
};
