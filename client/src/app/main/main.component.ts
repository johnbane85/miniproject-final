import { Component, OnInit } from '@angular/core';
import { LocationService } from '../location.service';
import { Location } from '../models';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css'],
})
export class MainComponent implements OnInit {
  locations: Location[] = [];

  constructor(private locationSvc: LocationService) {}

  ngOnInit(): void {
    this.locationSvc
      .getLocations()
      .then((result) => {
        this.locations = result;
        // console.log('>>> locations: ', result);
      })
      .catch((error) => {
        console.error('>>> error', error);
      });
  }
}
