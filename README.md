# FoodLink - Food Recommendation Platform

Full-stack application for food creators to share restaurant recommendations with their audience.

## 📁 Project Structure

```
food_guide/
├── backend/          # Spring Boot API
│   ├── src/
│   ├── pom.xml
│   └── ...
├── frontend/         # Next.js + Tailwind CSS
│   ├── src/
│   ├── package.json
│   └── ...
└── README.md         # This file
```

## 🚀 Quick Start

### Backend (Spring Boot)

```bash
cd backend

# Start database
docker compose up -d

# Run backend
mvn spring-boot:run
```

Backend runs on: http://localhost:8080
API Docs: http://localhost:8080/swagger-ui.html

### Frontend (Next.js)

```bash
cd frontend

# Install dependencies
npm install

# Run development server
npm run dev
```

Frontend runs on: http://localhost:3000

## 📚 Documentation

- Backend: See `backend/README.md`
- Frontend: See `frontend/README.md`

## 🛠️ Tech Stack

**Backend:**
- Java 17 + Spring Boot 3
- PostgreSQL + Elasticsearch
- JWT Authentication
- Swagger/OpenAPI

**Frontend:**
- Next.js 14 (App Router)
- TypeScript
- Tailwind CSS
- React Query (data fetching)

## 🎯 Features

- Creator authentication & profiles
- Food recommendation management
- Public creator pages
- Search & filtering
- Analytics tracking
