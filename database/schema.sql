-- =.=.=.=.=.=.=.=.=.=.=.=.=.=.=.=.=.=
-- Création des tables de la base
-- =.=.=.=.=.=.=.=.=.=.=.=.=.=.=.=.=.=

-- Table Users
CREATE TABLE Users (
    id SERIAL PRIMARY KEY,
    email VARCHAR,
    password VARCHAR,
    first_name VARCHAR,
    last_name VARCHAR,
    birthdate DATE,
    address VARCHAR
);

-- Table Agencies
CREATE TABLE Agencies (
    id SERIAL PRIMARY KEY,
    address VARCHAR,
    name VARCHAR
);

-- Table Employees
CREATE TABLE Employees (
    id SERIAL PRIMARY KEY,
    username VARCHAR,
    password VARCHAR
);

-- Table Vehicles
CREATE TABLE Vehicles (
    id SERIAL PRIMARY KEY,
    name VARCHAR,
    acriss_category VARCHAR,
    agency_id INTEGER REFERENCES Agencies(id),
    price_per_day DECIMAL
);

-- Table Reservations
CREATE TABLE Reservations (
    id SERIAL PRIMARY KEY,
    departure_agency INTEGER REFERENCES Agencies(id),
    return_agency INTEGER REFERENCES Agencies(id),
    start_date DATE,
    end_date DATE,
    user_id INTEGER REFERENCES Users(id),
    vehicle_id INTEGER REFERENCES Vehicles(id)
);

-- Table Chats
CREATE TABLE Chats (
    id SERIAL PRIMARY KEY,
    content TEXT,
    author_id INTEGER REFERENCES Users(id),
    employee_id INTEGER REFERENCES Employees(id),
    sent_at TIMESTAMP,
    CHECK (
        (author_id IS NOT NULL AND employee_id IS NULL)
        OR
        (author_id IS NULL AND employee_id IS NOT NULL)
    )
);

-- Table Messages
CREATE TABLE Messages (
    id SERIAL PRIMARY KEY,
    content TEXT,
    sent_at TIMESTAMP,
    author_id INTEGER REFERENCES Users(id),
    status VARCHAR,
    subject VARCHAR,
    assigned_to INTEGER REFERENCES Employees(id)
);
