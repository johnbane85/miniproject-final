import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NoSpaceValidator } from '../nospace.validator';
import { AuthService } from './auth.service';
import { take } from 'rxjs';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.css'],
})
export class AuthComponent implements OnInit {
  form!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private authSvc: AuthService
  ) {}

  ngOnInit(): void {
    this.form = this.createForm();
    if (localStorage.getItem('userData') != null) {
      alert('You are already logged in!');
      this.router.navigate(['/']);
    }
  }

  processForm() {
    if (!this.form.valid) {
      return;
    }

    const userEmail = this.form.value.email;
    const userPassword = this.form.value.password;

    if (localStorage.getItem('userData') != null) {
      alert('You are already logged in!');
      this.router.navigate(['/']);
    } else {
      this.authSvc
        .userLogin(userEmail, userPassword)
        .pipe(take(1))
        .subscribe((authModel) => {
          // console.log('authResponse: ', authModel);

          alert('Login Successful, Welcome Back!');
          this.router.navigate(['/user-info']);
        });
    }

    // this.form = this.createForm();
  }

  createForm(): FormGroup {
    return this.fb.group({
      email: this.fb.control('', [Validators.required, Validators.email]),
      password: this.fb.control('', [
        Validators.required,
        Validators.minLength(6),
        NoSpaceValidator.cannotContainSpace,
      ]),
    });
  }
}
