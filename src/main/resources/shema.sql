CREATE DATABASE Polytasking;

DROP TABLE tasks;

CREATE TABLE
    tasks (
        id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
        name VARCHAR(50) NOT NULL,
        type VARCHAR(20) NOT NULL,
        frequency VARCHAR(20) NOT NULL,
        due_date DATE NOT NULL,
        email VARCHAR(320) NOT NULL
    );