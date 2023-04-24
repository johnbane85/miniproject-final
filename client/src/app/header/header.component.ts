import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { AuthService } from '../auth/auth.service';
import { authUser } from '../auth/auth.model';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
})
export class HeaderComponent implements OnInit, OnDestroy {
  isAuthenticated = false;
  private userSub!: Subscription;
  authUser!: authUser;

  constructor(private router: Router, private authSvc: AuthService) {}

  ngOnInit(): void {
    this.userSub = this.authSvc.authUser.subscribe((user) => {
      this.isAuthenticated = !user ? false : true;

      this.authUser = user;
    });

    this.authSvc.autoLogin();
  }

  onLogout() {
    this.authSvc.logout();

    this.router.navigate(['/']);
  }

  ngOnDestroy(): void {
    this.userSub.unsubscribe();
  }
}
