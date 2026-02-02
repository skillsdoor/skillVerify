import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';

export interface ITrainingProvider {
  id?: number;
  firstName?: string;
  lastName?: string;
  phone?: string | null;
  bio?: string | null;
  avatarUrl?: string | null;
  organizationName?: string;
  website?: string | null;
  accreditation?: string | null;
  createdAt?: dayjs.Dayjs | null;
  updatedAt?: dayjs.Dayjs | null;
  user?: IUser;
}

export const defaultValue: Readonly<ITrainingProvider> = {};
