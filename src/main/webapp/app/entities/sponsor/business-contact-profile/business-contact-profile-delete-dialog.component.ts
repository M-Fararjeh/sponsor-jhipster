import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBusinessContactProfile } from 'app/shared/model/sponsor/business-contact-profile.model';
import { BusinessContactProfileService } from './business-contact-profile.service';

@Component({
  selector: 'jhi-business-contact-profile-delete-dialog',
  templateUrl: './business-contact-profile-delete-dialog.component.html'
})
export class BusinessContactProfileDeleteDialogComponent {
  businessContactProfile: IBusinessContactProfile;

  constructor(
    private businessContactProfileService: BusinessContactProfileService,
    public activeModal: NgbActiveModal,
    private eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.businessContactProfileService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'businessContactProfileListModification',
        content: 'Deleted an businessContactProfile'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-business-contact-profile-delete-popup',
  template: ''
})
export class BusinessContactProfileDeletePopupComponent implements OnInit, OnDestroy {
  private ngbModalRef: NgbModalRef;

  constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ businessContactProfile }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(BusinessContactProfileDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.businessContactProfile = businessContactProfile;
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
