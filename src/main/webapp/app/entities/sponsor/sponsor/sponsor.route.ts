import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Sponsor } from 'app/shared/model/sponsor/sponsor.model';
import { SponsorService } from './sponsor.service';
import { SponsorComponent } from './sponsor.component';
import { SponsorDetailComponent } from './sponsor-detail.component';
import { SponsorUpdateComponent } from './sponsor-update.component';
import { SponsorDeletePopupComponent } from './sponsor-delete-dialog.component';
import { ISponsor } from 'app/shared/model/sponsor/sponsor.model';

@Injectable({ providedIn: 'root' })
export class SponsorResolve implements Resolve<ISponsor> {
  constructor(private service: SponsorService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Sponsor> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Sponsor>) => response.ok),
        map((sponsor: HttpResponse<Sponsor>) => sponsor.body)
      );
    }
    return of(new Sponsor());
  }
}

export const sponsorRoute: Routes = [
  {
    path: 'sponsor',
    component: SponsorComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sponsorApp.sponsorSponsor.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'sponsor/:id/view',
    component: SponsorDetailComponent,
    resolve: {
      sponsor: SponsorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sponsorApp.sponsorSponsor.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'sponsor/new',
    component: SponsorUpdateComponent,
    resolve: {
      sponsor: SponsorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sponsorApp.sponsorSponsor.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'sponsor/:id/edit',
    component: SponsorUpdateComponent,
    resolve: {
      sponsor: SponsorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sponsorApp.sponsorSponsor.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const sponsorPopupRoute: Routes = [
  {
    path: 'sponsor/:id/delete',
    component: SponsorDeletePopupComponent,
    resolve: {
      sponsor: SponsorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sponsorApp.sponsorSponsor.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
