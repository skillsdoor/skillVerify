import { ICandidateAnswer } from 'app/shared/model/candidate-answer.model';
import { IQcmOption } from 'app/shared/model/qcm-option.model';

export interface IQcmAnswer {
  id?: number;
  isCorrect?: boolean | null;
  candidateAnswer?: ICandidateAnswer;
  qcmOption?: IQcmOption;
}

export const defaultValue: Readonly<IQcmAnswer> = {
  isCorrect: false,
};
