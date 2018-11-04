/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SponsorTestModule } from '../../../../test.module';
import { BusinessActivityDeleteDialogComponent } from 'app/entities/sponsor/business-activity/business-activity-delete-dialog.component';
import { BusinessActivityService } from 'app/entities/sponsor/business-activity/business-activity.service';

describe('Component Tests', () => {
  describe('BusinessActivity Management Delete Component', () => {
    let comp: BusinessActivityDeleteDialogComponent;
    let fixture: ComponentFixture<BusinessActivityDeleteDialogComponent>;
    let service: BusinessActivityService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SponsorTestModule],
        declarations: [BusinessActivityDeleteDialogComponent]
      })
        .overrideTemplate(BusinessActivityDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BusinessActivityDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BusinessActivityService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
