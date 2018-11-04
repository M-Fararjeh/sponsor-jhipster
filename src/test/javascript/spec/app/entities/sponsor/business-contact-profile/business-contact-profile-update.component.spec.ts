/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SponsorTestModule } from '../../../../test.module';
import { BusinessContactProfileUpdateComponent } from 'app/entities/sponsor/business-contact-profile/business-contact-profile-update.component';
import { BusinessContactProfileService } from 'app/entities/sponsor/business-contact-profile/business-contact-profile.service';
import { BusinessContactProfile } from 'app/shared/model/sponsor/business-contact-profile.model';

describe('Component Tests', () => {
  describe('BusinessContactProfile Management Update Component', () => {
    let comp: BusinessContactProfileUpdateComponent;
    let fixture: ComponentFixture<BusinessContactProfileUpdateComponent>;
    let service: BusinessContactProfileService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SponsorTestModule],
        declarations: [BusinessContactProfileUpdateComponent]
      })
        .overrideTemplate(BusinessContactProfileUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BusinessContactProfileUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BusinessContactProfileService);
    });

    describe('save', () => {
      it(
        'Should call update service on save for existing entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new BusinessContactProfile(123);
          spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.businessContactProfile = entity;
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
          const entity = new BusinessContactProfile();
          spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.businessContactProfile = entity;
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
