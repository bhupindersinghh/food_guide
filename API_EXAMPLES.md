# FoodLink API - Postman Collection Examples

## Register Creator
POST http://localhost:8080/api/v1/auth/register
Content-Type: application/json

{
  "email": "creator@example.com",
  "password": "SecurePass123!",
  "displayName": "Delhi Foodie",
  "slug": "delhifoodie",
  "instagramHandle": "delhifoodie",
  "bio": "Exploring Delhi's best food spots 🍽️"
}

## Login
POST http://localhost:8080/api/v1/auth/login
Content-Type: application/json

{
  "email": "creator@example.com",
  "password": "SecurePass123!"
}

## Get Creator Profile (Public)
GET http://localhost:8080/api/v1/public/creators/delhifoodie

## Create Recommendation (Authenticated)
POST http://localhost:8080/api/v1/creator/recommendations
Authorization: Bearer YOUR_JWT_TOKEN
Content-Type: application/json

{
  "dishName": "Butter Chicken",
  "restaurantName": "Moti Mahal",
  "area": "Daryaganj",
  "creatorQuote": "The OG butter chicken - been coming here for years!",
  "dishCategory": "FINE_DINING",
  "priceRange": "MID",
  "googleMapsUrl": "https://maps.google.com/?cid=123456789",
  "tags": ["non-veg", "must-try", "classic"]
}

## Get Recommendations (Public)
GET http://localhost:8080/api/v1/public/creators/delhifoodie/recommendations?page=0&size=20

## Track Analytics Event
POST http://localhost:8080/api/v1/public/events
Content-Type: application/json

{
  "eventType": "PAGE_VIEW",
  "creatorSlug": "delhifoodie",
  "sessionId": "550e8400-e29b-41d4-a716-446655440000"
}

## Track Recommendation Click
POST http://localhost:8080/api/v1/public/events
Content-Type: application/json

{
  "eventType": "INSTAGRAM_CLICK",
  "recommendationId": 1,
  "creatorSlug": "delhifoodie",
  "sessionId": "550e8400-e29b-41d4-a716-446655440000"
}
