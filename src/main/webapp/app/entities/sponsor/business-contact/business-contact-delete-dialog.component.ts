import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBusinessContact } from 'app/shared/model/sponsor/business-contact.model';
import { BusinessContactService } from './business-contact.service';

@Component({
  selector: 'jhi-business-contact-delete-dialog',
  templateUrl: './business-contact-delete-dialog.component.html'
})
export class BusinessContactDeleteDialogComponent {
  businessContact: IBusinessContact;

  constructor(
    private businessContactService: BusinessContactService,
    public activeModal: NgbActiveModal,
    private eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.businessContactService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'businessContactListModification',
        content: 'Deleted an businessContact'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-business-contact-delete-popup',
  template: ''
})
export class BusinessContactDeletePopupComponent implements OnInit, OnDestroy {
  private ngbModalRef: NgbModalRef;

  constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ businessContact }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(BusinessContactDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.businessContact = businessContact;
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
