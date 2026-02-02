import { IExercise } from 'app/shared/model/exercise.model';

export interface IListItem {
  id?: number;
  content?: string;
  matchingContent?: string;
  isLeftSide?: boolean;
  orderIndex?: number;
  exercise?: IExercise;
}

export const defaultValue: Readonly<IListItem> = {
  isLeftSide: false,
};
