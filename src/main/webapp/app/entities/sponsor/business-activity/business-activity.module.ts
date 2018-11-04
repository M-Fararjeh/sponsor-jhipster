import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SponsorSharedModule } from 'app/shared';
import {
  BusinessActivityComponent,
  BusinessActivityDetailComponent,
  BusinessActivityUpdateComponent,
  BusinessActivityDeletePopupComponent,
  BusinessActivityDeleteDialogComponent,
  businessActivityRoute,
  businessActivityPopupRoute
} from './';

const ENTITY_STATES = [...businessActivityRoute, ...businessActivityPopupRoute];

@NgModule({
  imports: [SponsorSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    BusinessActivityComponent,
    BusinessActivityDetailComponent,
    BusinessActivityUpdateComponent,
    BusinessActivityDeleteDialogComponent,
    BusinessActivityDeletePopupComponent
  ],
  entryComponents: [
    BusinessActivityComponent,
    BusinessActivityUpdateComponent,
    BusinessActivityDeleteDialogComponent,
    BusinessActivityDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SponsorBusinessActivityModule {}
