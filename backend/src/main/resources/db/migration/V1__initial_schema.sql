-- V1__initial_schema.sql
-- Create creators table
CREATE TABLE creators (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(100) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    display_name VARCHAR(255) NOT NULL,
    bio TEXT,
    profile_image_url VARCHAR(500),
    instagram_handle VARCHAR(100) UNIQUE,
    instagram_verified BOOLEAN DEFAULT FALSE,
    slug VARCHAR(100) UNIQUE NOT NULL,
    theme_color VARCHAR(7) DEFAULT '#FF6B6B',
    subscription_tier VARCHAR(20) DEFAULT 'FREE',
    subscription_expires_at TIMESTAMP,
    total_recommendations INTEGER DEFAULT 0,
    total_page_views INTEGER DEFAULT 0,
    status VARCHAR(20) DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW(),
    last_login_at TIMESTAMP
);

CREATE INDEX idx_creators_slug ON creators(slug);
CREATE INDEX idx_creators_instagram ON creators(instagram_handle);
CREATE INDEX idx_creators_status ON creators(status);

-- Create restaurants table
CREATE TABLE restaurants (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    slug VARCHAR(255) UNIQUE,
    area VARCHAR(100),
    city VARCHAR(100) DEFAULT 'Delhi',
    full_address TEXT,
    latitude DECIMAL(10, 8),
    longitude DECIMAL(11, 8),
    google_place_id VARCHAR(255) UNIQUE,
    google_maps_url TEXT,
    zomato_url TEXT,
    swiggy_url TEXT,
    phone VARCHAR(20),
    cuisine_types TEXT[],
    price_range VARCHAR(20),
    total_recommendations INTEGER DEFAULT 0,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);

CREATE INDEX idx_restaurants_name ON restaurants(name);
CREATE INDEX idx_restaurants_area ON restaurants(area);
CREATE UNIQUE INDEX idx_restaurants_google_place ON restaurants(google_place_id);

-- Create videos table
CREATE TABLE videos (
    id BIGSERIAL PRIMARY KEY,
    creator_id BIGINT NOT NULL REFERENCES creators(id) ON DELETE CASCADE,
    instagram_url TEXT NOT NULL,
    instagram_post_id VARCHAR(50),
    thumbnail_url VARCHAR(500),
    video_duration_seconds INTEGER,
    posted_at TIMESTAMP,
    audio_file_url VARCHAR(500),
    transcript TEXT,
    transcript_language VARCHAR(10) DEFAULT 'hi-IN',
    processing_status VARCHAR(30) DEFAULT 'PENDING',
    processing_error TEXT,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);

CREATE INDEX idx_videos_creator ON videos(creator_id);
CREATE INDEX idx_videos_status ON videos(processing_status);
CREATE INDEX idx_videos_instagram_post ON videos(instagram_post_id);

-- Create recommendations table
CREATE TABLE recommendations (
    id BIGSERIAL PRIMARY KEY,
    creator_id BIGINT NOT NULL REFERENCES creators(id) ON DELETE CASCADE,
    video_id BIGINT REFERENCES videos(id),
    restaurant_id BIGINT REFERENCES restaurants(id),
    dish_name VARCHAR(255) NOT NULL,
    restaurant_name VARCHAR(255) NOT NULL,
    area VARCHAR(100),
    city VARCHAR(100) DEFAULT 'Delhi',
    full_address TEXT,
    latitude DECIMAL(10, 8),
    longitude DECIMAL(11, 8),
    google_maps_url TEXT,
    creator_quote TEXT,
    description TEXT,
    dish_category VARCHAR(50),
    cuisine_type VARCHAR(50),
    meal_type VARCHAR(30),
    price_range VARCHAR(20),
    tags TEXT[],
    view_count INTEGER DEFAULT 0,
    instagram_clicks INTEGER DEFAULT 0,
    maps_clicks INTEGER DEFAULT 0,
    save_count INTEGER DEFAULT 0,
    status VARCHAR(20) DEFAULT 'PUBLISHED',
    published_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);

CREATE INDEX idx_recommendations_creator ON recommendations(creator_id);
CREATE INDEX idx_recommendations_restaurant ON recommendations(restaurant_id);
CREATE INDEX idx_recommendations_status ON recommendations(status);
CREATE INDEX idx_recommendations_category ON recommendations(dish_category);

-- Create analytics_events table
CREATE TABLE analytics_events (
    id BIGSERIAL PRIMARY KEY,
    creator_id BIGINT,
    recommendation_id BIGINT,
    event_type VARCHAR(50) NOT NULL,
    search_query TEXT,
    user_agent TEXT,
    ip_address TEXT,
    referrer TEXT,
    session_id UUID,
    user_id BIGINT,
    created_at TIMESTAMP DEFAULT NOW()
);

CREATE INDEX idx_analytics_creator ON analytics_events(creator_id);
CREATE INDEX idx_analytics_recommendation ON analytics_events(recommendation_id);
CREATE INDEX idx_analytics_event_type ON analytics_events(event_type);
CREATE INDEX idx_analytics_created_at ON analytics_events(created_at);
