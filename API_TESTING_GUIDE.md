# API Testing Guide - MediaTech Application

This document provides step-by-step examples for testing all API functionality.

## Prerequisites
- Application running on `http://localhost:8080`
- Tool to make HTTP requests (curl, Postman, or your frontend)

## Step 1: Login as Admin

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'
```

**Response:**
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTYxMjM0NTY3OCwiZXhwIjoxNjEyMzQ5Mjc4fQ..."
}
```

**Save the token** - You'll need it for all subsequent requests!

## Step 2: View All Users (Admin Only)

```bash
curl -X GET http://localhost:8080/api/admin/users \
  -H "Authorization: Bearer YOUR_TOKEN_HERE"
```

**Expected Response:**
```json
[
  {
    "id": 1,
    "username": "admin",
    "roles": ["ADMIN"]
  },
  {
    "id": 2,
    "username": "user",
    "roles": ["USER"]
  }
]
```

## Step 3: Create a New User (Admin Only)

```bash
curl -X POST http://localhost:8080/api/admin/users \
  -H "Authorization: Bearer YOUR_TOKEN_HERE" \
  -H "Content-Type: application/json" \
  -d '{
    "username": "manager",
    "password": "manager123",
    "role": "ADMIN"
  }'
```

**Expected Response:**
```json
{
  "id": 3,
  "username": "manager",
  "roles": ["ADMIN"]
}
```

## Step 4: Create a Client (Admin Only)

```bash
curl -X POST http://localhost:8080/api/clients \
  -H "Authorization: Bearer YOUR_TOKEN_HERE" \
  -H "Content-Type: application/json" \
  -d '{
    "nom": "Smith",
    "prenom": "Alice",
    "telephone": "+1234567890"
  }'
```

**Expected Response:**
```json
{
  "id": 1,
  "nom": "Smith",
  "prenom": "Alice",
  "telephone": "+1234567890",
  "factures": []
}
```

## Step 5: View All Clients

```bash
curl -X GET http://localhost:8080/api/clients \
  -H "Authorization: Bearer YOUR_TOKEN_HERE"
```

## Step 6: Create a Product (Admin Only)

```bash
curl -X POST http://localhost:8080/api/produits \
  -H "Authorization: Bearer YOUR_TOKEN_HERE" \
  -H "Content-Type: application/json" \
  -d '{
    "nom": "MacBook Pro",
    "prix": 2499.99,
    "description": "16-inch MacBook Pro with M2 chip"
  }'
```

**Expected Response:**
```json
{
  "id": 1,
  "nom": "MacBook Pro",
  "prix": 2499.99,
  "description": "16-inch MacBook Pro with M2 chip"
}
```

## Step 7: View All Products

```bash
curl -X GET http://localhost:8080/api/produits \
  -H "Authorization: Bearer YOUR_TOKEN_HERE"
```

## Step 8: Update a User (Admin Only)

```bash
curl -X PUT http://localhost:8080/api/admin/users/3 \
  -H "Authorization: Bearer YOUR_TOKEN_HERE" \
  -H "Content-Type: application/json" \
  -d '{
    "username": "manager",
    "password": "newpassword123",
    "role": "USER"
  }'
```

## Step 9: Get System Statistics (Admin Only)

```bash
curl -X GET http://localhost:8080/api/admin/stats \
  -H "Authorization: Bearer YOUR_TOKEN_HERE"
```

**Expected Response:**
```json
{
  "totalUsers": 3,
  "totalRoles": 2
}
```

## Step 10: Test Regular User Access

### Login as Regular User
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"user","password":"user123"}'
```

### Try to View Products (Should Work)
```bash
curl -X GET http://localhost:8080/api/produits \
  -H "Authorization: Bearer USER_TOKEN_HERE"
```

### Try to Create Product (Should Fail - User doesn't have permission)
```bash
curl -X POST http://localhost:8080/api/produits \
  -H "Authorization: Bearer USER_TOKEN_HERE" \
  -H "Content-Type: application/json" \
  -d '{
    "nom": "Test Product",
    "prix": 99.99,
    "description": "This should fail"
  }'
```

**Expected Response:** 403 Forbidden

## Step 11: Create a Facture/Invoice (Admin Only)

```bash
curl -X POST http://localhost:8080/api/factures \
  -H "Authorization: Bearer ADMIN_TOKEN_HERE" \
  -H "Content-Type: application/json" \
  -d '{
    "ref": "INV-001",
    "date": "2025-12-09T00:00:00.000+00:00",
    "client": {
      "id": 1
    },
    "produits": []
  }'
```

## Step 12: View All Factures

```bash
curl -X GET http://localhost:8080/api/factures \
  -H "Authorization: Bearer YOUR_TOKEN_HERE"
```

## Step 13: View Factures by Client ID

```bash
curl -X GET http://localhost:8080/api/factures/client/1 \
  -H "Authorization: Bearer YOUR_TOKEN_HERE"
```

## Step 14: Delete a User (Admin Only)

```bash
curl -X DELETE http://localhost:8080/api/admin/users/3 \
  -H "Authorization: Bearer YOUR_TOKEN_HERE"
```

**Expected Response:**
```
User deleted successfully
```

## Step 15: Register a New User (Public Endpoint)

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "newuser",
    "password": "newpass123"
  }'
```

**Expected Response:**
```
User registered successfully!
```

**Note:** Registered users get the "USER" role by default.

## Testing with Postman

If you prefer using Postman:

1. **Import these requests** or create them manually
2. **Create an environment variable** for the token:
   - Variable name: `auth_token`
   - Set it after logging in
3. **Use `{{auth_token}}`** in the Authorization header as: `Bearer {{auth_token}}`

## Verification Checklist

- ✅ Admin can login
- ✅ Admin can view all users
- ✅ Admin can create users with any role
- ✅ Admin can update users
- ✅ Admin can delete users
- ✅ Admin can create clients
- ✅ Admin can view/update/delete clients
- ✅ Admin can create products
- ✅ Admin can view/update/delete products
- ✅ Admin can create factures
- ✅ Admin can view/update/delete factures
- ✅ Regular user can login
- ✅ Regular user can view data (but not modify)
- ✅ Regular user cannot access admin endpoints
- ✅ Database persists data (H2 file-based)

## Database Verification

You can also verify the database directly:

1. Open H2 Console: `http://localhost:8080/h2-console`
2. Enter connection details:
   - JDBC URL: `jdbc:h2:file:./data/mediatech`
   - Username: `sa`
   - Password: `password`
3. Run SQL queries:
   ```sql
   SELECT * FROM users;
   SELECT * FROM roles;
   SELECT * FROM clients;
   SELECT * FROM produits;
   SELECT * FROM factures;
   ```

## Troubleshooting

### 401 Unauthorized
- Check if your token is valid
- Token may have expired - login again
- Check if you included "Bearer " before the token

### 403 Forbidden
- You don't have permission for this operation
- Check if you're using an ADMIN token for admin-only endpoints

### 404 Not Found
- Check the endpoint URL
- Verify the resource ID exists

---

**All functionality has been tested and verified!** ✅
