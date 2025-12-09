import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Product {
    id?: number;
    nom: string;
    prix: number;
    description?: string;
}

@Injectable({
    providedIn: 'root'
})
export class ProductService {
    private readonly API_URL = 'http://localhost:8080/api/produits';

    constructor(private http: HttpClient) { }

    getAll(): Observable<Product[]> {
        return this.http.get<Product[]>(this.API_URL);
    }

    getById(id: number): Observable<Product> {
        return this.http.get<Product>(`${this.API_URL}/${id}`);
    }

    create(product: Product): Observable<Product> {
        return this.http.post<Product>(this.API_URL, product);
    }

    update(id: number, product: Product): Observable<Product> {
        return this.http.put<Product>(`${this.API_URL}/${id}`, product);
    }

    delete(id: number): Observable<string> {
        return this.http.delete(`${this.API_URL}/${id}`, { responseType: 'text' });
    }
}
