import { inject } from '@angular/core';
import { Router, type CanMatchFn } from '@angular/router';
import { AuthService } from '../service/Auth.service';

export const TpvGuard: CanMatchFn = (route, state) => {

  const auth = inject(AuthService);
  const router = inject(Router);

  const role = auth.getRole();

  if (role && role == 'tpv') {
    return true;
  }

  router.navigate(['/401']);
  return false;
};
