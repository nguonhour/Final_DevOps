USE heng_nguonhour_db;

CREATE TABLE
    departments (
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        name VARCHAR(100) NOT NULL UNIQUE
    );

CREATE TABLE
    templates (
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        code VARCHAR(50) UNIQUE NOT NULL,
        name VARCHAR(100) NOT NULL,
        organization_name VARCHAR(150),
        layout VARCHAR(50),
        primary_color VARCHAR(20),
        secondary_color VARCHAR(20),
        text_color VARCHAR(20),
        tagline VARCHAR(255)
    );

CREATE TABLE
    profiles (
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        uuid VARCHAR(36) UNIQUE NOT NULL,
        registration_number VARCHAR(50) UNIQUE NOT NULL,
        profile_type ENUM('STUDENT', 'EMPLOYEE', 'USER') NOT NULL,
        full_name VARCHAR(150) NOT NULL,
        email VARCHAR(150),
        phone VARCHAR(50),
        department_id BIGINT,
        template_id BIGINT,
        barcode_type ENUM('CODE_128', 'EAN_13'),
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
        FOREIGN KEY (department_id) REFERENCES departments (id),
        FOREIGN KEY (template_id) REFERENCES templates (id)
    );

CREATE TABLE
    profile_photos (
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        profile_id BIGINT UNIQUE,
        file_name VARCHAR(255),
        file_type VARCHAR(50),
        file_size BIGINT,
        FOREIGN KEY (profile_id) REFERENCES profiles (id)
    );

CREATE TABLE
    generated_cards (
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        profile_id BIGINT,
        pdf_path VARCHAR(255),
        qr_path VARCHAR(255),
        barcode_path VARCHAR(255),
        generated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        FOREIGN KEY (profile_id) REFERENCES profiles (id)
    );

CREATE TABLE
    verification_logs (
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        profile_id BIGINT,
        scanned_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        ip_address VARCHAR(100),
        FOREIGN KEY (profile_id) REFERENCES profiles (id)
    );

-- profile_type VARCHAR(16) NOT NULL, -- changed from ENUM to regular VARCHAR for robust JPA mapping compatibility
-- department VARCHAR(80), -- changed from department_id FK to String to match your updated Profile entity structure
alter table profiles
add column profile_type VARCHAR(16) NOT NULL;

alter table profiles
add column department VARCHAR(80);