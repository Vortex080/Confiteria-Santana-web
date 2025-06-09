import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private user = localStorage.getItem('user') ? JSON.parse(localStorage.getItem('user')!) : null;

  getRole(): string {
    return this.user.rol;
  }

}
