import {
  ApplicationConfig,
  provideZoneChangeDetection,
  isDevMode
} from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { provideServiceWorker } from '@angular/service-worker';
import { routes } from './app.routes';
import { requestInterceptor } from './shared/interceptors/request.interceptor';

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    provideHttpClient(withInterceptors([requestInterceptor])),
    ...(isDevMode()
      ? []
      : [
        provideServiceWorker('ngsw-worker.js', {
          registrationStrategy: 'registerWhenStable:30000',
        }),
      ]),
  ],
};
