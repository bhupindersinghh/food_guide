# Database Reset Script - Run this to fix Flyway issues

## Quick Fix (Run these commands):

```bash
cd backend

# 1. Stop and remove database (this will delete all data!)
docker compose down -v

# 2. Start fresh database
docker compose up -d

# 3. Wait for database to be ready
sleep 15

# 4. Start backend (Flyway will create fresh schema)
mvn spring-boot:run
```

## Alternative: Flyway Repair (If you have important data)

```bash
cd backend

# Connect to PostgreSQL
docker compose exec postgres psql -U postgres -d foodlink_db

# Check Flyway history
SELECT * FROM flyway_schema_history;

# Delete the checksum entry for V1
DELETE FROM flyway_schema_history WHERE version = '1';

# Exit
\q

# Now restart backend
mvn spring-boot:run
```

## Manual SQL Fix (If you want to keep data)

```bash
# Run this command directly:
docker compose exec postgres psql -U postgres -d foodlink_db << 'EOF'
-- Fix ip_address type
ALTER TABLE analytics_events ALTER COLUMN ip_address TYPE TEXT;

-- Fix counters
UPDATE creators SET total_recommendations = 0 WHERE total_recommendations IS NULL;
UPDATE creators SET total_page_views = 0 WHERE total_page_views IS NULL;
UPDATE recommendations SET view_count = 0 WHERE view_count IS NULL;
UPDATE recommendations SET instagram_clicks = 0 WHERE instagram_clicks IS NULL;
UPDATE recommendations SET maps_clicks = 0 WHERE maps_clicks IS NULL;
UPDATE recommendations SET save_count = 0 WHERE save_count IS NULL;

-- Fix Flyway checksum
DELETE FROM flyway_schema_history WHERE version = '1';

SELECT 'Database fixed successfully!' as status;
EOF

# Then restart backend
mvn spring-boot:run
```

---

**Recommended: Use the Quick Fix (fresh start) if you don't have important data yet!**
