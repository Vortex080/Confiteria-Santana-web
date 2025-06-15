import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Alergen } from '../interface/alergen';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AlergensService {
  private apiUrlPOST = '/api/api/alergen';

  constructor(private http: HttpClient) { }

  crearAlergen(alergen: Alergen): Observable<HttpResponse<Alergen>> {

    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });

    return this.http.post<Alergen>(this.apiUrlPOST, alergen, { headers, observe: 'response' });
  }

  getAlergenById(id: number): Observable<Alergen> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.get<Alergen>(`${this.apiUrlPOST}/${id}`, { headers });
  }

  updateAlergen(Alergen: Alergen): Observable<HttpResponse<Alergen>> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.put<Alergen>(`${this.apiUrlPOST}/${Alergen.id}`, Alergen, { headers, observe: 'response' });
  }

  getallAlergen(): Observable<Alergen[]> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.get<Alergen[]>(`${this.apiUrlPOST}`, { headers });
  }

  deleteAlergen(id: number): Observable<HttpResponse<void>> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.delete<void>(`${this.apiUrlPOST}/${id}`, { headers, observe: 'response' });
  }

}
