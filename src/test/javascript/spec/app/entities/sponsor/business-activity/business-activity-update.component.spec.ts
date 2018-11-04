/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SponsorTestModule } from '../../../../test.module';
import { BusinessActivityUpdateComponent } from 'app/entities/sponsor/business-activity/business-activity-update.component';
import { BusinessActivityService } from 'app/entities/sponsor/business-activity/business-activity.service';
import { BusinessActivity } from 'app/shared/model/sponsor/business-activity.model';

describe('Component Tests', () => {
  describe('BusinessActivity Management Update Component', () => {
    let comp: BusinessActivityUpdateComponent;
    let fixture: ComponentFixture<BusinessActivityUpdateComponent>;
    let service: BusinessActivityService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SponsorTestModule],
        declarations: [BusinessActivityUpdateComponent]
      })
        .overrideTemplate(BusinessActivityUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BusinessActivityUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BusinessActivityService);
    });

    describe('save', () => {
      it(
        'Should call update service on save for existing entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new BusinessActivity(123);
          spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.businessActivity = entity;
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
          const entity = new BusinessActivity();
          spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.businessActivity = entity;
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
