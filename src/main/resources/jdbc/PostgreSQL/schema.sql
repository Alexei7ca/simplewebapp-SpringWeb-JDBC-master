drop table if exists employee;
drop TYPE if exists gender;
CREATE TYPE gender AS ENUM ('MALE', 'FEMALE');
create table employee (
                          employeeId SERIAL,
                          first_name VARCHAR(250),
                          last_name VARCHAR(250),
                          gender gender,
                          department_id INT,
                          job_title VARCHAR(250),
                          date_of_birth DATE,
                          PRIMARY KEY (employeeId)
);