import { Injectable } from '@angular/core';
import { Sale } from '../interface/Sale';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SaleService {

  private apiUrlPOST = '/api/api/sale';

  constructor(private http: HttpClient) { }

  crearSale(sale: Sale): Observable<HttpResponse<Sale>> {

    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.post<Sale>(this.apiUrlPOST, sale, { headers, observe: 'response' });
  }

  getSaleById(id: number): Observable<Sale> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.get<Sale>(`${this.apiUrlPOST}/${id}`, { headers });
  }

  updateSale(Sale: Sale): Observable<HttpResponse<Sale>> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.put<Sale>(`${this.apiUrlPOST}/${Sale.id}`, Sale, { headers, observe: 'response' });
  }

  getallSale(): Observable<Sale[]> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.get<Sale[]>(this.apiUrlPOST, { headers });
  }

  deleteSale(id: number): Observable<HttpResponse<void>> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.delete<void>(`${this.apiUrlPOST}/${id}`, { headers, observe: 'response' });
  }

}
