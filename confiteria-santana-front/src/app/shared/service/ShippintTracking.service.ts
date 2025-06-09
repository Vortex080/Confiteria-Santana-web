import { Injectable } from '@angular/core';
import { ShippingTracking } from '../interface/ShippingTracking';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ShippintTrackingService {

  private apiUrlPOST = 'http://localhost:9080/confiteria/api/shippingtracking';

  constructor(private http: HttpClient) { }

  crearShippingTracking(ShippingTracking: ShippingTracking): Observable<HttpResponse<ShippingTracking>> {

    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });

    return this.http.post<ShippingTracking>(this.apiUrlPOST, ShippingTracking, { headers, observe: 'response' });
  }

  getShippingTrackingById(id: number): Observable<ShippingTracking> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.get<ShippingTracking>(`${this.apiUrlPOST}/${id}`, { headers });
  }

  updateShippingTracking(ShippingTracking: ShippingTracking): Observable<HttpResponse<ShippingTracking>> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.put<ShippingTracking>(`${this.apiUrlPOST}/${ShippingTracking.id}`, ShippingTracking, { headers, observe: 'response' });
  }

}
