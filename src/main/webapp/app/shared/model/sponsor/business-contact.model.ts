export interface IBusinessContact {
  id?: number;
  firstName?: string;
  lastName?: string;
  personalPhone?: string;
  workPhone?: string;
  email?: string;
  sponsorId?: number;
  profileId?: number;
}

export class BusinessContact implements IBusinessContact {
  constructor(
    public id?: number,
    public firstName?: string,
    public lastName?: string,
    public personalPhone?: string,
    public workPhone?: string,
    public email?: string,
    public sponsorId?: number,
    public profileId?: number
  ) {}
}
