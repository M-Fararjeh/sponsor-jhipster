import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SponsorSharedModule } from 'app/shared';
import {
  BusinessContactComponent,
  BusinessContactDetailComponent,
  BusinessContactUpdateComponent,
  BusinessContactDeletePopupComponent,
  BusinessContactDeleteDialogComponent,
  businessContactRoute,
  businessContactPopupRoute
} from './';

const ENTITY_STATES = [...businessContactRoute, ...businessContactPopupRoute];

@NgModule({
  imports: [SponsorSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    BusinessContactComponent,
    BusinessContactDetailComponent,
    BusinessContactUpdateComponent,
    BusinessContactDeleteDialogComponent,
    BusinessContactDeletePopupComponent
  ],
  entryComponents: [
    BusinessContactComponent,
    BusinessContactUpdateComponent,
    BusinessContactDeleteDialogComponent,
    BusinessContactDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SponsorBusinessContactModule {}
