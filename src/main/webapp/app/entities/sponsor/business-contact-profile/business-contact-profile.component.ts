import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IBusinessContactProfile } from 'app/shared/model/sponsor/business-contact-profile.model';
import { Principal } from 'app/core';
import { BusinessContactProfileService } from './business-contact-profile.service';

@Component({
  selector: 'jhi-business-contact-profile',
  templateUrl: './business-contact-profile.component.html'
})
export class BusinessContactProfileComponent implements OnInit, OnDestroy {
  businessContactProfiles: IBusinessContactProfile[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    private businessContactProfileService: BusinessContactProfileService,
    private jhiAlertService: JhiAlertService,
    private eventManager: JhiEventManager,
    private principal: Principal
  ) {}

  loadAll() {
    this.businessContactProfileService.query().subscribe(
      (res: HttpResponse<IBusinessContactProfile[]>) => {
        this.businessContactProfiles = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  ngOnInit() {
    this.loadAll();
    this.principal.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInBusinessContactProfiles();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IBusinessContactProfile) {
    return item.id;
  }

  registerChangeInBusinessContactProfiles() {
    this.eventSubscriber = this.eventManager.subscribe('businessContactProfileListModification', response => this.loadAll());
  }

  private onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
