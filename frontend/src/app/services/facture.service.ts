import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Facture {
    id?: number;
    ref: string;
    date: Date | string;
    client?: any;
    produits?: any[];
}

@Injectable({
    providedIn: 'root'
})
export class FactureService {
    private readonly API_URL = 'http://localhost:8080/api/factures';

    constructor(private http: HttpClient) { }

    getAll(): Observable<Facture[]> {
        return this.http.get<Facture[]>(this.API_URL);
    }

    getById(id: number): Observable<Facture> {
        return this.http.get<Facture>(`${this.API_URL}/${id}`);
    }

    getByClientId(clientId: number): Observable<Facture[]> {
        return this.http.get<Facture[]>(`${this.API_URL}/client/${clientId}`);
    }

    create(facture: Facture): Observable<Facture> {
        return this.http.post<Facture>(this.API_URL, facture);
    }

    update(id: number, facture: Facture): Observable<Facture> {
        return this.http.put<Facture>(`${this.API_URL}/${id}`, facture);
    }

    delete(id: number): Observable<string> {
        return this.http.delete(`${this.API_URL}/${id}`, { responseType: 'text' });
    }
}
