import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PaymentMethod } from '../interface/PaymentMethod';

@Injectable({
  providedIn: 'root'
})
export class PaymentMethodService {

  private apiUrlPOST = '/api/api/paymentmethod';

  constructor(private http: HttpClient) { }

  crearPaymentMethod(PaymentMethod: PaymentMethod): Observable<HttpResponse<PaymentMethod>> {

    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });

    return this.http.post<PaymentMethod>(this.apiUrlPOST, PaymentMethod, { headers, observe: 'response' });
  }

  getPaymentMethodById(id: number): Observable<PaymentMethod> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.get<PaymentMethod>(`${this.apiUrlPOST}/${id}`, { headers });
  }

  updatePaymentMethod(PaymentMethod: PaymentMethod): Observable<HttpResponse<PaymentMethod>> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.put<PaymentMethod>(`${this.apiUrlPOST}/${PaymentMethod.id}`, PaymentMethod, { headers, observe: 'response' });
  }

  getallMethod(): Observable<PaymentMethod[]> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.get<PaymentMethod[]>(`${this.apiUrlPOST}`, { headers });
  }
}
