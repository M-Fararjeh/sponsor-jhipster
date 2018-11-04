import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IBusinessActivity } from 'app/shared/model/sponsor/business-activity.model';
import { BusinessActivityService } from './business-activity.service';
import { ISponsor } from 'app/shared/model/sponsor/sponsor.model';
import { SponsorService } from 'app/entities/sponsor/sponsor';

@Component({
  selector: 'jhi-business-activity-update',
  templateUrl: './business-activity-update.component.html'
})
export class BusinessActivityUpdateComponent implements OnInit {
  businessActivity: IBusinessActivity;
  isSaving: boolean;

  sponsors: ISponsor[];

  constructor(
    private jhiAlertService: JhiAlertService,
    private businessActivityService: BusinessActivityService,
    private sponsorService: SponsorService,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ businessActivity }) => {
      this.businessActivity = businessActivity;
    });
    this.sponsorService.query().subscribe(
      (res: HttpResponse<ISponsor[]>) => {
        this.sponsors = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    if (this.businessActivity.id !== undefined) {
      this.subscribeToSaveResponse(this.businessActivityService.update(this.businessActivity));
    } else {
      this.subscribeToSaveResponse(this.businessActivityService.create(this.businessActivity));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IBusinessActivity>>) {
    result.subscribe((res: HttpResponse<IBusinessActivity>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  getSelected(selectedVals: Array<any>, option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
