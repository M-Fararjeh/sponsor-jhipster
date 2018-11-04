import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISponsor } from 'app/shared/model/sponsor/sponsor.model';
import { SponsorService } from './sponsor.service';

@Component({
  selector: 'jhi-sponsor-delete-dialog',
  templateUrl: './sponsor-delete-dialog.component.html'
})
export class SponsorDeleteDialogComponent {
  sponsor: ISponsor;

  constructor(private sponsorService: SponsorService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.sponsorService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'sponsorListModification',
        content: 'Deleted an sponsor'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-sponsor-delete-popup',
  template: ''
})
export class SponsorDeletePopupComponent implements OnInit, OnDestroy {
  private ngbModalRef: NgbModalRef;

  constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ sponsor }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SponsorDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.sponsor = sponsor;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
