import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import {
  DeleteUserResponse,
  EditUserResponse,
  LoginRecord,
  PostUserResponse,
  User,
} from './models';
import { firstValueFrom } from 'rxjs';

// const BACKEND = 'http://localhost:8080';
const BACKEND = '';

@Injectable()
export class UserService {
  constructor(private http: HttpClient) {}

  public postUser(user: User): Promise<PostUserResponse> {
    const form = new FormData();
    form.set('username', user.username);
    form.set('email', user.email);
    form.set('password', user.password);

    return firstValueFrom(
      this.http.post<PostUserResponse>(`${BACKEND}/api/postUser`, form)
    );
  }

  public getUser(username: string, token: string): Promise<User> {
    const params = new HttpParams()
      .set('username', username)
      .set('token', token);

    return firstValueFrom(
      this.http.get<User>(`${BACKEND}/api/getUser`, { params: params })
    );
  }

  public editUser(user: User, token: string): Promise<EditUserResponse> {
    const form = new FormData();
    form.set('token', token);
    form.set('username', user.username);
    form.set('email', user.email);
    form.set('password', user.password);
    form.set('user_id', user.user_id!);
    // form.set('userImage', user.userImage!);

    return firstValueFrom(
      this.http.put<EditUserResponse>(`${BACKEND}/api/editUser`, form)
    );
  }

  public getLoginRecord(
    user_id: string,
    token: string
  ): Promise<LoginRecord[]> {
    const params = new HttpParams().set('user_id', user_id).set('token', token);

    return firstValueFrom(
      this.http.get<LoginRecord[]>(`${BACKEND}/api/getLoginRecord`, {
        params: params,
      })
    );
  }

  public deleteUser(
    user_id: string,
    token: string,
    email: string
  ): Promise<DeleteUserResponse> {
    const params = new HttpParams()
      .set('user_id', user_id)
      .set('token', token)
      .set('email', email);

    return firstValueFrom(
      this.http.delete<DeleteUserResponse>(`${BACKEND}/api/deleteUser`, {
        params: params,
      })
    );
  }
}
