# MediaTech Application - Complete Functionality Guide

## Database Configuration
The application now uses **H2 file-based database** stored at: `./data/mediatech`

This means:
- ✅ Database is a file in the project
- ✅ Data persists between application restarts
- ✅ No need for external MySQL server
- ✅ H2 Console available at: http://localhost:8080/h2-console

### H2 Console Access
- JDBC URL: `jdbc:h2:file:./data/mediatech`
- Username: `sa`
- Password: `password`

## Default Users

### Admin User
- **Username**: `admin`
- **Password**: `admin123`
- **Role**: ADMIN
- **Permissions**: Full access to all endpoints

### Regular User
- **Username**: `user`
- **Password**: `user123`
- **Role**: USER
- **Permissions**: Read-only access to most endpoints

## API Endpoints

### Authentication Endpoints (Public)
1. **POST /api/auth/login**
   - Login with username and password
   - Returns JWT token

2. **POST /api/auth/register**
   - Register new user (default USER role)
   - Body: `{"username": "string", "password": "string"}`

3. **GET /api/auth/debug/users**
   - Debug endpoint showing user/role counts

### Admin User Management (ADMIN only)
1. **GET /api/admin/users**
   - Get all users with their roles

2. **GET /api/admin/users/{id}**
   - Get specific user by ID

3. **POST /api/admin/users**
   - Create new user
   - Body: `{"username": "string", "password": "string", "role": "ADMIN"/"USER"}`

4. **PUT /api/admin/users/{id}**
   - Update user information
   - Can change username, password, and role

5. **DELETE /api/admin/users/{id}**
   - Delete user

6. **GET /api/admin/stats**
   - Get system statistics (user count, role count)

### Client Management
1. **POST /api/clients** (ADMIN only)
   - Create new client
   - Body: `{"nom": "string", "prenom": "string", "telephone": "string"}`

2. **GET /api/clients** (ADMIN only)
   - Get all clients

3. **GET /api/clients/{id}** (ADMIN & USER)
   - Get specific client

4. **PUT /api/clients/{id}** (ADMIN only)
   - Update client

5. **DELETE /api/clients/{id}** (ADMIN only)
   - Delete client

### Product Management (Produits)
1. **POST /api/produits** (ADMIN only)
   - Create new product
   - Body: `{"nom": "string", "prix": number, "description": "string"}`

2. **GET /api/produits** (ADMIN & USER)
   - Get all products

3. **GET /api/produits/{id}** (ADMIN & USER)
   - Get specific product

4. **PUT /api/produits/{id}** (ADMIN only)
   - Update product

5. **DELETE /api/produits/{id}** (ADMIN only)
   - Delete product

### Factures/Invoice Management
1. **POST /api/factures** (ADMIN only)
   - Create new invoice
   - Body: `{"ref": "string", "date": "date", "client": {...}, "produits": [...]}`

2. **GET /api/factures** (ADMIN & USER)
   - Get all invoices

3. **GET /api/factures/{id}** (ADMIN & USER)
   - Get specific invoice

4. **GET /api/factures/client/{clientId}** (ADMIN & USER)
   - Get all invoices for a specific client

5. **PUT /api/factures/{id}** (ADMIN only)
   - Update invoice

6. **DELETE /api/factures/{id}** (ADMIN only)
   - Delete invoice

## Admin Capabilities Summary

As ADMIN, you can:
✅ View all users in the system
✅ Create new users (with ADMIN or USER role)
✅ Update existing users (change username, password, role)
✅ Delete users
✅ Create, read, update, delete clients
✅ Create, read, update, delete products
✅ Create, read, update, delete invoices
✅ View all information across all entities
✅ Access system statistics

## Testing the Application

### 1. Start the Application
```bash
./mvnw spring-boot:run
```

### 2. Test Login (using curl or Postman)
```bash
# Login as admin
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'
```

### 3. Use the returned token
Save the token from the response and use it in subsequent requests:
```bash
curl -X GET http://localhost:8080/api/admin/users \
  -H "Authorization: Bearer YOUR_TOKEN_HERE"
```

### 4. Create a new user
```bash
curl -X POST http://localhost:8080/api/admin/users \
  -H "Authorization: Bearer YOUR_TOKEN_HERE" \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","password":"test123","role":"USER"}'
```

### 5. Create a new client
```bash
curl -X POST http://localhost:8080/api/clients \
  -H "Authorization: Bearer YOUR_TOKEN_HERE" \
  -H "Content-Type: application/json" \
  -d '{"nom":"Doe","prenom":"John","telephone":"1234567890"}'
```

### 6. Create a new product
```bash
curl -X POST http://localhost:8080/api/produits \
  -H "Authorization: Bearer YOUR_TOKEN_HERE" \
  -H "Content-Type: application/json" \
  -d '{"nom":"Product 1","prix":99.99,"description":"Test product"}'
```

## Key Features

1. **File-based Database**: H2 database stored in `./data/mediatech.mv.db`
2. **Auto-initialization**: Default admin and user accounts created on startup
3. **JWT Authentication**: Secure token-based authentication
4. **Role-based Access Control**: ADMIN and USER roles with different permissions
5. **Complete CRUD**: Full create, read, update, delete for all entities
6. **Admin Dashboard**: Admin can manage all users and access all data

## Security Notes

- All endpoints except `/api/auth/**` require authentication
- ADMIN role required for creating/updating/deleting most entities
- USER role can view most data but cannot modify
- Passwords are encrypted using BCrypt
- JWT tokens expire (configure in JwtGenerator if needed)
