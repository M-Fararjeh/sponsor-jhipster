import { ISponsor } from 'app/shared/model/sponsor/sponsor.model';

export interface IBusinessActivity {
  id?: number;
  activityName?: string;
  sponsors?: ISponsor[];
}

export class BusinessActivity implements IBusinessActivity {
  constructor(public id?: number, public activityName?: string, public sponsors?: ISponsor[]) {}
}
