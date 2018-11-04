import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBusinessContactProfile } from 'app/shared/model/sponsor/business-contact-profile.model';

@Component({
  selector: 'jhi-business-contact-profile-detail',
  templateUrl: './business-contact-profile-detail.component.html'
})
export class BusinessContactProfileDetailComponent implements OnInit {
  businessContactProfile: IBusinessContactProfile;

  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ businessContactProfile }) => {
      this.businessContactProfile = businessContactProfile;
    });
  }

  previousState() {
    window.history.back();
  }
}
