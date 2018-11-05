import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBusinessActivity } from 'app/shared/model/sponsor/business-activity.model';

type EntityResponseType = HttpResponse<IBusinessActivity>;
type EntityArrayResponseType = HttpResponse<IBusinessActivity[]>;

@Injectable({ providedIn: 'root' })
export class BusinessActivityService {
  public resourceUrl = SERVER_API_URL + 'api/business-activities';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/business-activities';

  constructor(private http: HttpClient) {}

  create(businessActivity: IBusinessActivity): Observable<EntityResponseType> {
    return this.http.post<IBusinessActivity>(this.resourceUrl, businessActivity, { observe: 'response' });
  }

  update(businessActivity: IBusinessActivity): Observable<EntityResponseType> {
    return this.http.put<IBusinessActivity>(this.resourceUrl, businessActivity, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBusinessActivity>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBusinessActivity[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBusinessActivity[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
