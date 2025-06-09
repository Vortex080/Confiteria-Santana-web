import { Injectable } from '@angular/core';
import { StockMovements } from '../interface/StockMovements';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class StockMovementsService {

  private apiUrlPOST = 'http://localhost:9080/confiteria/api/stockmovements';

  constructor(private http: HttpClient) { }

  crearStockMovements(StockMovements: StockMovements): Observable<HttpResponse<StockMovements>> {

    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });

    return this.http.post<StockMovements>(this.apiUrlPOST, StockMovements, { headers, observe: 'response' });
  }

  getStockMovementsById(id: number): Observable<StockMovements> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.get<StockMovements>(`${this.apiUrlPOST}/${id}`, { headers });
  }

  updateStockMovements(StockMovements: StockMovements): Observable<HttpResponse<StockMovements>> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.put<StockMovements>(`${this.apiUrlPOST}/${StockMovements.id}`, StockMovements, { headers, observe: 'response' });
  }

}
