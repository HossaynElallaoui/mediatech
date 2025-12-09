import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from '../services/auth.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
    constructor(private authService: AuthService) {
        console.log('AuthInterceptor created');
    }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        console.log('Intercepting request:', req.url);

        const token = this.authService.getToken();

        if (token) {
            console.log('Adding token to request headers');
            const cloned = req.clone({
                headers: req.headers.set('Authorization', `Bearer ${token}`)
            });
            console.log('Request headers:', cloned.headers.get('Authorization'));
            return next.handle(cloned);
        }

        console.log('No token, sending request without Authorization header');
        return next.handle(req);
    }
}
