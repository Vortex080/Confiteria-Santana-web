import { Injectable } from '@angular/core';
import { StockMovementsInput, StockMovementsOutput } from '../interface/StockMovements';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class StockMovementsService {

  private apiUrlPOST = '/api/api/stockmovements';

  constructor(private http: HttpClient) { }

  crearStockMovements(StockMovements: StockMovementsOutput): Observable<HttpResponse<StockMovementsOutput>> {

    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });

    return this.http.post<StockMovementsOutput>(this.apiUrlPOST, StockMovements, { headers, observe: 'response' });
  }

  getStockMovementsById(id: number): Observable<StockMovementsInput> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.get<StockMovementsInput>(`${this.apiUrlPOST}/${id}`, { headers });
  }

  updateStockMovements(StockMovements: StockMovementsOutput): Observable<HttpResponse<StockMovementsOutput>> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.put<StockMovementsOutput>(`${this.apiUrlPOST}/${StockMovements.id}`, StockMovements, { headers, observe: 'response' });
  }

  deleteStockMovements(id: number): Observable<HttpResponse<void>> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.delete<void>(`${this.apiUrlPOST}/${id}`, { headers, observe: 'response' });
  }
  
  getAllMovements(): Observable<StockMovementsInput[]> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.get<StockMovementsInput[]>(this.apiUrlPOST, { headers });
  }

}
