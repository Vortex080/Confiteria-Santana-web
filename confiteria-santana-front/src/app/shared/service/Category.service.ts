import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Category } from '../interface/category';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {
  private apiUrlPOST = '/api/api/category'; 

  constructor(private http: HttpClient) { }

  crearCategory(Category: Category): Observable<HttpResponse<Category>> {

    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });

    return this.http.post<Category>(this.apiUrlPOST, Category, { headers, observe: 'response' });
  }

  getCategoryById(id: number): Observable<Category> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.get<Category>(`${this.apiUrlPOST}/${id}`, { headers });
  }

  updateCategory(Category: Category): Observable<HttpResponse<Category>> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.put<Category>(`${this.apiUrlPOST}/${Category.id}`, Category, { headers, observe: 'response' });
  }

  getallCategory(): Observable<Category[]> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.get<Category[]>(`${this.apiUrlPOST}`, { headers });
  }

  deleteCategory(id: number): Observable<HttpResponse<void>> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.delete<void>(`${this.apiUrlPOST}/${id}`, { headers, observe: 'response' });
  }

}
