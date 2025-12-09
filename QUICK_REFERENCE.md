# MediaTech - Quick Reference Card

## 🚀 Starting the Application

### Backend (Spring Boot)
```bash
cd c:\Users\sba3\mediatech
./mvnw spring-boot:run
```
**URL:** http://localhost:8080

### Frontend (Angular)
```bash
cd c:\Users\sba3\mediatech\frontend
npm start
```
**URL:** http://localhost:4200

---

## 🔑 Default Credentials

### Admin Account
- **Username:** `admin`
- **Password:** `admin123`
- **Access:** Full CRUD on all entities

### Regular User
- **Username:** `user`
- **Password:** `user123`
- **Access:** Read-only view of data

---

## 📡 Backend API Endpoints

### Authentication (Public)
- `POST /api/auth/login` - Login
- `POST /api/auth/register` - Register new user

### Admin User Management (ADMIN only)
- `GET /api/admin/users` - List all users
- `POST /api/admin/users` - Create user
- `PUT /api/admin/users/{id}` - Update user
- `DELETE /api/admin/users/{id}` - Delete user
- `GET /api/admin/stats` - System statistics

### Clients
- `GET /api/clients` - List all (ADMIN)
- `POST /api/clients` - Create (ADMIN)
- `GET /api/clients/{id}` - View one (ADMIN & USER)
- `PUT /api/clients/{id}` - Update (ADMIN)
- `DELETE /api/clients/{id}` - Delete (ADMIN)

### Products (Produits)
- `GET /api/produits` - List all (ADMIN & USER)
- `POST /api/produits` - Create (ADMIN)
- `GET /api/produits/{id}` - View one (ADMIN & USER)
- `PUT /api/produits/{id}` - Update (ADMIN)
- `DELETE /api/produits/{id}` - Delete (ADMIN)

### Factures/Invoices
- `GET /api/factures` - List all (ADMIN & USER)
- `POST /api/factures` - Create (ADMIN)
- `GET /api/factures/{id}` - View one (ADMIN & USER)
- `GET /api/factures/client/{clientId}` - By client (ADMIN & USER)
- `PUT /api/factures/{id}` - Update (ADMIN)
- `DELETE /api/factures/{id}` - Delete (ADMIN)

---

## 🎨 Frontend Routes

- `/login` - Login page
- `/register` - Registration page
- `/dashboard` - User dashboard (authenticated)
- `/admin` - **Admin dashboard** (authenticated)

---

## 🛠️ Frontend Services (Angular)

### Importing Services
```typescript
import { AuthService } from './services/auth.service';
import { AdminService } from './services/admin.service';
import { ClientService } from './services/client.service';
import { ProductService } from './services/product.service';
import { FactureService } from './services/facture.service';
```

### Using Services in Components
```typescript
export class MyComponent {
  constructor(
    private authService: AuthService,
    private adminService: AdminService,
    private clientService: ClientService
  ) {}

  ngOnInit() {
    // Get all users
    this.adminService.getAllUsers().subscribe(
      users => console.log(users)
    );

    // Get all clients
    this.clientService.getAll().subscribe(
      clients => console.log(clients)
    );
  }
}
```

---

## 💾 Database

**Type:** H2 File-Based Database  
**Location:** `./data/mediatech.mv.db`

### H2 Console Access
- **URL:** http://localhost:8080/h2-console
- **JDBC URL:** `jdbc:h2:file:./data/mediatech`
- **Username:** `sa`
- **Password:** `password`

---

## 🧪 Quick Test (using curl)

### 1. Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d "{\"username\":\"admin\",\"password\":\"admin123\"}"
```

### 2. Use Token (replace YOUR_TOKEN)
```bash
curl -X GET http://localhost:8080/api/admin/users \
  -H "Authorization: Bearer YOUR_TOKEN"
```

### 3. Create Client
```bash
curl -X POST http://localhost:8080/api/clients \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -H "Content-Type: application/json" \
  -d "{\"nom\":\"Smith\",\"prenom\":\"John\",\"telephone\":\"123456\"}"
```

---

## 📚 Documentation Files

- **COMPLETE_REVIEW_SUMMARY.md** - Full feature overview
- **API_TESTING_GUIDE.md** - Detailed API testing examples
- **FUNCTIONALITY_GUIDE.md** - Complete endpoint documentation
- **frontend/FRONTEND_BACKEND_INTEGRATION.md** - Frontend integration guide

---

## ✅ What's Working

✔️ H2 file-based database (no external DB needed)  
✔️ JWT authentication  
✔️ Role-based access control (ADMIN/USER)  
✔️ Auto-initialization of default users  
✔️ Complete CRUD for Users, Clients, Products, Factures  
✔️ Frontend services for all backend endpoints  
✔️ Admin dashboard component  
✔️ HTTP interceptor for automatic token injection  
✔️ CORS configured for frontend-backend communication  

---

## 🎯 Quick Start Guide

1. **Start Backend:**
   ```bash
   cd c:\Users\sba3\mediatech
   ./mvnw spring-boot:run
   ```

2. **Start Frontend:**
   ```bash
   cd c:\Users\sba3\mediatech\frontend
   npm start
   ```

3. **Access Application:**
   - Open browser: http://localhost:4200
   - Login: `admin / admin123`
   - Navigate to: http://localhost:4200/admin

4. **Test Admin Features:**
   - Create users
   - Manage clients
   - Manage products
   - View factures

---

## 🔧 Troubleshooting

**Backend won't start?**
- Check port 8080 is free
- Delete `./data` folder and restart

**Frontend build errors?**
- Run `npm install` in frontend folder
- Check Node.js version (should be 18+)

**Can't login?**
- Check backend is running
- Check browser console for errors
- Verify CORS settings

**API calls fail?**
- Check JWT token in localStorage
- Check Network tab in browser DevTools
- Verify you're using ADMIN account for admin endpoints

---

**Everything is configured and ready to use!** 🚀
