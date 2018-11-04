/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SponsorTestModule } from '../../../../test.module';
import { BusinessContactUpdateComponent } from 'app/entities/sponsor/business-contact/business-contact-update.component';
import { BusinessContactService } from 'app/entities/sponsor/business-contact/business-contact.service';
import { BusinessContact } from 'app/shared/model/sponsor/business-contact.model';

describe('Component Tests', () => {
  describe('BusinessContact Management Update Component', () => {
    let comp: BusinessContactUpdateComponent;
    let fixture: ComponentFixture<BusinessContactUpdateComponent>;
    let service: BusinessContactService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SponsorTestModule],
        declarations: [BusinessContactUpdateComponent]
      })
        .overrideTemplate(BusinessContactUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BusinessContactUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BusinessContactService);
    });

    describe('save', () => {
      it(
        'Should call update service on save for existing entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new BusinessContact(123);
          spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.businessContact = entity;
          // WHEN
          comp.save();
          tick(); // simulate async

          // THEN
          expect(service.update).toHaveBeenCalledWith(entity);
          expect(comp.isSaving).toEqual(false);
        })
      );

      it(
        'Should call create service on save for new entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new BusinessContact();
          spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.businessContact = entity;
          // WHEN
          comp.save();
          tick(); // simulate async

          // THEN
          expect(service.create).toHaveBeenCalledWith(entity);
          expect(comp.isSaving).toEqual(false);
        })
      );
    });
  });
});
