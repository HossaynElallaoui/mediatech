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

    constructor(private http: HttpClient) {
        console.log('AuthService initialized');
    }

    login(credentials: LoginRequest): Observable<AuthResponse> {
        console.log('Login attempt for user:', credentials.username);
        return this.http.post<AuthResponse>(`${this.API_URL}/login`, credentials).pipe(
            tap(response => {
                console.log('Login response received:', response);
                if (response && response.accessToken) {
                    this.setToken(response.accessToken);
                    this.currentUserSubject.next(credentials.username);
                    console.log('Token stored successfully');
                } else {
                    console.error('No access token in response');
                }
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
        console.log('Logged out, token removed');
    }

    getToken(): string | null {
        const token = localStorage.getItem('token');
        console.log('Getting token:', token ? 'Token exists' : 'No token');
        return token;
    }

    private setToken(token: string): void {
        localStorage.setItem('token', token);
        console.log('Token set in localStorage');
    }

    private getStoredUser(): string | null {
        return localStorage.getItem('username');
    }

    isAuthenticated(): boolean {
        const hasToken = this.getToken() !== null;
        console.log('Is authenticated:', hasToken);
        return hasToken;
    }

    getCurrentUser(): Observable<string | null> {
        return this.currentUserSubject.asObservable();
    }
}
