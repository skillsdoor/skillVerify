import dayjs from 'dayjs';
import { IRecruiter } from 'app/shared/model/recruiter.model';
import { ITrainingProvider } from 'app/shared/model/training-provider.model';
import { IExercise } from 'app/shared/model/exercise.model';
import { ExamStatus } from 'app/shared/model/enumerations/exam-status.model';
import { TestType } from 'app/shared/model/enumerations/test-type.model';

export interface IExam {
  id?: number;
  title?: string;
  description?: string | null;
  duration?: number;
  passingScore?: number;
  status?: keyof typeof ExamStatus;
  testType?: keyof typeof TestType;
  createdAt?: dayjs.Dayjs | null;
  updatedAt?: dayjs.Dayjs | null;
  publishedAt?: dayjs.Dayjs | null;
  recruiter?: IRecruiter | null;
  trainingProvider?: ITrainingProvider | null;
  exercises?: IExercise[];
}

export const defaultValue: Readonly<IExam> = {};
