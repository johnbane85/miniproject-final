import {
  ActivatedRouteSnapshot,
  CanActivateFn,
  Router,
  RouterStateSnapshot,
  UrlTree,
} from '@angular/router';
import { Observable } from 'rxjs';
import { inject } from '@angular/core';
import { authUser } from './auth.model';

export const AuthGuard: CanActivateFn = (
  route: ActivatedRouteSnapshot,
  state: RouterStateSnapshot
):
  | Observable<boolean | UrlTree>
  | Promise<boolean | UrlTree>
  | boolean
  | UrlTree => {
  const router: Router = inject(Router);

  var USER: authUser = new authUser('', '', '', '', new Date());

  if (localStorage.getItem('userData')) {
    USER = JSON.parse(localStorage.getItem('userData')!);
  }

  const user_token = USER.token;

  if (user_token === '') {
    return router.navigate(['/']);
  } else {
    return true;
  }
};
