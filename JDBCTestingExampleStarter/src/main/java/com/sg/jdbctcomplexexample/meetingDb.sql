/**
 * Author:  naris
 * Created: Aug 11, 2020
 */

DROP DATABASE IF EXISTS meetingDB;
CREATE DATABASE meetingDB;

USE meetingDB;

CREATE TABLE room(
    id INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL,
    `description` VARCHAR(100)
);

CREATE TABLE meeting(
    id INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL,
    `time` DATETIME NOT NULL,
    roomId INT NOT NULL,
    FOREIGN KEY (roomId) REFERENCES room(id)
);

CREATE TABLE employee(
    id INT PRIMARY KEY AUTO_INCREMENT,
    firstName VARCHAR(50) NOT NULL,
    lastName VARCHAR(50) NOT NULL
);

CREATE TABLE meeting_employee(
    meetingId INT NOT NULL,
    employeeId INT NOT NULL,
    PRIMARY KEY(meetingId, employeeId),
    FOREIGN KEY (meetingId) REFERENCES meeting(id),
    FOREIGN KEY (employeeId) REFERENCES employee(id)
);


INSERT INTO room(id, name, description) VALUES(1, "North Room", "Large conference room");
INSERT INTO room(id, name, description) VALUES(2, "South Room", "Medium conference room");
INSERT INTO room(id, name, description) VALUES(3, "West Room", "Small conference room");

INSERT INTO meeting(id, name, time, roomId) VALUES(1, "All Team Meeting", '2018-01-01 14:00:00', 1);
INSERT INTO meeting(id, name, time, roomId) VALUES(2, "Lunch and Learn", '2018-01-02 12:00:00', 1);
INSERT INTO meeting(id, name, time, roomId) VALUES(3, "Birthday", '2018-01-03 10:00:00', 2);

INSERT INTO employee(id, firstName, lastName) VALUES(1, "Bob", "Johnson");
INSERT INTO employee(id, firstName, lastName) VALUES(2, "John", "Smith");
INSERT INTO employee(id, firstName, lastName) VALUES(3, "Karen", "Jones");
INSERT INTO employee(id, firstName, lastName) VALUES(4, "Connie", "Samson");

INSERT INTO meeting_employee(meetingId, employeeId) VALUES(1, 1);
INSERT INTO meeting_employee(meetingId, employeeId) VALUES(1, 2);
INSERT INTO meeting_employee(meetingId, employeeId) VALUES(1, 3);
INSERT INTO meeting_employee(meetingId, employeeId) VALUES(1, 4);
INSERT INTO meeting_employee(meetingId, employeeId) VALUES(2, 3);
INSERT INTO meeting_employee(meetingId, employeeId) VALUES(2, 4);
INSERT INTO meeting_employee(meetingId, employeeId) VALUES(3, 1);
INSERT INTO meeting_employee(meetingId, employeeId) VALUES(3, 2);
INSERT INTO meeting_employee(meetingId, employeeId) VALUES(3, 4);