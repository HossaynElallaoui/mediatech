import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { ProductService, Product } from '../../services/product.service';

@Component({
    selector: 'app-dashboard',
    standalone: true,
    imports: [CommonModule, RouterModule],
    templateUrl: './dashboard.component.html',
    styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
    username: string = '';
    products: Product[] = [];
    loading = false;
    error: string | null = null;

    constructor(
        private authService: AuthService,
        private productService: ProductService,
        private router: Router
    ) { }

    ngOnInit(): void {
        this.username = localStorage.getItem('username') || 'User';
        this.loadProducts();
    }

    loadProducts(): void {
        this.loading = true;
        this.productService.getAll().subscribe({
            next: (data) => {
                this.products = data;
                this.loading = false;
            },
            error: (err) => {
                this.error = 'Failed to load products';
                this.loading = false;
                console.error('Error loading products:', err);
            }
        });
    }

    goToAdmin(): void {
        this.router.navigate(['/admin']);
    }

    logout(): void {
        this.authService.logout();
        this.router.navigate(['/login']);
    }
}
