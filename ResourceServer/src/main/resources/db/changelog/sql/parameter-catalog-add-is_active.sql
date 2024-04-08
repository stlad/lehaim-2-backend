ALTER TABLE "catalog" ADD COLUMN IF NOT EXISTS
    is_active boolean default true;