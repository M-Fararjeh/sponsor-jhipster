/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SponsorTestModule } from '../../../../test.module';
import { BusinessContactDeleteDialogComponent } from 'app/entities/sponsor/business-contact/business-contact-delete-dialog.component';
import { BusinessContactService } from 'app/entities/sponsor/business-contact/business-contact.service';

describe('Component Tests', () => {
  describe('BusinessContact Management Delete Component', () => {
    let comp: BusinessContactDeleteDialogComponent;
    let fixture: ComponentFixture<BusinessContactDeleteDialogComponent>;
    let service: BusinessContactService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SponsorTestModule],
        declarations: [BusinessContactDeleteDialogComponent]
      })
        .overrideTemplate(BusinessContactDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BusinessContactDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BusinessContactService);
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
