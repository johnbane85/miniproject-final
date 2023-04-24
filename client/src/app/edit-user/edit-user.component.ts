import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserService } from '../user.service';
import { NoSpaceValidator } from '../nospace.validator';
import { User } from '../models';
import { AuthService } from '../auth/auth.service';

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.css'],
})
export class EditUserComponent implements OnInit {
  form!: FormGroup;
  form_delUSer!: FormGroup;
  username: string = '';
  password: string = '';
  email: string = '';
  token: string = '';
  userId: string = '';

  constructor(
    private fb: FormBuilder,
    private userSvc: UserService,
    private authSvc: AuthService
  ) {}

  ngOnInit(): void {
    const userData: {
      userId_returned: string;
      username_returned: string;
      email_returned: string;
      _token: string;
      _tokenExpiry: Date;
    } = JSON.parse(localStorage.getItem('userData')!);

    this.token = userData._token;

    this.userSvc
      .getUser(userData.username_returned, userData._token)
      .then((result) => {
        this.username = result.username;
        this.password = result.password;
        this.email = result.email;
        this.userId = result.user_id!;
        this.form = this.createForm();
      })
      .catch((error) => {
        console.error('>>> error', error);
      });

    this.form = this.createForm();
    this.form_delUSer = this.createFormDelUser();
  }

  processForm() {
    // console.log('edit-user form value: ', this.form.value);

    const user = this.form.value as User;
    user.user_id = this.userId;

    this.userSvc
      .editUser(user, this.token)
      .then((response) => {
        // console.info(`editUserResponse: ${response.editUserResponse}`);
      })
      .catch((error) => {
        console.error('>>> error: ', error);
      });

    this.authSvc.logout();
  }

  processFormDelUser() {
    const user_id = this.userId;
    const token = this.token;
    const email = this.email;
    const passcode = this.form_delUSer.get('passcode')?.value;
    // console.log(passcode);

    if (passcode === 'DELETE') {
      this.userSvc
        .deleteUser(user_id, token, email)
        .then((response) => {
          console.info(`deleteUserResponse: ${response.deleteUserResponse}`);
        })
        .catch((error) => {
          console.error('>>> error: ', error);
        });

      this.authSvc.logout();
    } else {
      this.form_delUSer = this.createFormDelUser();
    }
  }

  createForm(): FormGroup {
    return this.fb.group({
      username: this.fb.control(this.username, [
        Validators.required,
        Validators.minLength(3),
        NoSpaceValidator.cannotContainSpace,
      ]),
      email: this.fb.control(this.email, [
        Validators.required,
        Validators.email,
      ]),
      password: this.fb.control(this.password, [
        Validators.required,
        Validators.minLength(6),
        NoSpaceValidator.cannotContainSpace,
      ]),
    });
  }

  createFormDelUser(): FormGroup {
    return this.fb.group({
      passcode: this.fb.control('', [
        Validators.required,
        Validators.minLength(6),
        NoSpaceValidator.cannotContainSpace,
      ]),
    });
  }
}
