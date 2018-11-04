import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SponsorSharedModule } from 'app/shared';
import {
  BusinessContactProfileComponent,
  BusinessContactProfileDetailComponent,
  BusinessContactProfileUpdateComponent,
  BusinessContactProfileDeletePopupComponent,
  BusinessContactProfileDeleteDialogComponent,
  businessContactProfileRoute,
  businessContactProfilePopupRoute
} from './';

const ENTITY_STATES = [...businessContactProfileRoute, ...businessContactProfilePopupRoute];

@NgModule({
  imports: [SponsorSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    BusinessContactProfileComponent,
    BusinessContactProfileDetailComponent,
    BusinessContactProfileUpdateComponent,
    BusinessContactProfileDeleteDialogComponent,
    BusinessContactProfileDeletePopupComponent
  ],
  entryComponents: [
    BusinessContactProfileComponent,
    BusinessContactProfileUpdateComponent,
    BusinessContactProfileDeleteDialogComponent,
    BusinessContactProfileDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SponsorBusinessContactProfileModule {}
