export interface User {
    id: number;
    username: string;
    roles: Role[];
}

export interface Role {
    id: number;
    name: string;
}

export interface LoginRequest {
    username: string;
    password: string;
}

export interface RegisterRequest {
    username: string;
    password: string;
}

export interface AuthResponse {
    accessToken: string;
    tokenType?: string;
}
