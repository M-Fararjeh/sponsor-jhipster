import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { BusinessContactProfile } from 'app/shared/model/sponsor/business-contact-profile.model';
import { BusinessContactProfileService } from './business-contact-profile.service';
import { BusinessContactProfileComponent } from './business-contact-profile.component';
import { BusinessContactProfileDetailComponent } from './business-contact-profile-detail.component';
import { BusinessContactProfileUpdateComponent } from './business-contact-profile-update.component';
import { BusinessContactProfileDeletePopupComponent } from './business-contact-profile-delete-dialog.component';
import { IBusinessContactProfile } from 'app/shared/model/sponsor/business-contact-profile.model';

@Injectable({ providedIn: 'root' })
export class BusinessContactProfileResolve implements Resolve<IBusinessContactProfile> {
  constructor(private service: BusinessContactProfileService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<BusinessContactProfile> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<BusinessContactProfile>) => response.ok),
        map((businessContactProfile: HttpResponse<BusinessContactProfile>) => businessContactProfile.body)
      );
    }
    return of(new BusinessContactProfile());
  }
}

export const businessContactProfileRoute: Routes = [
  {
    path: 'business-contact-profile',
    component: BusinessContactProfileComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sponsorApp.sponsorBusinessContactProfile.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'business-contact-profile/:id/view',
    component: BusinessContactProfileDetailComponent,
    resolve: {
      businessContactProfile: BusinessContactProfileResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sponsorApp.sponsorBusinessContactProfile.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'business-contact-profile/new',
    component: BusinessContactProfileUpdateComponent,
    resolve: {
      businessContactProfile: BusinessContactProfileResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sponsorApp.sponsorBusinessContactProfile.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'business-contact-profile/:id/edit',
    component: BusinessContactProfileUpdateComponent,
    resolve: {
      businessContactProfile: BusinessContactProfileResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sponsorApp.sponsorBusinessContactProfile.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const businessContactProfilePopupRoute: Routes = [
  {
    path: 'business-contact-profile/:id/delete',
    component: BusinessContactProfileDeletePopupComponent,
    resolve: {
      businessContactProfile: BusinessContactProfileResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sponsorApp.sponsorBusinessContactProfile.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
