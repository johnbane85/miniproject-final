import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Comment, Location } from './models';
import { firstValueFrom } from 'rxjs';

// const BACKEND = 'http://localhost:8080';

@Injectable()
export class LocationService {
  constructor(private http: HttpClient) {}

  public getLocations(): Promise<Location[]> {
    return firstValueFrom(
      // this.http.get<Location[]>(`${BACKEND}/api/locations`)
      this.http.get<Location[]>(`/api/locations`)
    );
  }

  public getLocationByName(location_name: string): Promise<Location> {
    return firstValueFrom(
      // this.http.get<Location>(`${BACKEND}/api/location/${location_name}`)
      this.http.get<Location>(`/api/location/${location_name}`)
    );
  }
}
