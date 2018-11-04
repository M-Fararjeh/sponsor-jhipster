import { ISponsor } from 'app/shared/model/sponsor/sponsor.model';
import { IBusinessContactProfile } from 'app/shared/model/sponsor/business-contact-profile.model';

export interface IBusinessContact {
  id?: number;
  firstName?: string;
  lastName?: string;
  personalPhone?: string;
  workPhone?: string;
  email?: string;
  sponsor?: ISponsor;
  profile?: IBusinessContactProfile;
}

export class BusinessContact implements IBusinessContact {
  constructor(
    public id?: number,
    public firstName?: string,
    public lastName?: string,
    public personalPhone?: string,
    public workPhone?: string,
    public email?: string,
    public sponsor?: ISponsor,
    public profile?: IBusinessContactProfile
  ) {}
}
