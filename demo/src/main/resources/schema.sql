ALTER TABLE books
    ADD COLUMN IF NOT EXISTS quantity INTEGER NOT NULL DEFAULT 1;

ALTER TABLE books
    ADD COLUMN IF NOT EXISTS available_quantity INTEGER NOT NULL DEFAULT 1;

UPDATE books
SET available_quantity = CASE
    WHEN available THEN GREATEST(available_quantity, 1)
    ELSE 0
END;

UPDATE books
SET available = (available_quantity > 0);
