import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Product } from '../interface/product';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProductosService {

  private apiUrlPOST = '/api/api/product';

  constructor(private http: HttpClient) { }

  crearProduct(Product: Product): Observable<HttpResponse<Product>> {

    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.post<Product>(this.apiUrlPOST, Product, { headers, observe: 'response' });
  }

  getProductById(id: number): Observable<Product> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.get<Product>(`${this.apiUrlPOST}/${id}`, { headers });
  }

  updateProduct(Product: Product): Observable<HttpResponse<Product>> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.put<Product>(`${this.apiUrlPOST}/${Product.id}`, Product, { headers, observe: 'response' });
  }

  getallProduct(): Observable<Product[]> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.get<Product[]>(`${this.apiUrlPOST}`, { headers });
  }

  deleteProduct(id: number): Observable<HttpResponse<void>> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.delete<void>(`${this.apiUrlPOST}/${id}`, { headers, observe: 'response' });
  }
}
