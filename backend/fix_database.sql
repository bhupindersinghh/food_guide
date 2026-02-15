-- Manual database fix script
-- Run this directly on PostgreSQL to fix all issues

-- 1. Fix ip_address column type
ALTER TABLE analytics_events ALTER COLUMN ip_address TYPE TEXT;

-- 2. Fix null counters in creators
UPDATE creators SET total_recommendations = 0 WHERE total_recommendations IS NULL;
UPDATE creators SET total_page_views = 0 WHERE total_page_views IS NULL;
ALTER TABLE creators ALTER COLUMN total_recommendations SET DEFAULT 0;
ALTER TABLE creators ALTER COLUMN total_page_views SET DEFAULT 0;
ALTER TABLE creators ALTER COLUMN total_recommendations SET NOT NULL;
ALTER TABLE creators ALTER COLUMN total_page_views SET NOT NULL;

-- 3. Fix null counters in recommendations
UPDATE recommendations SET view_count = 0 WHERE view_count IS NULL;
UPDATE recommendations SET instagram_clicks = 0 WHERE instagram_clicks IS NULL;
UPDATE recommendations SET maps_clicks = 0 WHERE maps_clicks IS NULL;
UPDATE recommendations SET save_count = 0 WHERE save_count IS NULL;
ALTER TABLE recommendations ALTER COLUMN view_count SET DEFAULT 0;
ALTER TABLE recommendations ALTER COLUMN instagram_clicks SET DEFAULT 0;
ALTER TABLE recommendations ALTER COLUMN maps_clicks SET DEFAULT 0;
ALTER TABLE recommendations ALTER COLUMN save_count SET DEFAULT 0;
ALTER TABLE recommendations ALTER COLUMN view_count SET NOT NULL;
ALTER TABLE recommendations ALTER COLUMN instagram_clicks SET NOT NULL;
ALTER TABLE recommendations ALTER COLUMN maps_clicks SET NOT NULL;
ALTER TABLE recommendations ALTER COLUMN save_count SET NOT NULL;

-- 4. Verify changes
SELECT 
    column_name, 
    data_type, 
    is_nullable, 
    column_default 
FROM information_schema.columns 
WHERE table_name = 'analytics_events' AND column_name = 'ip_address';
