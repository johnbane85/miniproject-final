import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Weather } from './models';
import { firstValueFrom } from 'rxjs';

// const BACKEND = 'http://localhost:8080';
const BACKEND = '';

@Injectable()
export class WeatherService {
  constructor(private http: HttpClient) {}

  public getWeatherByCity(city_name: string): Promise<Weather> {
    return firstValueFrom(
      this.http.get<Weather>(`${BACKEND}/api/weather/${city_name}`)
    );
  }
}
