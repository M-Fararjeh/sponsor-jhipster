export interface IBusinessContactProfile {
  id?: number;
  attending?: string;
  retention?: string;
  customerService?: string;
  customerServiceSpecial?: string;
}

export class BusinessContactProfile implements IBusinessContactProfile {
  constructor(
    public id?: number,
    public attending?: string,
    public retention?: string,
    public customerService?: string,
    public customerServiceSpecial?: string
  ) {}
}
