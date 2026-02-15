# Testing Guide - FoodLink Application

## ✅ Fixed Issues

1. **RecommendationService** - Added `@RequiredArgsConstructor` annotation for proper dependency injection
2. All DTOs, entities, and mappers are properly configured
3. API endpoints are correctly set up

## 🧪 Complete Testing Flow

### 1. Start the Application

**Terminal 1 - Backend:**
```bash
cd backend
docker compose up -d  # Start PostgreSQL
mvn spring-boot:run   # Run Spring Boot
```

**Terminal 2 - Frontend:**
```bash
cd frontend
npm install          # First time only
npm run dev          # Run Next.js
```

### 2. Test Backend API (Using Swagger)

Open: http://localhost:8080/swagger-ui.html

#### Test 1: Register a Creator
```
POST /api/v1/auth/register

Body:
{
  "email": "test@example.com",
  "password": "Test1234!",
  "displayName": "Delhi Food Explorer",
  "slug": "delhifoodie",
  "instagramHandle": "delhifoodie",
  "bio": "Exploring Delhi's best food 🍽️"
}

Expected: 201 Created with token and creator data
```

#### Test 2: Login
```
POST /api/v1/auth/login

Body:
{
  "email": "test@example.com",
  "password": "Test1234!"
}

Expected: 200 OK with token
Copy the token for next requests!
```

#### Test 3: Create Recommendation (with token)
```
POST /api/v1/creator/recommendations
Authorization: Bearer YOUR_TOKEN_HERE

Body:
{
  "dishName": "Butter Chicken",
  "restaurantName": "Moti Mahal",
  "area": "Daryaganj",
  "creatorQuote": "Best butter chicken in Delhi!",
  "dishCategory": "FINE_DINING",
  "priceRange": "MID",
  "tags": ["non-veg", "must-try"]
}

Expected: 201 Created with recommendation data
```

#### Test 4: Get Public Recommendations
```
GET /api/v1/public/creators/delhifoodie/recommendations

Expected: 200 OK with array of recommendations
```

### 3. Test Frontend Flow

#### Step 1: Register via UI
1. Go to http://localhost:3000
2. Click "I'm a Creator"
3. Fill registration form:
   - Display Name: "Delhi Food Explorer"
   - Slug: "delhifoodie" (auto-generated)
   - Email: "test2@example.com"
   - Instagram: "delhifoodie"
   - Password: "Test1234!"
4. Click "Create Account"
5. **Expected:** Redirect to dashboard

#### Step 2: Add Recommendation
1. On dashboard, click "Add Recommendation"
2. Fill form:
   - Dish Name: "Mutton Biryani"
   - Restaurant: "Al Jawahar"
   - Area: "Jama Masjid"
   - Quote: "Best biryani in Old Delhi!"
   - Category: Select "🍛 Biryani"
   - Price Range: "₹₹ Mid-Range"
3. Click "Create Recommendation"
4. **Expected:** Success message, redirect to dashboard

#### Step 3: View Public Page
1. On dashboard, click "View Public Page"
2. **Expected:** See your creator page with recommendation
3. Try searching for "biryani"
4. **Expected:** Recommendation appears in search results

## 🔍 Common Issues & Solutions

### Backend Issues

**Issue: "Cannot find symbol" errors**
```bash
cd backend
mvn clean install
```

**Issue: Database connection failed**
```bash
# Check if PostgreSQL is running
docker compose ps

# Restart database
docker compose down
docker compose up -d
```

**Issue: Port 8080 already in use**
```bash
# Find process using port 8080
lsof -i :8080
# Kill it or change port in application.yml
```

### Frontend Issues

**Issue: Module not found**
```bash
cd frontend
rm -rf node_modules package-lock.json
npm install
```

**Issue: API connection errors**
Check:
1. Backend is running on port 8080
2. `.env.local` has correct API URL: `NEXT_PUBLIC_API_URL=http://localhost:8080/api/v1`
3. CORS is enabled in backend

**Issue: "localStorage is not defined"**
This is normal during server-side rendering. The code handles it properly.

## 📊 Database Verification

Check if data is being saved:

```bash
# Connect to PostgreSQL
docker compose exec postgres psql -U postgres -d foodlink_db

# Check creators
SELECT id, display_name, slug, email FROM creators;

# Check recommendations
SELECT id, dish_name, restaurant_name, area FROM recommendations;

# Exit
\q
```

## ✅ Success Criteria

Your application is working correctly if:

- ✅ Backend starts without errors
- ✅ Frontend starts without errors
- ✅ Can register a new creator
- ✅ Can login with credentials
- ✅ Dashboard shows creator stats
- ✅ Can create a recommendation
- ✅ Recommendation appears in dashboard list
- ✅ Public page shows recommendation
- ✅ Search works on public page
- ✅ Database has the data

## 🐛 Debug Tips

### Enable Debug Logging (Backend)

Edit `backend/src/main/resources/application.yml`:
```yaml
logging:
  level:
    com.foodlink: DEBUG
    org.springframework.web: DEBUG
    org.hibernate.SQL: DEBUG
```

### Check Browser Console (Frontend)

Open browser DevTools (F12) and check:
1. Console tab - for JavaScript errors
2. Network tab - for API request/response
3. Application tab - for localStorage token

### Common Error Messages

**"Creator not found"**
- Token might be expired or invalid
- Try logging in again

**"Slug already taken"**
- Choose a different slug
- Or login with existing account

**"Invalid email or password"**
- Check credentials
- Password must be at least 8 characters

## 📝 Test Data Examples

### Good Test Recommendations

```json
{
  "dishName": "Chole Bhature",
  "restaurantName": "Sitaram Diwan Chand",
  "area": "Paharganj",
  "creatorQuote": "Fluffy bhature with tangy chole!",
  "dishCategory": "STREET_FOOD",
  "priceRange": "BUDGET"
}
```

```json
{
  "dishName": "Chicken Momos",
  "restaurantName": "Dolma Aunty",
  "area": "Lajpat Nagar",
  "creatorQuote": "These momos are LEGENDARY",
  "dishCategory": "MOMOS",
  "priceRange": "BUDGET",
  "tags": ["street-food", "must-try", "spicy"]
}
```

## 🎯 Quick Smoke Test

Run this to verify everything works:

1. **Backend health:** http://localhost:8080/api/v1/public/creators/test
   - Should return 404 (expected, no creator with slug "test")

2. **Frontend loads:** http://localhost:3000
   - Should show landing page

3. **Swagger works:** http://localhost:8080/swagger-ui.html
   - Should show API documentation

4. **Database works:**
   ```bash
   docker compose exec postgres psql -U postgres -d foodlink_db -c "SELECT 1;"
   ```
   - Should return: `?column? ---------- 1`

If all 4 pass, your application is healthy! ✅

## 🚀 Ready for MVP

Once testing passes, you're ready to:
1. Deploy backend to Railway/Heroku
2. Deploy frontend to Vercel
3. Get real creators to test
4. Collect feedback
5. Iterate!

---

**Need more help?** Check the logs:
- Backend: Check terminal where `mvn spring-boot:run` is running
- Frontend: Check terminal where `npm run dev` is running
- Database: `docker compose logs postgres`
