import { Component, OnInit } from '@angular/core';
import { LoginRecord, User } from '../models';
import { UserService } from '../user.service';

@Component({
  selector: 'app-user-info',
  templateUrl: './user-info.component.html',
  styleUrls: ['./user-info.component.css'],
})
export class UserInfoComponent implements OnInit {
  user!: User;
  loginRecords!: LoginRecord[];

  constructor(private userSvc: UserService) {}

  ngOnInit(): void {
    const userData: {
      userId_returned: string;
      username_returned: string;
      email_returned: string;
      _token: string;
      _tokenExpiry: Date;
    } = JSON.parse(localStorage.getItem('userData')!);

    const userName = userData.username_returned;
    const token = userData._token;
    const userId = userData.userId_returned;
    const userEmail = userData.email_returned;

    // console.log('userName: ' + userName + ' token: ' + token);

    this.userSvc
      .getUser(userEmail, token)
      .then((result) => {
        this.user = result;
      })
      .catch((error) => {
        console.error('>>> error', error);
      });

    this.userSvc
      .getLoginRecord(userId, token)
      .then((result) => {
        this.loginRecords = result;
      })
      .catch((error) => {
        console.error('>>> error', error);
      });
  }
}
