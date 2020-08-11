/**
 * Author:  naris
 * Created: Aug 11, 2020
 */

DROP DATABASE IF EXISTS meetingDBtest;
CREATE DATABASE meetingDBtest;

USE meetingDBtest;

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
FOREIGN KEY (roomId) REFERENCES room(id));

CREATE TABLE employee(
id INT PRIMARY KEY AUTO_INCREMENT,
firstName VARCHAR(50) NOT NULL,
lastName VARCHAR(50) NOT NULL);

CREATE TABLE meeting_employee(
meetingId INT NOT NULL,
employeeId INT NOT NULL,
PRIMARY KEY(meetingId, employeeId),
FOREIGN KEY (meetingId) REFERENCES meeting(id),
FOREIGN KEY (employeeId) REFERENCES employee(id));