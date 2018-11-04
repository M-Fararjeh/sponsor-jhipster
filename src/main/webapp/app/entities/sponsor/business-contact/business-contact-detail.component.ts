import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBusinessContact } from 'app/shared/model/sponsor/business-contact.model';

@Component({
  selector: 'jhi-business-contact-detail',
  templateUrl: './business-contact-detail.component.html'
})
export class BusinessContactDetailComponent implements OnInit {
  businessContact: IBusinessContact;

  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ businessContact }) => {
      this.businessContact = businessContact;
    });
  }

  previousState() {
    window.history.back();
  }
}
