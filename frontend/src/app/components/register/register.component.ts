import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { RegisterRequest } from '../../models/auth.models';

@Component({
    selector: 'app-register',
    standalone: true,
    imports: [CommonModule, FormsModule, RouterModule],
    templateUrl: './register.component.html',
    styleUrls: ['./register.component.css']
})
export class RegisterComponent {
    userData: RegisterRequest = { username: '', password: '' };
    confirmPassword = '';
    errorMessage = '';
    successMessage = '';
    isLoading = false;

    constructor(
        private authService: AuthService,
        private router: Router
    ) { }

    onSubmit(): void {
        this.errorMessage = '';
        this.successMessage = '';

        // Validation
        if (!this.userData.username || !this.userData.password) {
            this.errorMessage = 'Please fill in all fields';
            return;
        }

        if (this.userData.password !== this.confirmPassword) {
            this.errorMessage = 'Passwords do not match';
            return;
        }

        if (this.userData.password.length < 6) {
            this.errorMessage = 'Password must be at least 6 characters';
            return;
        }

        this.isLoading = true;

        this.authService.register(this.userData).subscribe({
            next: (response) => {
                this.successMessage = 'Registration successful! Redirecting to login...';
                setTimeout(() => {
                    this.router.navigate(['/login']);
                }, 2000);
            },
            error: (error) => {
                console.error('Registration error:', error);
                this.errorMessage = error.error || 'Registration failed. Username may already exist.';
                this.isLoading = false;
            },
            complete: () => {
                this.isLoading = false;
            }
        });
    }
}
