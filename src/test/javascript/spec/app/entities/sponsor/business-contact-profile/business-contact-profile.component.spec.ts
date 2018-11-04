/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SponsorTestModule } from '../../../../test.module';
import { BusinessContactProfileComponent } from 'app/entities/sponsor/business-contact-profile/business-contact-profile.component';
import { BusinessContactProfileService } from 'app/entities/sponsor/business-contact-profile/business-contact-profile.service';
import { BusinessContactProfile } from 'app/shared/model/sponsor/business-contact-profile.model';

describe('Component Tests', () => {
  describe('BusinessContactProfile Management Component', () => {
    let comp: BusinessContactProfileComponent;
    let fixture: ComponentFixture<BusinessContactProfileComponent>;
    let service: BusinessContactProfileService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SponsorTestModule],
        declarations: [BusinessContactProfileComponent],
        providers: []
      })
        .overrideTemplate(BusinessContactProfileComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BusinessContactProfileComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BusinessContactProfileService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new BusinessContactProfile(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.businessContactProfiles[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
