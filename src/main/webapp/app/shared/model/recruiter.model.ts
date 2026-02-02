import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';

export interface IRecruiter {
  id?: number;
  firstName?: string;
  lastName?: string;
  phone?: string | null;
  bio?: string | null;
  avatarUrl?: string | null;
  company?: string;
  position?: string | null;
  website?: string | null;
  createdAt?: dayjs.Dayjs | null;
  updatedAt?: dayjs.Dayjs | null;
  user?: IUser;
}

export const defaultValue: Readonly<IRecruiter> = {};
