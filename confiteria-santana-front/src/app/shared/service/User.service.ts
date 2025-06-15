import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../interface/user';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {


  private apiUrlPOST = 'confiteria/api/user';

  constructor(private http: HttpClient) { }

  crearUser(User: User): Observable<HttpResponse<User>> {

    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.post<User>(this.apiUrlPOST, User, { headers, observe: 'response' });
  }

  getUserById(id: number): Observable<User> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.get<User>(`${this.apiUrlPOST}/${id}`, { headers });
  }

  updateUser(User: User): Observable<HttpResponse<User>> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    console.log(User);
    return this.http.put<User>(`${this.apiUrlPOST}/${User.id}`, User, { headers, observe: 'response' });
  }

  getallUser(): Observable<User[]> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.get<User[]>(`${this.apiUrlPOST}`, { headers });
  }

}
