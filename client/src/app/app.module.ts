import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { MainComponent } from './main/main.component';

import { LocationService } from './location.service';
import { LocationComponent, SafePipe } from './location/location.component';
import { UserInfoComponent } from './user-info/user-info.component';
import { CommentComponent } from './comment/comment.component';
import { HttpClientModule } from '@angular/common/http';
import { SignupComponent } from './signup/signup.component';
import { UserService } from './user.service';
import { HeaderComponent } from './header/header.component';
import { CommentService } from './comment.service';
import { AuthComponent } from './auth/auth.component';
import { AuthService } from './auth/auth.service';

import { EditUserComponent } from './edit-user/edit-user.component';
import { AuthGuard } from './auth/auth.guard';
import { WeatherService } from './weather.service';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from './material/material.module';

const appRoutes: Routes = [
  {
    path: '',
    component: MainComponent,
  },
  {
    path: 'login',
    component: AuthComponent,
  },
  {
    path: 'signup',
    component: SignupComponent,
  },
  {
    path: 'user-info',
    component: UserInfoComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'user-info/edit-user',
    component: EditUserComponent,
    canActivate: [AuthGuard],
  },
  {
    path: ':location_name/comment',
    component: CommentComponent,
    canActivate: [AuthGuard],
  },
  { path: 'location/:location_name', component: LocationComponent },

  { path: '**', redirectTo: '/', pathMatch: 'full' },
];

@NgModule({
  declarations: [
    AppComponent,
    MainComponent,
    SignupComponent,
    LocationComponent,
    UserInfoComponent,
    CommentComponent,
    HeaderComponent,
    SafePipe,
    AuthComponent,
    EditUserComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    ReactiveFormsModule,
    RouterModule.forRoot(appRoutes, {
      scrollPositionRestoration: 'enabled',
      useHash: true,
    }),
    BrowserAnimationsModule,
    MaterialModule,
  ],
  providers: [
    LocationService,
    UserService,
    CommentService,
    AuthService,
    WeatherService,
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
