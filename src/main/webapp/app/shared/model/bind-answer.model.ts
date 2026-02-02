import { ICandidateAnswer } from 'app/shared/model/candidate-answer.model';

export interface IBindAnswer {
  id?: number;
  leftItemId?: number;
  rightItemId?: number;
  isCorrect?: boolean | null;
  candidateAnswer?: ICandidateAnswer;
}

export const defaultValue: Readonly<IBindAnswer> = {
  isCorrect: false,
};
