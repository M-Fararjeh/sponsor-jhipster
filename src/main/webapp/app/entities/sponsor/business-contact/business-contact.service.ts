import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBusinessContact } from 'app/shared/model/sponsor/business-contact.model';

type EntityResponseType = HttpResponse<IBusinessContact>;
type EntityArrayResponseType = HttpResponse<IBusinessContact[]>;

@Injectable({ providedIn: 'root' })
export class BusinessContactService {
  public resourceUrl = SERVER_API_URL + 'api/business-contacts';

  constructor(private http: HttpClient) {}

  create(businessContact: IBusinessContact): Observable<EntityResponseType> {
    return this.http.post<IBusinessContact>(this.resourceUrl, businessContact, { observe: 'response' });
  }

  update(businessContact: IBusinessContact): Observable<EntityResponseType> {
    return this.http.put<IBusinessContact>(this.resourceUrl, businessContact, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBusinessContact>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBusinessContact[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
