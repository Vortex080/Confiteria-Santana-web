import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Address } from '../interface/address';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AddressService {
  private apiUrlPOST = 'http://localhost:9080/confiteria/api/address';

  constructor(private http: HttpClient) { }

  crearDireccion(address: Address): Observable<HttpResponse<Address>> {

    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });

    return this.http.post<Address>(this.apiUrlPOST, address, { headers, observe: 'response' });
  }

  getAddressById(id: number): Observable<Address> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.get<Address>(`${this.apiUrlPOST}/${id}`, { headers });
  }

  updateAddress(address: Address): Observable<HttpResponse<Address>> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.put<Address>(`${this.apiUrlPOST}/${address.id}`, address, { headers, observe: 'response' });
  }
}
