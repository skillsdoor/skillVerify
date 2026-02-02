import { IExam } from 'app/shared/model/exam.model';
import { IQcmOption } from 'app/shared/model/qcm-option.model';
import { IBlankSpace } from 'app/shared/model/blank-space.model';
import { IListItem } from 'app/shared/model/list-item.model';
import { ExerciseType } from 'app/shared/model/enumerations/exercise-type.model';

export interface IExercise {
  id?: number;
  type?: keyof typeof ExerciseType;
  title?: string;
  instruction?: string | null;
  content?: string | null;
  explanation?: string | null;
  points?: number;
  orderIndex?: number;
  exam?: IExam;
  qcmOptions?: IQcmOption[];
  blankSpaces?: IBlankSpace[];
  listItems?: IListItem[];
}

export const defaultValue: Readonly<IExercise> = {};
