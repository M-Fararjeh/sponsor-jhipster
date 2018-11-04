import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBusinessActivity } from 'app/shared/model/sponsor/business-activity.model';
import { BusinessActivityService } from './business-activity.service';

@Component({
  selector: 'jhi-business-activity-delete-dialog',
  templateUrl: './business-activity-delete-dialog.component.html'
})
export class BusinessActivityDeleteDialogComponent {
  businessActivity: IBusinessActivity;

  constructor(
    private businessActivityService: BusinessActivityService,
    public activeModal: NgbActiveModal,
    private eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.businessActivityService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'businessActivityListModification',
        content: 'Deleted an businessActivity'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-business-activity-delete-popup',
  template: ''
})
export class BusinessActivityDeletePopupComponent implements OnInit, OnDestroy {
  private ngbModalRef: NgbModalRef;

  constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ businessActivity }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(BusinessActivityDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.businessActivity = businessActivity;
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
