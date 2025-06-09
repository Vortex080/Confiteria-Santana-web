import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Order } from '../interface/Order';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  private apiUrlPOST = 'http://localhost:9080/confiteria/api/order';

  constructor(private http: HttpClient) { }

  crearOrder(Order: Order): Observable<HttpResponse<Order>> {

    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });

    return this.http.post<Order>(this.apiUrlPOST, Order, { headers, observe: 'response' });
  }

  getOrderById(id: number): Observable<Order> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.get<Order>(`${this.apiUrlPOST}/${id}`, { headers });
  }

  updateOrder(Order: Order): Observable<HttpResponse<Order>> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.put<Order>(`${this.apiUrlPOST}/${Order.id}`, Order, { headers, observe: 'response' });
  }

  getallPedido(): Observable<Order[]> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.get<Order[]>(this.apiUrlPOST, { headers });
  }

}
