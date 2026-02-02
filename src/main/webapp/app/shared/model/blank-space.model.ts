import { IExercise } from 'app/shared/model/exercise.model';

export interface IBlankSpace {
  id?: number;
  position?: number;
  correctAnswer?: string;
  caseSensitive?: boolean;
  orderIndex?: number;
  exercise?: IExercise;
}

export const defaultValue: Readonly<IBlankSpace> = {
  caseSensitive: false,
};
