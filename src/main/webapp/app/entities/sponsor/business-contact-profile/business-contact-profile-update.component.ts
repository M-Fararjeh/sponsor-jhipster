import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IBusinessContactProfile } from 'app/shared/model/sponsor/business-contact-profile.model';
import { BusinessContactProfileService } from './business-contact-profile.service';

@Component({
  selector: 'jhi-business-contact-profile-update',
  templateUrl: './business-contact-profile-update.component.html'
})
export class BusinessContactProfileUpdateComponent implements OnInit {
  businessContactProfile: IBusinessContactProfile;
  isSaving: boolean;

  constructor(private businessContactProfileService: BusinessContactProfileService, private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ businessContactProfile }) => {
      this.businessContactProfile = businessContactProfile;
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    if (this.businessContactProfile.id !== undefined) {
      this.subscribeToSaveResponse(this.businessContactProfileService.update(this.businessContactProfile));
    } else {
      this.subscribeToSaveResponse(this.businessContactProfileService.create(this.businessContactProfile));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IBusinessContactProfile>>) {
    result.subscribe((res: HttpResponse<IBusinessContactProfile>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  private onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError() {
    this.isSaving = false;
  }
}
