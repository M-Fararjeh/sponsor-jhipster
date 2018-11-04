/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SponsorTestModule } from '../../../../test.module';
import { BusinessActivityDetailComponent } from 'app/entities/sponsor/business-activity/business-activity-detail.component';
import { BusinessActivity } from 'app/shared/model/sponsor/business-activity.model';

describe('Component Tests', () => {
  describe('BusinessActivity Management Detail Component', () => {
    let comp: BusinessActivityDetailComponent;
    let fixture: ComponentFixture<BusinessActivityDetailComponent>;
    const route = ({ data: of({ businessActivity: new BusinessActivity(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SponsorTestModule],
        declarations: [BusinessActivityDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(BusinessActivityDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BusinessActivityDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.businessActivity).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
