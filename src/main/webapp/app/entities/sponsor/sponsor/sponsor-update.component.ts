import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ISponsor } from 'app/shared/model/sponsor/sponsor.model';
import { SponsorService } from './sponsor.service';
import { IBusinessActivity } from 'app/shared/model/sponsor/business-activity.model';
import { BusinessActivityService } from 'app/entities/sponsor/business-activity';

@Component({
  selector: 'jhi-sponsor-update',
  templateUrl: './sponsor-update.component.html'
})
export class SponsorUpdateComponent implements OnInit {
  sponsor: ISponsor;
  isSaving: boolean;

  businessactivities: IBusinessActivity[];

  constructor(
    private jhiAlertService: JhiAlertService,
    private sponsorService: SponsorService,
    private businessActivityService: BusinessActivityService,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ sponsor }) => {
      this.sponsor = sponsor;
    });
    this.businessActivityService.query().subscribe(
      (res: HttpResponse<IBusinessActivity[]>) => {
        this.businessactivities = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    if (this.sponsor.id !== undefined) {
      this.subscribeToSaveResponse(this.sponsorService.update(this.sponsor));
    } else {
      this.subscribeToSaveResponse(this.sponsorService.create(this.sponsor));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<ISponsor>>) {
    result.subscribe((res: HttpResponse<ISponsor>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackBusinessActivityById(index: number, item: IBusinessActivity) {
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
