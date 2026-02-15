# 🎉 FoodLink - Complete Full-Stack Application

Your full-stack food recommendation platform is ready!

## 📁 Project Structure

```
food_guide/
├── backend/                    # Spring Boot API
│   ├── src/
│   │   └── main/
│   │       ├── java/com/foodlink/
│   │       └── resources/
│   ├── pom.xml
│   ├── docker-compose.yml
│   └── README.md
│
├── frontend/                   # Next.js + Tailwind
│   ├── src/
│   │   ├── app/
│   │   │   ├── [slug]/        # Creator pages
│   │   │   ├── layout.tsx
│   │   │   └── page.tsx       # Home
│   │   ├── components/
│   │   └── lib/
│   ├── package.json
│   └── README.md
│
└── README.md                   # This file
```

## 🚀 Quick Start

### 1. Start Backend

```bash
cd backend

# Start database
docker compose up -d

# Run Spring Boot
mvn spring-boot:run
```

Backend: http://localhost:8080
Swagger: http://localhost:8080/swagger-ui.html

### 2. Start Frontend

```bash
cd frontend

# Install dependencies (first time only)
npm install

# Run Next.js
npm run dev
```

Frontend: http://localhost:3000

## ✅ What's Included

### Backend (Spring Boot)
- ✅ 46 Java files
- ✅ JWT Authentication
- ✅ 8 REST API endpoints
- ✅ PostgreSQL + Elasticsearch
- ✅ Database migrations
- ✅ Swagger documentation
- ✅ Analytics tracking

### Frontend (Next.js)
- ✅ Landing page
- ✅ Creator profile pages
- ✅ Search functionality
- ✅ Responsive design
- ✅ API integration
- ✅ Analytics tracking
- ✅ TypeScript + Tailwind CSS

## 🎯 Core Features

1. **For Creators:**
   - Register and login
   - Create food recommendations
   - Get custom page: `foodlink.com/yourname`
   - Track analytics

2. **For Viewers:**
   - Discover creators
   - Search recommendations
   - View Instagram reels
   - Get Google Maps directions

## 📝 Testing the Full Stack

### 1. Create a Creator Account

```bash
# Using curl
curl -X POST http://localhost:8080/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "creator@example.com",
    "password": "Test1234!",
    "displayName": "Delhi Food Explorer",
    "slug": "delhifoodie",
    "instagramHandle": "delhifoodie",
    "bio": "Exploring Delhi food spots 🍽️"
  }'
```

### 2. Create Recommendations

```bash
# Login first to get token
TOKEN="your-jwt-token-here"

# Create recommendation
curl -X POST http://localhost:8080/api/v1/creator/recommendations \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "dishName": "Butter Chicken",
    "restaurantName": "Moti Mahal",
    "area": "Daryaganj",
    "creatorQuote": "Best butter chicken in Delhi!",
    "dishCategory": "FINE_DINING",
    "priceRange": "MID"
  }'
```

### 3. View Creator Page

Open: http://localhost:3000/delhifoodie

## 🎨 Key Pages

| Page | URL | Description |
|------|-----|-------------|
| Home | `/` | Landing page |
| Creator Profile | `/[slug]` | e.g., `/delhifoodie` |
| Login | `/auth/login` | Creator login (to build) |
| Register | `/auth/register` | Creator registration (to build) |
| Dashboard | `/dashboard` | Creator dashboard (to build) |

## 🔧 Configuration

### Backend (.env)
```bash
DB_HOST=localhost
DB_PORT=5432
DB_NAME=foodlink_db
DB_USERNAME=postgres
DB_PASSWORD=postgres
JWT_SECRET=your-secret-key
```

### Frontend (.env.local)
```bash
NEXT_PUBLIC_API_URL=http://localhost:8080/api/v1
```

## 🐛 Troubleshooting

**Backend won't start:**
- Check if PostgreSQL is running: `docker compose ps`
- Verify Java 17+: `java -version`
- Check port 8080 is available

**Frontend won't start:**
- Install dependencies: `npm install`
- Check Node.js version: `node -v` (need 18+)
- Clear cache: `rm -rf .next`

**CORS errors:**
- Backend CORS is configured for `http://localhost:3000`
- Check `CORS_ORIGINS` in backend `.env`

**Database connection:**
- Ensure Docker Desktop is running
- Run: `docker compose up -d` in backend folder

## 📚 Documentation

- **Backend:** `backend/README.md`
- **Frontend:** `frontend/README.md`
- **API:** http://localhost:8080/swagger-ui.html
- **Deployment:** `backend/DEPLOYMENT.md`

## 🎯 Next Steps

### To Complete MVP:

1. **Build Auth Pages** (frontend)
   - `/auth/login` - Login form
   - `/auth/register` - Registration form

2. **Build Creator Dashboard** (frontend)
   - Create recommendations UI
   - View analytics
   - Edit profile

3. **Add Manual Data Entry**
   - For now, creators manually add recommendations
   - Skip video processing for MVP

4. **Test with Real Creators**
   - Get 2-3 creators to test
   - Add 5-10 recommendations each
   - Get feedback

5. **Deploy**
   - Backend: Railway/Heroku
   - Frontend: Vercel
   - Follow `backend/DEPLOYMENT.md`

## 💡 Tips

- Use Swagger UI to test backend APIs first
- Creator page URL format: `localhost:3000/creator-slug`
- All dates are in UTC
- JWT tokens expire in 24 hours
- Search is case-insensitive

## 🚢 Deployment Checklist

- [ ] Backend deployed to Railway/Heroku
- [ ] Database set up and migrated
- [ ] Frontend deployed to Vercel
- [ ] Environment variables configured
- [ ] CORS origins updated
- [ ] Custom domain (optional)
- [ ] SSL certificates (auto with Vercel/Railway)

## 📊 Tech Stack Summary

**Backend:**
- Java 17 + Spring Boot 3.2.2
- PostgreSQL + Flyway
- JWT Authentication
- Maven

**Frontend:**
- Next.js 14 (App Router)
- TypeScript
- Tailwind CSS
- React Query
- Axios

## 🎊 You're All Set!

Your full-stack application is ready for development!

**Start both servers:**
```bash
# Terminal 1 - Backend
cd backend && mvn spring-boot:run

# Terminal 2 - Frontend
cd frontend && npm run dev
```

**Then open:** http://localhost:3000

---

**Need help?** Check the README files in each directory or the Swagger docs at http://localhost:8080/swagger-ui.html

Happy coding! 🚀
