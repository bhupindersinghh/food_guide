# FoodLink Backend - Quick Start Guide

## 🚀 Get Started in 5 Minutes

### Option 1: Using Docker (Recommended)

1. **Start the database**
```bash
docker-compose up -d
```

2. **Run the application**
```bash
mvn spring-boot:run
```

### Option 2: Local PostgreSQL

1. **Create database**
```bash
createdb foodlink_db
```

2. **Update .env file**
```bash
cp .env.example .env
# Edit .env with your PostgreSQL credentials
```

3. **Run the application**
```bash
mvn spring-boot:run
```

## ✅ Verify Installation

1. **Check if API is running**
```bash
curl http://localhost:8080/api/v1/public/creators/test
```

2. **Open Swagger UI**
```
http://localhost:8080/swagger-ui.html
```

## 📝 First API Calls

### 1. Register a Creator
```bash
curl -X POST http://localhost:8080/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@example.com",
    "password": "Test1234!",
    "displayName": "Test Creator",
    "slug": "testcreator",
    "instagramHandle": "testcreator"
  }'
```

### 2. Login
```bash
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@example.com",
    "password": "Test1234!"
  }'
```
Save the `token` from the response!

### 3. Create a Recommendation
```bash
curl -X POST http://localhost:8080/api/v1/creator/recommendations \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_TOKEN_HERE" \
  -d '{
    "dishName": "Butter Chicken",
    "restaurantName": "Moti Mahal",
    "area": "Daryaganj",
    "creatorQuote": "Best butter chicken in Delhi!",
    "dishCategory": "FINE_DINING",
    "priceRange": "MID"
  }'
```

### 4. Get Public Recommendations
```bash
curl http://localhost:8080/api/v1/public/creators/testcreator/recommendations
```

## 🔧 Troubleshooting

**Database connection error?**
- Check if PostgreSQL is running: `docker-compose ps` or `pg_isready`
- Verify credentials in `.env` file

**Port 8080 already in use?**
- Change port in `application.yml`: `server.port: 8081`

**Build errors?**
- Ensure Java 17+: `java -version`
- Clean and rebuild: `mvn clean install`

## 📚 Next Steps

1. Import `API_EXAMPLES.md` into Postman
2. Check Swagger docs at `/swagger-ui.html`
3. Connect your frontend to these APIs
4. Read full API documentation in README.md

## 🎯 Key Endpoints

| Endpoint | Method | Auth | Description |
|----------|--------|------|-------------|
| `/api/v1/auth/register` | POST | No | Register creator |
| `/api/v1/auth/login` | POST | No | Login |
| `/api/v1/creator/recommendations` | POST | Yes | Create recommendation |
| `/api/v1/public/creators/{slug}` | GET | No | Get creator page |
| `/api/v1/public/creators/{slug}/recommendations` | GET | No | Get recommendations |

## 💡 Tips

- Use Swagger UI for easy API testing
- JWT tokens expire in 24 hours (configurable)
- All dates are in UTC
- Use `page` and `size` params for pagination

---

Need help? Check the full README.md or API documentation.
