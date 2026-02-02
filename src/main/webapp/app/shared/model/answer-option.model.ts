import { IQuestion } from 'app/shared/model/question.model';

export interface IAnswerOption {
  id?: number;
  content?: string;
  isCorrect?: boolean;
  orderIndex?: number;
  question?: IQuestion;
}

export const defaultValue: Readonly<IAnswerOption> = {
  isCorrect: false,
};
