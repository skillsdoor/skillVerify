import { IExercise } from 'app/shared/model/exercise.model';

export interface IQuestion {
  id?: number;
  content?: string;
  explanation?: string | null;
  points?: number;
  orderIndex?: number;
  exercise?: IExercise;
}

export const defaultValue: Readonly<IQuestion> = {};
