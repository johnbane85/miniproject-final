import { Component, OnDestroy, OnInit } from '@angular/core';
import { LocationService } from '../location.service';
import { ActivatedRoute } from '@angular/router';
import { Location, Comment, Weather } from '../models';

import { Pipe, PipeTransform } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { CommentService } from '../comment.service';
import { WeatherService } from '../weather.service';
import { Subscription } from 'rxjs';
import { AuthService } from '../auth/auth.service';

// sanitize unsafe HTML string before inserting it into the DOM
@Pipe({ name: 'safe' })
export class SafePipe implements PipeTransform {
  constructor(private sanitizer: DomSanitizer) {}
  transform(url: string) {
    return this.sanitizer.bypassSecurityTrustResourceUrl(url);
  }
}

@Component({
  selector: 'app-location',
  templateUrl: './location.component.html',
  styleUrls: ['./location.component.css'],
})
export class LocationComponent implements OnInit, OnDestroy {
  locationName!: string;
  location!: Location;
  api_location: string = '';
  api_url: string = '';
  comments: Comment[] = [];
  weather!: Weather;
  isAuthenticated = false;
  private userSub!: Subscription;

  constructor(
    private locationSvc: LocationService,
    private activeRoute: ActivatedRoute,
    private commentSvc: CommentService,
    private weatherSvc: WeatherService,
    private authSvc: AuthService
  ) {}

  ngOnInit(): void {
    // call for location details
    this.locationName = this.activeRoute.snapshot.params['location_name'];
    this.locationSvc
      .getLocationByName(this.locationName)
      .then((result) => {
        this.location = result;
      })
      .catch((error) => {
        console.error('>>> error', error);
      });

    // call to the google maps API
    // slice() returns a new copy of the string without modifying the original string
    this.api_url =
      'https://www.google.com/maps/embed/v1/place?key=AIzaSyBg5QfDjS-uHoKYMFgiWEVdolx8er53mrw&q=' +
      this.locationName.slice().replaceAll(' ', '+');

    // call for list of comments
    this.commentSvc
      .getComments(this.locationName)
      .then((result) => {
        // sort the comments array in Descending order
        this.comments = result.sort((a, b) =>
          a.comment_date! > b.comment_date! ? -1 : 1
        );
        // console.log('>>> comments: ', this.comments);
      })
      .catch((error) => {
        console.error('>>> error', error);
      });

    // call WeatherService
    this.weatherSvc.getWeatherByCity('Singapore').then((result) => {
      this.weather = result;
    });

    // check if user is Authenticated
    this.userSub = this.authSvc.authUser.subscribe((user) => {
      this.isAuthenticated = !user ? false : true;
    });
  }

  ngOnDestroy(): void {
    this.userSub.unsubscribe();
  }
}
