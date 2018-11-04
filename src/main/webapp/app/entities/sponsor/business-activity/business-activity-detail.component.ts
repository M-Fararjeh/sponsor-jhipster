import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBusinessActivity } from 'app/shared/model/sponsor/business-activity.model';

@Component({
  selector: 'jhi-business-activity-detail',
  templateUrl: './business-activity-detail.component.html'
})
export class BusinessActivityDetailComponent implements OnInit {
  businessActivity: IBusinessActivity;

  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ businessActivity }) => {
      this.businessActivity = businessActivity;
    });
  }

  previousState() {
    window.history.back();
  }
}
