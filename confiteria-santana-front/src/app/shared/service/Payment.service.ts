import { Injectable } from '@angular/core';
import { Payments } from '../interface/Payments';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {

  private apiUrlPOST = 'http://localhost:9080/confiteria/api/payment';

  constructor(private http: HttpClient) { }

  crearPayment(Payment: Payments): Observable<HttpResponse<Payments>> {

    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });

    return this.http.post<Payments>(this.apiUrlPOST, Payment, { headers, observe: 'response' });
  }

  getPaymentById(id: number): Observable<Payments> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.get<Payments>(`${this.apiUrlPOST}/${id}`, { headers });
  }

  updatePayment(Payment: Payments): Observable<HttpResponse<Payments>> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.put<Payments>(`${this.apiUrlPOST}/${Payment.id}`, Payment, { headers, observe: 'response' });
  }

}
