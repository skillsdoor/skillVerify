import { IQuestion } from 'app/shared/model/question.model';

export interface IFillInTheBlank {
  id?: number;
  textWithBlanks?: string;
  blankIndex?: number;
  correctAnswer?: string;
  caseSensitive?: boolean;
  question?: IQuestion;
}

export const defaultValue: Readonly<IFillInTheBlank> = {
  caseSensitive: false,
};
