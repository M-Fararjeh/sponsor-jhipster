/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SponsorTestModule } from '../../../../test.module';
import { BusinessContactProfileDeleteDialogComponent } from 'app/entities/sponsor/business-contact-profile/business-contact-profile-delete-dialog.component';
import { BusinessContactProfileService } from 'app/entities/sponsor/business-contact-profile/business-contact-profile.service';

describe('Component Tests', () => {
  describe('BusinessContactProfile Management Delete Component', () => {
    let comp: BusinessContactProfileDeleteDialogComponent;
    let fixture: ComponentFixture<BusinessContactProfileDeleteDialogComponent>;
    let service: BusinessContactProfileService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SponsorTestModule],
        declarations: [BusinessContactProfileDeleteDialogComponent]
      })
        .overrideTemplate(BusinessContactProfileDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BusinessContactProfileDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BusinessContactProfileService);
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
