import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IBusinessContact } from 'app/shared/model/sponsor/business-contact.model';
import { BusinessContactService } from './business-contact.service';
import { ISponsor } from 'app/shared/model/sponsor/sponsor.model';
import { SponsorService } from 'app/entities/sponsor/sponsor';
import { IBusinessContactProfile } from 'app/shared/model/sponsor/business-contact-profile.model';
import { BusinessContactProfileService } from 'app/entities/sponsor/business-contact-profile';

@Component({
  selector: 'jhi-business-contact-update',
  templateUrl: './business-contact-update.component.html'
})
export class BusinessContactUpdateComponent implements OnInit {
  businessContact: IBusinessContact;
  isSaving: boolean;

  sponsors: ISponsor[];

  profiles: IBusinessContactProfile[];

  constructor(
    private jhiAlertService: JhiAlertService,
    private businessContactService: BusinessContactService,
    private sponsorService: SponsorService,
    private businessContactProfileService: BusinessContactProfileService,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ businessContact }) => {
      this.businessContact = businessContact;
    });
    this.sponsorService.query().subscribe(
      (res: HttpResponse<ISponsor[]>) => {
        this.sponsors = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.businessContactProfileService.query({ filter: 'businesscontact-is-null' }).subscribe(
      (res: HttpResponse<IBusinessContactProfile[]>) => {
        if (!this.businessContact.profile || !this.businessContact.profile.id) {
          this.profiles = res.body;
        } else {
          this.businessContactProfileService.find(this.businessContact.profile.id).subscribe(
            (subRes: HttpResponse<IBusinessContactProfile>) => {
              this.profiles = [subRes.body].concat(res.body);
            },
            (subRes: HttpErrorResponse) => this.onError(subRes.message)
          );
        }
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    if (this.businessContact.id !== undefined) {
      this.subscribeToSaveResponse(this.businessContactService.update(this.businessContact));
    } else {
      this.subscribeToSaveResponse(this.businessContactService.create(this.businessContact));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IBusinessContact>>) {
    result.subscribe((res: HttpResponse<IBusinessContact>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  private onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError() {
    this.isSaving = false;
  }

  private onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackSponsorById(index: number, item: ISponsor) {
    return item.id;
  }

  trackBusinessContactProfileById(index: number, item: IBusinessContactProfile) {
    return item.id;
  }
}
