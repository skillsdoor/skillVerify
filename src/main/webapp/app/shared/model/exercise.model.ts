import { IExam } from 'app/shared/model/exam.model';
import { ExerciseType } from 'app/shared/model/enumerations/exercise-type.model';

export interface IExercise {
  id?: number;
  type?: keyof typeof ExerciseType;
  title?: string;
  instruction?: string | null;
  points?: number;
  orderIndex?: number;
  exam?: IExam;
}

export const defaultValue: Readonly<IExercise> = {};
