import { inject } from '@angular/core';
import { Router, type CanMatchFn } from '@angular/router';
import { AuthService } from '../service/Auth.service';

export const adminGuard: CanMatchFn = (route, state) => {

  const auth = inject(AuthService);
  const router = inject(Router);

  if (auth.getRole() === 'almacen') {
    return true;
  }

  router.navigate(['/home']);
  return false;
};
