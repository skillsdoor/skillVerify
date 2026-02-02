import { IQuestion } from 'app/shared/model/question.model';

export interface IBindList {
  id?: number;
  leftItem?: string;
  rightItem?: string;
  orderIndex?: number;
  question?: IQuestion;
}

export const defaultValue: Readonly<IBindList> = {};
