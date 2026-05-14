CREATE TABLE IF NOT EXISTS parking_spot (
    id SERIAL PRIMARY KEY,
    code VARCHAR(30) UNIQUE NOT NULL,
    vip_reserved BOOLEAN NOT NULL DEFAULT FALSE,
    occupied BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS reservation (
    id SERIAL PRIMARY KEY,
    customer_name VARCHAR(120) NOT NULL,
    user_type VARCHAR(20) NOT NULL CHECK (user_type IN ('STANDARD', 'ABONNE', 'VIP')),
    plate_number VARCHAR(30) NOT NULL,
    spot_id INTEGER NOT NULL REFERENCES parking_spot(id),
    start_at TIMESTAMP NOT NULL,
    duration_hours INTEGER NOT NULL CHECK (duration_hours > 0),
    status VARCHAR(20) NOT NULL CHECK (status IN ('CONFIRMEE', 'ANNULEE'))
);

CREATE TABLE IF NOT EXISTS parking_entry (
    id SERIAL PRIMARY KEY,
    plate_number VARCHAR(30) NOT NULL,
    spot_id INTEGER NOT NULL REFERENCES parking_spot(id),
    entry_at TIMESTAMP NOT NULL DEFAULT NOW(),
    exit_at TIMESTAMP,
    amount NUMERIC(12,2) NOT NULL DEFAULT 0
);
