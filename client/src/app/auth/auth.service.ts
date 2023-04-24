import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Subject, catchError, tap, throwError } from 'rxjs';
import { authModel, authUser } from './auth.model';
import { Router } from '@angular/router';

// const BACKEND = 'http://localhost:8080';
const BACKEND = '';

@Injectable()
export class AuthService {
  authUser = new BehaviorSubject<authUser>(null!);
  private tokenExpirationTimer: any;

  constructor(private http: HttpClient, private router: Router) {}

  public userLogin(email: string, password: string) {
    const form = new FormData();
    form.set('email', email);
    form.set('password', password);

    this.http.post<authModel>(`${BACKEND}/api/userLogin`, form);

    return this.http.post<authModel>(`${BACKEND}/api/userLogin`, form).pipe(
      catchError((error) => throwError(() => this.handleError(error))),
      tap((resData) => {
        this.handleAuthentication(
          resData.userId_returned,
          resData.username_returned,
          resData.email_returned,
          resData._token,
          resData.expiresIn
        );
      })
    );
  }

  private handleError(error: any) {
    let errorMessage = error;
    alert('Incorrect e-mail or password, Please try again');
    return errorMessage;
  }

  public autoLogin() {
    const userData: {
      userId_returned: string;
      username_returned: string;
      email_returned: string;
      _token: string;
      _tokenExpiry: Date;
    } = JSON.parse(localStorage.getItem('userData')!);

    if (!userData) {
      return;
    }

    const loadedUser = new authUser(
      userData.userId_returned,
      userData.username_returned,
      userData.email_returned,
      userData._token,
      new Date(userData._tokenExpiry)
    );

    if (loadedUser.token) {
      this.authUser.next(loadedUser);
      const expirationDuration =
        new Date(userData._tokenExpiry).getTime() - new Date().getTime();
      this.autoLogout(expirationDuration);
    } else {
      this.autoLogout(1);
    }
  }

  public logout() {
    this.authUser.next(null!);
    localStorage.removeItem('userData');
    alert('You have Logged out.');
    this.router.navigate(['/']);

    if (this.tokenExpirationTimer) {
      clearTimeout(this.tokenExpirationTimer);
    }
    this.tokenExpirationTimer = null;
  }

  public autoLogout(expirationDuration: number) {
    this.tokenExpirationTimer = setTimeout(() => {
      this.logout();
    }, expirationDuration);
  }

  private handleAuthentication(
    user_id: string,
    username: string,
    email: string,
    token: string,
    expiresIn: Date
  ) {
    const date = new Date(+expiresIn);
    const expiringIn = date.getTime() - new Date().getTime();
    // console.log('date: ', date.getTime() - new Date().getTime());
    const user = new authUser(user_id, username, email, token, date);
    this.authUser.next(user);
    this.autoLogout(expiringIn);
    localStorage.setItem('userData', JSON.stringify(user));
  }
}
