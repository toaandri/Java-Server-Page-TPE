CREATE TABLE IF NOT EXISTS textile_order (
    id SERIAL PRIMARY KEY,
    client_name VARCHAR(120) NOT NULL,
    article_type VARCHAR(120) NOT NULL,
    quantity INTEGER NOT NULL CHECK (quantity > 0),
    size_color VARCHAR(120),
    expected_delivery_date DATE NOT NULL,
    status VARCHAR(30) NOT NULL CHECK (status IN ('EN_ATTENTE', 'EN_PRODUCTION', 'LIVRE'))
);

CREATE TABLE IF NOT EXISTS production_step (
    id SERIAL PRIMARY KEY,
    order_id INTEGER NOT NULL REFERENCES textile_order(id) ON DELETE CASCADE,
    step_name VARCHAR(30) NOT NULL CHECK (step_name IN ('COUPE', 'COUTURE', 'FINITION', 'LIVRAISON')),
    responsible VARCHAR(120),
    status VARCHAR(20) NOT NULL CHECK (status IN ('PENDING', 'IN_PROGRESS', 'DONE')),
    started_at TIMESTAMP,
    ended_at TIMESTAMP,
    planned_duration_hours INTEGER NOT NULL CHECK (planned_duration_hours > 0),
    notes TEXT
);
