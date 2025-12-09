import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface User {
    id?: number;
    username: string;
    roles: string[];
}

export interface CreateUserRequest {
    username: string;
    password: string;
    role: 'ADMIN' | 'USER';
}

export interface AdminStats {
    totalUsers: number;
    totalRoles: number;
}

@Injectable({
    providedIn: 'root'
})
export class AdminService {
    private readonly API_URL = 'http://localhost:8080/api/admin';

    constructor(private http: HttpClient) { }

    // User Management
    getAllUsers(): Observable<User[]> {
        return this.http.get<User[]>(`${this.API_URL}/users`);
    }

    getUserById(id: number): Observable<User> {
        return this.http.get<User>(`${this.API_URL}/users/${id}`);
    }

    createUser(user: CreateUserRequest): Observable<User> {
        return this.http.post<User>(`${this.API_URL}/users`, user);
    }

    updateUser(id: number, user: Partial<CreateUserRequest>): Observable<User> {
        return this.http.put<User>(`${this.API_URL}/users/${id}`, user);
    }

    deleteUser(id: number): Observable<string> {
        return this.http.delete(`${this.API_URL}/users/${id}`, { responseType: 'text' });
    }

    // Statistics
    getStats(): Observable<AdminStats> {
        return this.http.get<AdminStats>(`${this.API_URL}/stats`);
    }
}
