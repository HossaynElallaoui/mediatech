import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { LoginRequest } from '../../models/auth.models';

@Component({
    selector: 'app-login',
    standalone: true,
    imports: [CommonModule, FormsModule, RouterModule],
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.css']
})
export class LoginComponent {
    credentials: LoginRequest = { username: '', password: '' };
    errorMessage = '';
    isLoading = false;

    constructor(
        private authService: AuthService,
        private router: Router
    ) { }

    onSubmit(): void {
        if (!this.credentials.username || !this.credentials.password) {
            this.errorMessage = 'Please enter both username and password';
            return;
        }

        this.isLoading = true;
        this.errorMessage = '';

        this.authService.login(this.credentials).subscribe({
            next: () => {
                localStorage.setItem('username', this.credentials.username);
                // Navigate to admin dashboard if admin, else regular dashboard
                if (this.credentials.username === 'admin') {
                    this.router.navigate(['/admin']);
                } else {
                    this.router.navigate(['/dashboard']);
                }
            },
            error: (error) => {
                console.error('Login error:', error);
                this.errorMessage = error.error || 'Invalid username or password';
                this.isLoading = false;
            },
            complete: () => {
                this.isLoading = false;
            }
        });
    }
}
