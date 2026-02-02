import { ICandidateAnswer } from 'app/shared/model/candidate-answer.model';
import { IBlankSpace } from 'app/shared/model/blank-space.model';

export interface IBlankAnswer {
  id?: number;
  answerText?: string;
  isCorrect?: boolean | null;
  candidateAnswer?: ICandidateAnswer;
  blankSpace?: IBlankSpace;
}

export const defaultValue: Readonly<IBlankAnswer> = {
  isCorrect: false,
};
