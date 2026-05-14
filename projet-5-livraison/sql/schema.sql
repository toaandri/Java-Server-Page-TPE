CREATE TABLE IF NOT EXISTS delivery_person (
    id SERIAL PRIMARY KEY,
    full_name VARCHAR(120) NOT NULL,
    phone VARCHAR(50) NOT NULL,
    status VARCHAR(30) NOT NULL CHECK (status IN ('DISPONIBLE', 'EN_LIVRAISON'))
);

CREATE TABLE IF NOT EXISTS delivery_order (
    id SERIAL PRIMARY KEY,
    client_name VARCHAR(120) NOT NULL,
    pickup_address VARCHAR(200) NOT NULL,
    delivery_address VARCHAR(200) NOT NULL,
    package_weight NUMERIC(10,2) NOT NULL CHECK (package_weight >= 0),
    package_size VARCHAR(50) NOT NULL,
    desired_at TIMESTAMP NOT NULL,
    distance_km NUMERIC(10,2) NOT NULL CHECK (distance_km >= 0),
    extra_fees NUMERIC(10,2) NOT NULL DEFAULT 0,
    status VARCHAR(30) NOT NULL CHECK (status IN ('EN_ATTENTE', 'ASSIGNEE', 'EN_COURS', 'LIVREE', 'ANNULEE')),
    delivery_person_id INTEGER REFERENCES delivery_person(id),
    price NUMERIC(12,2)
);
