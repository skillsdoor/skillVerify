import { ICandidateAnswer } from 'app/shared/model/candidate-answer.model';

export interface IBindListAnswer {
  id?: number;
  leftItemId?: number;
  rightItemId?: number;
  isCorrect?: boolean | null;
  candidateAnswer?: ICandidateAnswer;
}

export const defaultValue: Readonly<IBindListAnswer> = {
  isCorrect: false,
};
