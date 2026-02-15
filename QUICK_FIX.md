# 🚀 QUICK FIX - Run These Commands

## Problem: Flyway checksum mismatch + Database type issues

## ✅ SOLUTION (Choose ONE):

---

### Option 1: FRESH START (Recommended - No data yet)

```bash
cd backend

# Stop and remove everything
docker compose down -v

# Start fresh
docker compose up -d

# Wait for database
sleep 15

# Rebuild and run
mvn clean install
mvn spring-boot:run
```

**Done! ✅** Everything will work with fresh schema.

---

### Option 2: REPAIR FLYWAY (If you have data to keep)

```bash
cd backend

# Fix the database
docker compose exec postgres psql -U postgres -d foodlink_db << 'EOF'
ALTER TABLE analytics_events ALTER COLUMN ip_address TYPE TEXT;
UPDATE creators SET total_recommendations = 0 WHERE total_recommendations IS NULL;
UPDATE creators SET total_page_views = 0 WHERE total_page_views IS NULL;
DELETE FROM flyway_schema_history WHERE version = '1';
\q
EOF

# Restart backend
mvn clean install
mvn spring-boot:run
```

**Done! ✅** Your data is preserved and Flyway will re-run.

---

### Option 3: COMPLETELY MANUAL (Step-by-step)

```bash
cd backend

# 1. Connect to database
docker compose exec postgres psql -U postgres -d foodlink_db

# 2. Run these SQL commands:
```

```sql
-- Fix column type
ALTER TABLE analytics_events ALTER COLUMN ip_address TYPE TEXT;

-- Fix null values
UPDATE creators SET total_recommendations = 0 WHERE total_recommendations IS NULL;
UPDATE creators SET total_page_views = 0 WHERE total_page_views IS NULL;

-- Remove bad Flyway entry
DELETE FROM flyway_schema_history WHERE version = '1';

-- Exit
\q
```

```bash
# 3. Restart backend
mvn spring-boot:run
```

---

## 🎯 PICK OPTION 1 IF:
- You just started
- No important data yet
- Want cleanest setup

## 🎯 PICK OPTION 2 IF:
- You have creators/recommendations you want to keep
- Want to preserve test data

---

## ✅ What's Fixed:

1. **Display Name** - Now supports spaces (auto-converts to slug with hyphens)
2. **Flyway Checksum** - Will be regenerated
3. **IP Address Type** - Changed from inet to TEXT
4. **Null Counters** - All set to 0

---

## 🧪 After Running Fix:

Test the full flow:

```bash
# 1. Backend should start successfully
# 2. Go to: http://localhost:3000/auth/register
# 3. Register with name: "Delhi Food Explorer" (with spaces!)
# 4. Slug auto-generates: "delhi-food-explorer"
# 5. Create account ✅
# 6. Add recommendations ✅
# 7. View public page ✅
```

---

**RECOMMENDED: Run Option 1 (fresh start) - takes 30 seconds!**
