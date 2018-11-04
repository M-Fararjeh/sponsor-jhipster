import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SponsorSharedModule } from 'app/shared';
import {
  SponsorComponent,
  SponsorDetailComponent,
  SponsorUpdateComponent,
  SponsorDeletePopupComponent,
  SponsorDeleteDialogComponent,
  sponsorRoute,
  sponsorPopupRoute
} from './';

const ENTITY_STATES = [...sponsorRoute, ...sponsorPopupRoute];

@NgModule({
  imports: [SponsorSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    SponsorComponent,
    SponsorDetailComponent,
    SponsorUpdateComponent,
    SponsorDeleteDialogComponent,
    SponsorDeletePopupComponent
  ],
  entryComponents: [SponsorComponent, SponsorUpdateComponent, SponsorDeleteDialogComponent, SponsorDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SponsorSponsorModule {}
