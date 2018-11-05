import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { ISponsor } from 'app/shared/model/sponsor/sponsor.model';
import { Principal } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { SponsorService } from './sponsor.service';

@Component({
  selector: 'jhi-sponsor',
  templateUrl: './sponsor.component.html'
})
export class SponsorComponent implements OnInit, OnDestroy {
  sponsors: ISponsor[];
  currentAccount: any;
  eventSubscriber: Subscription;
  itemsPerPage: number;
  links: any;
  page: any;
  predicate: any;
  queryCount: any;
  reverse: any;
  totalItems: number;
  currentSearch: string;

  constructor(
    private sponsorService: SponsorService,
    private jhiAlertService: JhiAlertService,
    private eventManager: JhiEventManager,
    private parseLinks: JhiParseLinks,
    private activatedRoute: ActivatedRoute,
    private principal: Principal
  ) {
    this.sponsors = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.reverse = true;
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search'] ? this.activatedRoute.snapshot.params['search'] : '';
  }

  loadAll() {
    if (this.currentSearch) {
      this.sponsorService
        .search({
          query: this.currentSearch,
          page: this.page,
          size: this.itemsPerPage,
          sort: this.sort()
        })
        .subscribe(
          (res: HttpResponse<ISponsor[]>) => this.paginateSponsors(res.body, res.headers),
          (res: HttpErrorResponse) => this.onError(res.message)
        );
      return;
    }
    this.sponsorService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<ISponsor[]>) => this.paginateSponsors(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  reset() {
    this.page = 0;
    this.sponsors = [];
    this.loadAll();
  }

  loadPage(page) {
    this.page = page;
    this.loadAll();
  }

  clear() {
    this.sponsors = [];
    this.links = {
      last: 0
    };
    this.page = 0;
    this.predicate = 'id';
    this.reverse = true;
    this.currentSearch = '';
    this.loadAll();
  }

  search(query) {
    if (!query) {
      return this.clear();
    }
    this.sponsors = [];
    this.links = {
      last: 0
    };
    this.page = 0;
    this.predicate = '_score';
    this.reverse = false;
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.principal.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInSponsors();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ISponsor) {
    return item.id;
  }

  registerChangeInSponsors() {
    this.eventSubscriber = this.eventManager.subscribe('sponsorListModification', response => this.reset());
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  private paginateSponsors(data: ISponsor[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    for (let i = 0; i < data.length; i++) {
      this.sponsors.push(data[i]);
    }
  }

  private onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
