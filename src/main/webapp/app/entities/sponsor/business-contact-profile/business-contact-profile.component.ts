import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
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
  currentSearch: string;

  constructor(
    private businessContactProfileService: BusinessContactProfileService,
    private jhiAlertService: JhiAlertService,
    private eventManager: JhiEventManager,
    private activatedRoute: ActivatedRoute,
    private principal: Principal
  ) {
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search'] ? this.activatedRoute.snapshot.params['search'] : '';
  }

  loadAll() {
    if (this.currentSearch) {
      this.businessContactProfileService
        .search({
          query: this.currentSearch
        })
        .subscribe(
          (res: HttpResponse<IBusinessContactProfile[]>) => (this.businessContactProfiles = res.body),
          (res: HttpErrorResponse) => this.onError(res.message)
        );
      return;
    }
    this.businessContactProfileService.query().subscribe(
      (res: HttpResponse<IBusinessContactProfile[]>) => {
        this.businessContactProfiles = res.body;
        this.currentSearch = '';
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  search(query) {
    if (!query) {
      return this.clear();
    }
    this.currentSearch = query;
    this.loadAll();
  }

  clear() {
    this.currentSearch = '';
    this.loadAll();
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
