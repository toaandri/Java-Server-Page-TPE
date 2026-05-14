CREATE TABLE IF NOT EXISTS driver (
    id SERIAL PRIMARY KEY,
    full_name VARCHAR(120) NOT NULL,
    phone VARCHAR(50) NOT NULL,
    license_number VARCHAR(80) NOT NULL,
    status VARCHAR(30) NOT NULL CHECK (status IN ('DISPONIBLE', 'EN_COURSE', 'EN_PAUSE', 'INDISPONIBLE'))
);

CREATE TABLE IF NOT EXISTS vehicle (
    id SERIAL PRIMARY KEY,
    brand_model VARCHAR(120) NOT NULL,
    plate_number VARCHAR(30) UNIQUE NOT NULL,
    mileage INTEGER NOT NULL CHECK (mileage >= 0),
    status VARCHAR(30) NOT NULL CHECK (status IN ('DISPONIBLE', 'EN_MISSION', 'MAINTENANCE')),
    assigned_driver_id INTEGER REFERENCES driver(id)
);

CREATE TABLE IF NOT EXISTS ride (
    id SERIAL PRIMARY KEY,
    pickup VARCHAR(200) NOT NULL,
    destination VARCHAR(200) NOT NULL,
    distance_km NUMERIC(10,2) NOT NULL CHECK (distance_km >= 0),
    wait_minutes INTEGER NOT NULL CHECK (wait_minutes >= 0),
    extra_fees NUMERIC(10,2) NOT NULL DEFAULT 0,
    status VARCHAR(30) NOT NULL CHECK (status IN ('EN_ATTENTE', 'ASSIGNEE', 'EN_COURS', 'TERMINEE', 'ANNULEE')),
    driver_id INTEGER REFERENCES driver(id),
    vehicle_id INTEGER REFERENCES vehicle(id),
    total_price NUMERIC(12,2),
    company_commission NUMERIC(12,2),
    driver_revenue NUMERIC(12,2),
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);
