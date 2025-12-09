# 🔍 Debugging 401 Errors - With Console Logs

## I've Added Debug Logging

I've added `console.log` statements to:
- ✅ AuthService (login, token storage, token retrieval)
- ✅ AuthInterceptor (request interception, token addition)
- ✅ Made tokenType optional in AuthResponse

## Steps to Debug:

### 1. Open Browser Console (F12)
Make sure the console is open BEFORE you try to login.

### 2. Clear Everything
In the console, run:
```javascript
localStorage.clear();
location.reload();
```

### 3. Try to Login
1. Enter: `admin / admin123`
2. Click "Sign In"
3. Watch the console output

### 4. What You Should See:

**If login is successful:**
```
AuthService initialized
Login attempt for user: admin
Intercepting request: http://localhost:8080/api/auth/login
No token, sending request without Authorization header
Login response received: {accessToken: "eyJ...", tokenType: "Bearer "}
Token stored successfully
Token set in localStorage
```

**If login fails with 401:**
```
AuthService initialized
Login attempt for user: admin
Intercepting request: http://localhost:8080/api/auth/login
No token, sending request without Authorization header
ERROR: 401
```

**After successful login, when making other requests:**
```
Intercepting request: http://localhost:8080/api/admin/users
Getting token: Token exists
Adding token to request headers
Request headers: Bearer eyJ...
```

## What Each Log Means:

| Log Message | Meaning |
|-------------|---------|
| `AuthInterceptor created` | Interceptor is loaded ✅ |
| `Intercepting request` | Every HTTP request is being intercepted ✅ |
| `Adding token to request headers` | Token is being added ✅ |
| `No token, sending request without...` | No token yet (normal for login) ✅ |
| `Token stored successfully` | Login worked ✅ |
| `ERROR: 401` | Request was rejected by backend ❌ |

## Common Issues & Solutions:

### Issue 1: "AuthInterceptor created" doesn't appear
**Problem:** Interceptor not loaded
**Solution:** Check app.config.ts has `withInterceptorsFromDi()`

### Issue 2: "Intercepting request" doesn't appear
**Problem:** Interceptor not working
**Solution:** Restart npm start

### Issue 3: Login returns 401
**Problem:** Backend not accepting credentials
**Solution:** Verify backend is running, check username/password

### Issue 4: Login succeeds but other endpoints return 401
**Problem:** Token not being sent
**Look for:** 
- "Adding token to request headers" should appear
- "Request headers: Bearer..." should show the token
- If missing, interceptor isn't working

### Issue 5: CORS error in console
**Problem:** Backend CORS configuration
**Look for:** Red text about "CORS policy"
**Solution:** Backend should allow localhost:4200

## Manual Testing in Console:

### After login, check token:
```javascript
// Should return the JWT token
localStorage.getItem('token')

// Should return username
localStorage.getItem('username')
```

### Test an API call manually:
```javascript
// Get the token
const token = localStorage.getItem('token');

// Make a test request
fetch('http://localhost:8080/api/admin/users', {
  headers: {
    'Authorization': `Bearer ${token}`,
    'Content-Type': 'application/json'
  }
})
.then(r => r.json())
.then(data => console.log('Success:', data))
.catch(err => console.error('Error:', err));
```

## Network Tab Analysis:

1. Open DevTools (F12)
2. Go to **Network** tab
3. Try to login
4. Click on the "login" request
5. Check:
   - **Status**: Should be 200 (not 401)
   - **Response**: Should have accessToken
   - **Request Payload**: Should have username and password

For subsequent requests:
6. Click on any request to `/api/admin/users` or `/api/clients`
7. Check **Request Headers**:
   - Should see: `Authorization: Bearer eyJ...`

## What to Tell Me:

Please share:
1. ✅ What you see in the Console (copy/paste the logs)
2. ✅ Does "AuthInterceptor created" appear?
3. ✅ Does login return 200 or 401?
4. ✅ Do you see the token being added to headers?
5. ✅ Any red error messages?

This will help me identify the exact issue!

---

**Remember:** The frontend npm start should be running with the latest code!
