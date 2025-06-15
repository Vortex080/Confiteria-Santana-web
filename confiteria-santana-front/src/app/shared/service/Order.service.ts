import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Order, OrderResponse } from '../interface/Order';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  private apiUrlPOST = '/api/api/order';

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

  getallPedido(): Observable<OrderResponse[]> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.get<OrderResponse[]>(this.apiUrlPOST, { headers });
  }

  deleteOrder(id: number): Observable<HttpResponse<void>> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.delete<void>(`${this.apiUrlPOST}/${id}`, { headers, observe: 'response' });
  }

}
