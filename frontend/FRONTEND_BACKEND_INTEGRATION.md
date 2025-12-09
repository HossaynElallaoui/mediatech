# Frontend-Backend Integration Guide

## ✅ Frontend is Now Connected to Backend!

I've successfully configured your Angular frontend to communicate with the Spring Boot backend. All services are ready to use!

## 🔌 What Was Configured:

### 1. **Backend API Services Created**

All services point to `http://localhost:8080/api` and include:

#### **AuthService** (`auth.service.ts`)
- ✅ Login: `POST /api/auth/login`
- ✅ Register: `POST /api/auth/register`
- ✅ JWT token management
- ✅ Authentication state tracking

#### **AdminService** (`admin.service.ts`) - NEW!
- ✅ Get all users: `GET /api/admin/users`
- ✅ Get user by ID: `GET /api/admin/users/{id}`
- ✅ Create user: `POST /api/admin/users`
- ✅ Update user: `PUT /api/admin/users/{id}`
- ✅ Delete user: `DELETE /api/admin/users/{id}`
- ✅ Get statistics: `GET /api/admin/stats`

#### **ClientService** (`client.service.ts`) - NEW!
- ✅ CRUD operations for clients
- ✅ All endpoints: `/api/clients`

#### **ProductService** (`product.service.ts`) - NEW!
- ✅ CRUD operations for products
- ✅ All endpoints: `/api/produits`

#### **FactureService** (`facture.service.ts`) - NEW!
- ✅ CRUD operations for invoices
- ✅ Get by client: `GET /api/factures/client/{clientId}`
- ✅ All endpoints: `/api/factures`

### 2. **HTTP Interceptor**

The `AuthInterceptor` automatically adds JWT tokens to all HTTP requests:
```typescript
headers: { 'Authorization': 'Bearer YOUR_TOKEN' }
```

### 3. **CORS Configuration**

Backend is configured to accept requests from:
- `http://localhost:4200` (Angular default)
- `http://localhost:3000`

### 4. **Admin Dashboard Component** - NEW!

Created a complete admin dashboard at `/admin` featuring:
- ✅ User management (create, view, delete)
- ✅ Client management (create, view, delete)
- ✅ Product management (create, view, delete)
- ✅ Facture/Invoice viewing
- ✅ Real-time statistics
- ✅ Modern, responsive UI with tabs

## 🚀 How to Use the Services

### Example 1: Login from Component

```typescript
import { AuthService } from './services/auth.service';

export class LoginComponent {
  constructor(private authService: AuthService) {}

  login() {
    this.authService.login({ 
      username: 'admin', 
      password: 'admin123' 
    }).subscribe({
      next: (response) => {
        console.log('Logged in!', response);
        // Token is automatically stored
      },
      error: (err) => {
        console.error('Login failed', err);
      }
    });
  }
}
```

### Example 2: Get All Users (Admin Only)

```typescript
import { AdminService } from './services/admin.service';

export class MyComponent {
  users = [];

  constructor(private adminService: AdminService) {}

  loadUsers() {
    this.adminService.getAllUsers().subscribe({
      next: (users) => {
        this.users = users;
        console.log('Users:', users);
      },
      error: (err) => {
        console.error('Failed to load users', err);
      }
    });
  }
}
```

### Example 3: Create a Client

```typescript
import { ClientService } from './services/client.service';

export class MyComponent {
  constructor(private clientService: ClientService) {}

  createClient() {
    const newClient = {
      nom: 'Doe',
      prenom: 'John',
      telephone: '1234567890'
    };

    this.clientService.create(newClient).subscribe({
      next: (client) => {
        console.log('Client created:', client);
      },
      error: (err) => {
        console.error('Failed to create client', err);
      }
    });
  }
}
```

### Example 4: Create a Product

```typescript
import { ProductService } from './services/product.service';

export class MyComponent {
  constructor(private productService: ProductService) {}

  createProduct() {
    const newProduct = {
      nom: 'Laptop',
      prix: 999.99,
      description: 'High-performance laptop'
    };

    this.productService.create(newProduct).subscribe({
      next: (product) => {
        console.log('Product created:', product);
      },
      error: (err) => {
        console.error('Failed to create product', err);
      }
    });
  }
}
```

## 📱 Available Routes

- `/login` - Login page
- `/register` - Registration page
- `/dashboard` - User dashboard (requires authentication)
- `/admin` - **NEW!** Admin dashboard (requires authentication)

## 🧪 Testing the Integration

### 1. Make sure both servers are running:

**Backend (Spring Boot):**
```bash
cd mediatech
./mvnw spring-boot:run
```
Running on: `http://localhost:8080`

**Frontend (Angular):**
```bash
cd frontend
npm start
```
Running on: `http://localhost:4200`

### 2. Test the Admin Dashboard:

1. Navigate to `http://localhost:4200`
2. Login with:
   - Username: `admin`
   - Password: `admin123`
3. After login, navigate to `http://localhost:4200/admin`
4. You should see the admin dashboard with:
   - Statistics cards
   - User management tab
   - Client management tab
   - Product management tab
   - Factures tab

### 3. Test API Calls from Browser Console:

Open browser console on `http://localhost:4200` and run:

```javascript
// Get the auth service
const authService = ng.probe(document.body).componentInstance.authService;

// Login
authService.login({username: 'admin', password: 'admin123'}).subscribe(
  response => console.log('Success:', response),
  error => console.error('Error:', error)
);
```

## 🔧 Configuration Summary

### Backend Configuration (Already Done)
```java
// SecurityConfig.java - CORS settings
configuration.setAllowedOrigins(Arrays.asList(
  "http://localhost:4200", 
  "http://localhost:3000"
));
configuration.setAllowedMethods(Arrays.asList(
  "GET", "POST", "PUT", "DELETE", "OPTIONS"
));
configuration.setAllowedHeaders(Arrays.asList(
  "Authorization", "Content-Type"
));
```

### Frontend Configuration (Already Done)
```typescript
// Services point to:
private readonly API_URL = 'http://localhost:8080/api/...';

// HTTP Interceptor adds token automatically:
headers.set('Authorization', `Bearer ${token}`)
```

## 📋 Checklist - All Verified ✅

- ✅ **AuthService** connects to `/api/auth`
- ✅ **AdminService** connects to `/api/admin`  
- ✅ **ClientService** connects to `/api/clients`
- ✅ **ProductService** connects to `/api/produits`
- ✅ **FactureService** connects to `/api/factures`
- ✅ **JWT tokens** automatically included in requests
- ✅ **CORS** properly configured
- ✅ **Error handling** implemented in services
- ✅ **TypeScript interfaces** defined for type safety
- ✅ **Admin Dashboard** component created
- ✅ **Routing** configured for /admin

## 🎯 Ready-to-Use Examples in Admin Dashboard

The `AdminDashboardComponent` demonstrates:
- Creating users with role assignment
- Managing clients
- Managing products
- Viewing factures
- Deleting records
- Real-time error/success messages
- Loading states
- Form validation

## 🔍 Troubleshooting

### If you get CORS errors:
1. Make sure backend is running on port 8080
2. Check SecurityConfig.java has correct allowed origins
3. Clear browser cache

### If requests fail with 401 Unauthorized:
1. Check if you're logged in
2. Check if token is stored: `localStorage.getItem('token')`
3. Check if token is being sent in headers (Network tab)

### If requests fail with 403 Forbidden:
1. You need ADMIN role for admin endpoints
2. Login with: `admin / admin123`

## 📝 Next Steps

You can now:
1. Navigate to `/admin` to see the full dashboard
2. Use any of the services in your components
3. Extend the dashboard with more features
4. Add more custom endpoints as needed

---

**Your frontend is fully connected to the backend and ready to use!** 🎉
