import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { User } from '../interface/user';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  private apiUrl = 'http://localhost:9080/confiteria/api/user/login'; // Cambia esta URL por la de tu endpoint

  constructor(private http: HttpClient) { }

  login(email: string, pass: string): Observable<HttpResponse<any>> {
    const body = { email: email, pass: pass };
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });

    return this.http.post<any>(this.apiUrl, body, { headers, observe: 'response' });
  }
}
