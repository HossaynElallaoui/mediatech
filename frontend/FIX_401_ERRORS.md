# 🔧 Fixing 401 Errors - Quick Guide

## Issue:
All API calls are returning 401 (Unauthorized) errors.

## Solution Applied:

I've fixed the HTTP interceptor configuration in `app.config.ts` by adding `withInterceptorsFromDi()`.

### What Changed:
```typescript
// Before:
provideHttpClient()

// After:
provideHttpClient(withInterceptorsFromDi())
```

## Steps to Test:

### 1. **Restart the Frontend**
The frontend is running but needs to reload with the new configuration:

Press `Ctrl+C` in the terminal running `npm start`, then:
```bash
npm start
```

### 2. **Clear Browser Cache**
- Open browser DevTools (F12)
- Right-click refresh button
- Select "Empty Cache and Hard Reload"
- Or just clear localStorage: `localStorage.clear()`

### 3. **Test Login**
1. Go to `http://localhost:4200`
2. Open browser console (F12)
3. Try to login with: `admin / admin123`
4. Check Network tab - you should see:
   - POST to `/api/auth/login` returns 200 (success)
   - Response contains `accessToken`

### 4. **Verify Token is Stored**
After successful login, in browser console type:
```javascript
localStorage.getItem('token')
```
You should see the JWT token.

### 5. **Test Protected Endpoints**
After login, subsequent API calls should include the token in headers:
```
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

## If Still Getting 401:

### Check 1: Is Backend Running?
```bash
# In another terminal
curl http://localhost:8080/api/auth/debug/users
```
Should return user and role counts.

### Check 2: Can You Login Directly?
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'
```
Should return a token.

### Check 3: Browser Console
Open F12 and check:
1. **Console tab** - Any JavaScript errors?
2. **Network tab** - Click on failed request
   - What's the Request URL?
   - Are there headers?
   - Is Authorization header present?

## Common Issues & Fixes:

### Issue: Login itself returns 401
**Fix:** Backend might not be running
```bash
cd c:\Users\sba3\mediatech
./mvnw spring-boot:run
```

### Issue: Login works, but other endpoints return 401
**Fix:** Token not being sent
- Check if interceptor is working
- Verify token is in localStorage
- Check Network tab for Authorization header

### Issue: CORS errors
**Fix:** Backend CORS configuration
- Should already be configured for localhost:4200
- Check SecurityConfig.java

## Quick Debug Commands:

### In Browser Console (F12):
```javascript
// Check if token exists
console.log('Token:', localStorage.getItem('token'));

// Check if username is stored
console.log('Username:', localStorage.getItem('username'));

// Clear all and retry
localStorage.clear();
location.reload();
```

### Backend Check:
```bash
# Check if backend is running
curl http://localhost:8080/api/auth/debug/users

# Test login
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'
```

## Expected Behavior After Fix:

1. **Login** → Returns token → Stores in localStorage
2. **Navigate to /admin** → Interceptor adds token to requests
3. **API calls** → Include `Authorization: Bearer <token>` header
4. **Backend** → Validates token → Returns data (200)

---

**After restarting the frontend with the fix, everything should work!** ✅

If you're still having issues after restart, let me know what you see in the browser console (F12).
