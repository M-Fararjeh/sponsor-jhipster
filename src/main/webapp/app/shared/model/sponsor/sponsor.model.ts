import { IBusinessContact } from 'app/shared/model/sponsor/business-contact.model';
import { IBusinessActivity } from 'app/shared/model/sponsor/business-activity.model';

export interface ISponsor {
  id?: number;
  name?: string;
  address?: string;
  city?: string;
  region?: string;
  postalCode?: string;
  country?: string;
  phone?: string;
  fax?: string;
  homePage?: string;
  businessContacts?: IBusinessContact[];
  businessActivities?: IBusinessActivity[];
}

export class Sponsor implements ISponsor {
  constructor(
    public id?: number,
    public name?: string,
    public address?: string,
    public city?: string,
    public region?: string,
    public postalCode?: string,
    public country?: string,
    public phone?: string,
    public fax?: string,
    public homePage?: string,
    public businessContacts?: IBusinessContact[],
    public businessActivities?: IBusinessActivity[]
  ) {}
}
