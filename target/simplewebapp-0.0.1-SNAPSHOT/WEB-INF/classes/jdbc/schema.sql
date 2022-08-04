-- создаем тестовую базу и таблицы в памяти
create schema test;

drop table if exists employee;
CREATE TYPE gender AS ENUM ('MALE', 'FEMALE');
create table employee (
                          employeeId INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
                          first_name VARCHAR(250),
                          last_name VARCHAR(250),
                          gender gender,
                          department_id INT,
                          job_title VARCHAR(250),
                          date_of_birth DATE
);

