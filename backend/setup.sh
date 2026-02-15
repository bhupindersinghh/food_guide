#!/bin/bash

# FoodLink API Setup Script

echo "🚀 Setting up FoodLink API..."

# Check Java version
echo "Checking Java version..."
java -version

# Check if PostgreSQL is running
echo "Checking PostgreSQL..."
if ! command -v psql &> /dev/null; then
    echo "❌ PostgreSQL is not installed. Please install PostgreSQL 14+."
    exit 1
fi

# Create database
echo "Creating database..."
psql -U postgres -c "CREATE DATABASE foodlink_db;" 2>/dev/null || echo "Database might already exist"

# Copy environment file
if [ ! -f .env ]; then
    echo "Creating .env file..."
    cp .env.example .env
    echo "⚠️  Please edit .env file with your configuration"
fi

# Build the project
echo "Building project..."
mvn clean install -DskipTests

echo "✅ Setup complete!"
echo ""
echo "Next steps:"
echo "1. Edit .env file with your database credentials"
echo "2. Run: mvn spring-boot:run"
echo "3. Access API docs at: http://localhost:8080/swagger-ui.html"
