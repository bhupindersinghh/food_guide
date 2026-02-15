-- Fix null values in existing records
UPDATE creators SET total_recommendations = 0 WHERE total_recommendations IS NULL;
UPDATE creators SET total_page_views = 0 WHERE total_page_views IS NULL;

-- Make sure future records have defaults
ALTER TABLE creators ALTER COLUMN total_recommendations SET DEFAULT 0;
ALTER TABLE creators ALTER COLUMN total_page_views SET DEFAULT 0;

-- Same for recommendations
UPDATE recommendations SET view_count = 0 WHERE view_count IS NULL;
UPDATE recommendations SET instagram_clicks = 0 WHERE instagram_clicks IS NULL;
UPDATE recommendations SET maps_clicks = 0 WHERE maps_clicks IS NULL;
UPDATE recommendations SET save_count = 0 WHERE save_count IS NULL;

ALTER TABLE recommendations ALTER COLUMN view_count SET DEFAULT 0;
ALTER TABLE recommendations ALTER COLUMN instagram_clicks SET DEFAULT 0;
ALTER TABLE recommendations ALTER COLUMN maps_clicks SET DEFAULT 0;
ALTER TABLE recommendations ALTER COLUMN save_count SET DEFAULT 0;
