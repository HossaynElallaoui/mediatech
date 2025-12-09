import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { AdminService, User, CreateUserRequest } from '../../services/admin.service';
import { ClientService, Client } from '../../services/client.service';
import { ProductService, Product } from '../../services/product.service';
import { FactureService, Facture } from '../../services/facture.service';

@Component({
    selector: 'app-admin-dashboard',
    standalone: true,
    imports: [CommonModule, FormsModule, RouterModule],
    templateUrl: './admin-dashboard.component.html',
    styleUrls: ['./admin-dashboard.component.css']
})
export class AdminDashboardComponent implements OnInit {
    activeTab = 'users';
    username = '';

    // Data
    users: User[] = [];
    clients: Client[] = [];
    products: Product[] = [];
    factures: Facture[] = [];
    stats: any = { totalUsers: 0, totalRoles: 0 };

    // Forms
    newUser: CreateUserRequest = { username: '', password: '', role: 'USER' };
    newClient: Client = { nom: '', prenom: '', telephone: '' };
    newProduct: Product = { nom: '', prix: 0, description: '' };

    // UI State
    loading = false;
    error: string | null = null;
    successMessage: string | null = null;

    constructor(
        private authService: AuthService,
        private adminService: AdminService,
        private clientService: ClientService,
        private productService: ProductService,
        private factureService: FactureService,
        private router: Router
    ) { }

    ngOnInit(): void {
        this.username = localStorage.getItem('username') || 'Admin';
        this.loadAllData();
    }

    switchTab(tab: string): void {
        this.activeTab = tab;
        this.clearMessages();
    }

    loadAllData(): void {
        this.loadUsers();
        this.loadClients();
        this.loadProducts();
        this.loadFactures();
        this.loadStats();
    }

    // Users Management
    loadUsers(): void {
        this.adminService.getAllUsers().subscribe({
            next: (data) => this.users = data,
            error: (err) => this.showError('Failed to load users')
        });
    }

    createUser(): void {
        if (!this.newUser.username || !this.newUser.password) {
            this.showError('Username and password are required');
            return;
        }

        this.adminService.createUser(this.newUser).subscribe({
            next: (user) => {
                this.showSuccess(`User ${user.username} created successfully!`);
                this.newUser = { username: '', password: '', role: 'USER' };
                this.loadUsers();
            },
            error: (err) => this.showError(err.error || 'Failed to create user')
        });
    }

    deleteUser(id: number): void {
        if (confirm('Are you sure you want to delete this user?')) {
            this.adminService.deleteUser(id).subscribe({
                next: () => {
                    this.showSuccess('User deleted successfully');
                    this.loadUsers();
                },
                error: (err) => this.showError('Failed to delete user')
            });
        }
    }

    // Clients Management
    loadClients(): void {
        this.clientService.getAll().subscribe({
            next: (data) => this.clients = data,
            error: (err) => this.showError('Failed to load clients')
        });
    }

    createClient(): void {
        if (!this.newClient.nom || !this.newClient.prenom) {
            this.showError('Name and first name are required');
            return;
        }

        this.clientService.create(this.newClient).subscribe({
            next: (client) => {
                this.showSuccess(`Client ${client.prenom} ${client.nom} created!`);
                this.newClient = { nom: '', prenom: '', telephone: '' };
                this.loadClients();
            },
            error: (err) => this.showError('Failed to create client')
        });
    }

    deleteClient(id: number): void {
        if (confirm('Are you sure you want to delete this client?')) {
            this.clientService.delete(id!).subscribe({
                next: () => {
                    this.showSuccess('Client deleted successfully');
                    this.loadClients();
                },
                error: (err) => this.showError('Failed to delete client')
            });
        }
    }

    // Products Management
    loadProducts(): void {
        this.productService.getAll().subscribe({
            next: (data) => this.products = data,
            error: (err) => this.showError('Failed to load products')
        });
    }

    createProduct(): void {
        if (!this.newProduct.nom || this.newProduct.prix <= 0) {
            this.showError('Product name and valid price are required');
            return;
        }

        this.productService.create(this.newProduct).subscribe({
            next: (product) => {
                this.showSuccess(`Product ${product.nom} created!`);
                this.newProduct = { nom: '', prix: 0, description: '' };
                this.loadProducts();
            },
            error: (err) => this.showError('Failed to create product')
        });
    }

    deleteProduct(id: number): void {
        if (confirm('Are you sure you want to delete this product?')) {
            this.productService.delete(id!).subscribe({
                next: () => {
                    this.showSuccess('Product deleted successfully');
                    this.loadProducts();
                },
                error: (err) => this.showError('Failed to delete product')
            });
        }
    }

    // Factures Management
    loadFactures(): void {
        this.factureService.getAll().subscribe({
            next: (data) => this.factures = data,
            error: (err) => this.showError('Failed to load factures')
        });
    }

    // Statistics
    loadStats(): void {
        this.adminService.getStats().subscribe({
            next: (data) => this.stats = data,
            error: (err) => console.error('Failed to load stats:', err)
        });
    }

    // UI Helpers
    showError(message: string): void {
        this.error = message;
        this.successMessage = null;
        setTimeout(() => this.error = null, 5000);
    }

    showSuccess(message: string): void {
        this.successMessage = message;
        this.error = null;
        setTimeout(() => this.successMessage = null, 3000);
    }

    clearMessages(): void {
        this.error = null;
        this.successMessage = null;
    }

    logout(): void {
        this.authService.logout();
        this.router.navigate(['/login']);
    }

    goToDashboard(): void {
        this.router.navigate(['/dashboard']);
    }
}
