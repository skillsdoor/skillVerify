import { IExercise } from 'app/shared/model/exercise.model';

export interface IQcmOption {
  id?: number;
  content?: string;
  isCorrect?: boolean;
  orderIndex?: number;
  exercise?: IExercise;
}

export const defaultValue: Readonly<IQcmOption> = {
  isCorrect: false,
};
