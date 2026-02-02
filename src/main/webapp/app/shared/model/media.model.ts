import dayjs from 'dayjs';
import { IExercise } from 'app/shared/model/exercise.model';
import { IQuestion } from 'app/shared/model/question.model';
import { MediaType } from 'app/shared/model/enumerations/media-type.model';

export interface IMedia {
  id?: number;
  type?: keyof typeof MediaType;
  s3Key?: string;
  s3Url?: string;
  fileName?: string;
  fileSize?: number | null;
  mimeType?: string | null;
  uploadedAt?: dayjs.Dayjs | null;
  exercise?: IExercise | null;
  question?: IQuestion | null;
}

export const defaultValue: Readonly<IMedia> = {};
