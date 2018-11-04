import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { BusinessActivity } from 'app/shared/model/sponsor/business-activity.model';
import { BusinessActivityService } from './business-activity.service';
import { BusinessActivityComponent } from './business-activity.component';
import { BusinessActivityDetailComponent } from './business-activity-detail.component';
import { BusinessActivityUpdateComponent } from './business-activity-update.component';
import { BusinessActivityDeletePopupComponent } from './business-activity-delete-dialog.component';
import { IBusinessActivity } from 'app/shared/model/sponsor/business-activity.model';

@Injectable({ providedIn: 'root' })
export class BusinessActivityResolve implements Resolve<IBusinessActivity> {
  constructor(private service: BusinessActivityService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<BusinessActivity> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<BusinessActivity>) => response.ok),
        map((businessActivity: HttpResponse<BusinessActivity>) => businessActivity.body)
      );
    }
    return of(new BusinessActivity());
  }
}

export const businessActivityRoute: Routes = [
  {
    path: 'business-activity',
    component: BusinessActivityComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sponsorApp.sponsorBusinessActivity.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'business-activity/:id/view',
    component: BusinessActivityDetailComponent,
    resolve: {
      businessActivity: BusinessActivityResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sponsorApp.sponsorBusinessActivity.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'business-activity/new',
    component: BusinessActivityUpdateComponent,
    resolve: {
      businessActivity: BusinessActivityResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sponsorApp.sponsorBusinessActivity.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'business-activity/:id/edit',
    component: BusinessActivityUpdateComponent,
    resolve: {
      businessActivity: BusinessActivityResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sponsorApp.sponsorBusinessActivity.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const businessActivityPopupRoute: Routes = [
  {
    path: 'business-activity/:id/delete',
    component: BusinessActivityDeletePopupComponent,
    resolve: {
      businessActivity: BusinessActivityResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sponsorApp.sponsorBusinessActivity.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
