import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject, tap } from 'rxjs';
import { AuthResponse, LoginRequest, RegisterRequest } from '../models/auth.models';

@Injectable({
    providedIn: 'root'
})
export class AuthService {
    private readonly API_URL = 'http://localhost:8080/api/auth';
    private currentUserSubject = new BehaviorSubject<string | null>(this.getStoredUser());

    constructor(private http: HttpClient) { }

    login(credentials: LoginRequest): Observable<AuthResponse> {
        return this.http.post<AuthResponse>(`${this.API_URL}/login`, credentials).pipe(
            tap(response => {
                this.setToken(response.accessToken);
                this.currentUserSubject.next(credentials.username);
            })
        );
    }

    register(userData: RegisterRequest): Observable<string> {
        return this.http.post(`${this.API_URL}/register`, userData, { responseType: 'text' });
    }

    logout(): void {
        localStorage.removeItem('token');
        localStorage.removeItem('username');
        this.currentUserSubject.next(null);
    }

    getToken(): string | null {
        return localStorage.getItem('token');
    }

    private setToken(token: string): void {
        localStorage.setItem('token', token);
    }

    private getStoredUser(): string | null {
        return localStorage.getItem('username');
    }

    isAuthenticated(): boolean {
        return this.getToken() !== null;
    }

    getCurrentUser(): Observable<string | null> {
        return this.currentUserSubject.asObservable();
    }
}
