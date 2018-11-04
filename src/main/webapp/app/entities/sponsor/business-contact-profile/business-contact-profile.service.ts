import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBusinessContactProfile } from 'app/shared/model/sponsor/business-contact-profile.model';

type EntityResponseType = HttpResponse<IBusinessContactProfile>;
type EntityArrayResponseType = HttpResponse<IBusinessContactProfile[]>;

@Injectable({ providedIn: 'root' })
export class BusinessContactProfileService {
  public resourceUrl = SERVER_API_URL + 'api/business-contact-profiles';

  constructor(private http: HttpClient) {}

  create(businessContactProfile: IBusinessContactProfile): Observable<EntityResponseType> {
    return this.http.post<IBusinessContactProfile>(this.resourceUrl, businessContactProfile, { observe: 'response' });
  }

  update(businessContactProfile: IBusinessContactProfile): Observable<EntityResponseType> {
    return this.http.put<IBusinessContactProfile>(this.resourceUrl, businessContactProfile, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBusinessContactProfile>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBusinessContactProfile[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
