// admin.guard.ts
import { inject } from '@angular/core';
import { Router, CanMatchFn } from '@angular/router';
import { AuthService } from '../service/Auth.service';

export const adminGuard = (allowedRoles: string[]): CanMatchFn => {
  return (route, state) => {
    const auth = inject(AuthService);
    const router = inject(Router);

    const userRole = auth.getRole();
    const isAuthorized = allowedRoles.includes(userRole);

    if (isAuthorized) {
      return true;
    }

    router.navigate(['/home']);
    return false;
  };
};
