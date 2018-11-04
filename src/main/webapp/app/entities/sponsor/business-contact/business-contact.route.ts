import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { BusinessContact } from 'app/shared/model/sponsor/business-contact.model';
import { BusinessContactService } from './business-contact.service';
import { BusinessContactComponent } from './business-contact.component';
import { BusinessContactDetailComponent } from './business-contact-detail.component';
import { BusinessContactUpdateComponent } from './business-contact-update.component';
import { BusinessContactDeletePopupComponent } from './business-contact-delete-dialog.component';
import { IBusinessContact } from 'app/shared/model/sponsor/business-contact.model';

@Injectable({ providedIn: 'root' })
export class BusinessContactResolve implements Resolve<IBusinessContact> {
  constructor(private service: BusinessContactService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<BusinessContact> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<BusinessContact>) => response.ok),
        map((businessContact: HttpResponse<BusinessContact>) => businessContact.body)
      );
    }
    return of(new BusinessContact());
  }
}

export const businessContactRoute: Routes = [
  {
    path: 'business-contact',
    component: BusinessContactComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'sponsorApp.sponsorBusinessContact.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'business-contact/:id/view',
    component: BusinessContactDetailComponent,
    resolve: {
      businessContact: BusinessContactResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sponsorApp.sponsorBusinessContact.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'business-contact/new',
    component: BusinessContactUpdateComponent,
    resolve: {
      businessContact: BusinessContactResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sponsorApp.sponsorBusinessContact.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'business-contact/:id/edit',
    component: BusinessContactUpdateComponent,
    resolve: {
      businessContact: BusinessContactResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sponsorApp.sponsorBusinessContact.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const businessContactPopupRoute: Routes = [
  {
    path: 'business-contact/:id/delete',
    component: BusinessContactDeletePopupComponent,
    resolve: {
      businessContact: BusinessContactResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sponsorApp.sponsorBusinessContact.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
