import { Injectable } from '@angular/core';
import { Stock } from '../interface/Stock';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class StockService {

  private apiUrlPOST = '/api/api/stock';

  constructor(private http: HttpClient) { }

  crearStock(Stock: Stock): Observable<HttpResponse<Stock>> {

    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });

    return this.http.post<Stock>(this.apiUrlPOST, Stock, { headers, observe: 'response' });
  }

  getStockById(id: number): Observable<Stock> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.get<Stock>(`${this.apiUrlPOST}/${id}`, { headers });
  }

  updateStock(Stock: Stock): Observable<HttpResponse<Stock>> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.put<Stock>(`${this.apiUrlPOST}/${Stock.id}`, Stock, { headers, observe: 'response' });
  }

  deleteStock(id: number): Observable<HttpResponse<void>> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.delete<void>(`${this.apiUrlPOST}/${id}`, { headers, observe: 'response' });
  }
  getAllStock(): Observable<Stock[]> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.get<Stock[]>(this.apiUrlPOST, { headers });
  }

}
