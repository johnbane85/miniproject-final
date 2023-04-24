import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, NgForm, Validators } from '@angular/forms';
import { UserService } from '../user.service';
import { NoSpaceValidator } from '../nospace.validator';
import { User } from '../models';
import { Router } from '@angular/router';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css'],
})
export class SignupComponent implements OnInit {
  form!: FormGroup;
  response: string = '';

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private userSvc: UserService
  ) {}

  ngOnInit(): void {
    this.form = this.createForm();

    if (localStorage.getItem('userData') != null) {
      alert('You are already logged in!');
      this.router.navigate(['/']);
    }
  }

  processForm() {
    const user = this.form.value as User;

    this.userSvc
      .postUser(user)
      .then((response) => {
        console.info(`>>> postUserResponse: ${response.postUserResponse}`);
        this.response = response.postUserResponse;
        this.form = this.createForm();
        alert(this.response);
        this.router.navigate(['/']);
      })
      .catch((error) => {
        console.error('>>> error: ', error);
      });
  }

  createForm(): FormGroup {
    return this.fb.group({
      username: this.fb.control('', [
        Validators.required,
        Validators.minLength(3),
        NoSpaceValidator.cannotContainSpace,
      ]),
      email: this.fb.control('', [Validators.required, Validators.email]),
      password: this.fb.control('', [
        Validators.required,
        Validators.minLength(6),
        NoSpaceValidator.cannotContainSpace,
      ]),
    });
  }
}
