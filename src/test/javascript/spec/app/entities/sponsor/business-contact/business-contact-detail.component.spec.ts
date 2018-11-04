/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SponsorTestModule } from '../../../../test.module';
import { BusinessContactDetailComponent } from 'app/entities/sponsor/business-contact/business-contact-detail.component';
import { BusinessContact } from 'app/shared/model/sponsor/business-contact.model';

describe('Component Tests', () => {
  describe('BusinessContact Management Detail Component', () => {
    let comp: BusinessContactDetailComponent;
    let fixture: ComponentFixture<BusinessContactDetailComponent>;
    const route = ({ data: of({ businessContact: new BusinessContact(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SponsorTestModule],
        declarations: [BusinessContactDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(BusinessContactDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BusinessContactDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.businessContact).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
