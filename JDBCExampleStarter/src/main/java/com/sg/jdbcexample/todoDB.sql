/**
 * Author:  naris
 * Created: Aug 10, 2020
 */

DROP DATABASE IF EXISTS todoDb;
CREATE DATABASE todoDB;
USE todoDB;

CREATE TABLE todo(
    id INT PRIMARY KEY AUTO_INCREMENT,
    todo VARCHAR(40) NOT NULL,
    note VARCHAR(255),
    finished BOOLEAN DEFAULT FALSE
);

INSERT INTO todo(id, todo, note, finished)
VALUES
    (1, "Wash Car", "", false),
    (2, "Laundry", "Jeans", false),
    (3, "Wash Dishes", "", true);