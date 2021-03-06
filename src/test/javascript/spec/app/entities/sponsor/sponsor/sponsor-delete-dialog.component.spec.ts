/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SponsorTestModule } from '../../../../test.module';
import { SponsorDeleteDialogComponent } from 'app/entities/sponsor/sponsor/sponsor-delete-dialog.component';
import { SponsorService } from 'app/entities/sponsor/sponsor/sponsor.service';

describe('Component Tests', () => {
  describe('Sponsor Management Delete Component', () => {
    let comp: SponsorDeleteDialogComponent;
    let fixture: ComponentFixture<SponsorDeleteDialogComponent>;
    let service: SponsorService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SponsorTestModule],
        declarations: [SponsorDeleteDialogComponent]
      })
        .overrideTemplate(SponsorDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SponsorDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SponsorService);
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
