import { inject } from '@angular/core';
import { Router, CanMatchFn } from '@angular/router';
import { AuthService } from '../service/Auth.service';

export const adminGuard: CanMatchFn = (route, state) => {
  const auth = inject(AuthService);
  const router = inject(Router);

  const role = auth.getRole();

  if (role && role == 'admin') {
    return true;
  }

  router.navigate(['/401']);
  return false;
};
