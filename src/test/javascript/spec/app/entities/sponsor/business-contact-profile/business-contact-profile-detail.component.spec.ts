/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SponsorTestModule } from '../../../../test.module';
import { BusinessContactProfileDetailComponent } from 'app/entities/sponsor/business-contact-profile/business-contact-profile-detail.component';
import { BusinessContactProfile } from 'app/shared/model/sponsor/business-contact-profile.model';

describe('Component Tests', () => {
  describe('BusinessContactProfile Management Detail Component', () => {
    let comp: BusinessContactProfileDetailComponent;
    let fixture: ComponentFixture<BusinessContactProfileDetailComponent>;
    const route = ({ data: of({ businessContactProfile: new BusinessContactProfile(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SponsorTestModule],
        declarations: [BusinessContactProfileDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(BusinessContactProfileDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BusinessContactProfileDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.businessContactProfile).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
