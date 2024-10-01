CREATE TABLE IF NOT EXISTS HR_REGIONS
(
    REGION_ID   int NOT NULL AUTO_INCREMENT,
    REGION_NAME varchar(25) DEFAULT NULL,
    PRIMARY KEY (REGION_ID)
);

CREATE TABLE IF NOT EXISTS HR_COUNTRIES
(
    COUNTRY_ID   varchar(2) NOT NULL DEFAULT '' COMMENT 'Primary key of countries table.',
    COUNTRY_NAME varchar(40)         DEFAULT NULL COMMENT 'Country name',
    REGION_ID    int                 DEFAULT NULL COMMENT 'Region ID for the country. Foreign key to region_id column in the departments table.',
    PRIMARY KEY (COUNTRY_ID)
);

CREATE TABLE IF NOT EXISTS HR_LOCATIONS
(
    LOCATION_ID    int         NOT NULL COMMENT 'Primary key of locations table',
    STREET_ADDRESS varchar(40) DEFAULT NULL COMMENT 'Street address of an office, warehouse, or production site of a company.\r\nContains building number and street name',
    POSTAL_CODE    varchar(12) DEFAULT NULL COMMENT 'Postal code of the location of an office, warehouse, or production site of a company.',
    CITY           varchar(30) NOT NULL COMMENT 'A not null column that shows city where an office, warehouse, or production site of a company is located.',
    STATE_PROVINCE varchar(25) DEFAULT NULL COMMENT 'State or Province where an office, warehouse, or production site of a company is located.',
    COUNTRY_ID     varchar(2)  DEFAULT NULL COMMENT 'Country where an office, warehouse, or production site of a company is located. Foreign key to country_id column of the countries table.',
    PRIMARY KEY (LOCATION_ID)
);

CREATE TABLE IF NOT EXISTS HR_DEPARTMENTS
(
    DEPARTMENT_ID   int         NOT NULL COMMENT 'Primary key column of departments table.',
    DEPARTMENT_NAME varchar(30) NOT NULL DEFAULT '' COMMENT 'A not null column that shows name of a department. Administration, Marketing, Purchasing, Human Resources, Shipping, IT, Executive, Public Relations, Sales, Finance, and Accounting. ',
    MANAGER_ID      int                  DEFAULT NULL COMMENT 'Manager_id of a department. Foreign key to employee_id column of employees table. The manager_id column of the employee table references this column.',
    LOCATION_ID     int                  DEFAULT NULL COMMENT 'Location id where a department is located. Foreign key to location_id column of locations table.',
    PRIMARY KEY (DEPARTMENT_ID)
);

CREATE TABLE IF NOT EXISTS HR_JOBS
(
    JOB_ID     varchar(10) NOT NULL DEFAULT '' COMMENT 'Primary key of jobs table.',
    JOB_TITLE  varchar(35) NOT NULL COMMENT 'A not null column that shows job title, e.g. AD_VP, FI_ACCOUNTANT',
    MIN_SALARY int                  DEFAULT NULL COMMENT 'Minimum salary for a job title.',
    MAX_SALARY int                  DEFAULT NULL COMMENT 'Maximum salary for a job title',
    PRIMARY KEY (JOB_ID)
);

CREATE TABLE IF NOT EXISTS HR_EMPLOYEES
(
    EMPLOYEE_ID    int         NOT NULL COMMENT 'Primary key of employees table.',
    FIRST_NAME     varchar(20)          DEFAULT NULL COMMENT 'First name of the employee. A not null column.',
    LAST_NAME      varchar(25) NOT NULL DEFAULT '' COMMENT 'Last name of the employee. A not null column.',
    EMAIL          varchar(25) NOT NULL DEFAULT '' COMMENT 'Email id of the employee',
    PHONE_NUMBER   varchar(20)          DEFAULT NULL COMMENT 'Phone number of the employee; includes country code and area code',
    HIRE_DATE      date        NOT NULL COMMENT 'Date when the employee started on this job. A not null column.',
    JOB_ID         varchar(10) NOT NULL DEFAULT '' COMMENT 'Current job of the employee; foreign key to job_id column of the jobs table. A not null column.',
    SALARY         double               DEFAULT NULL COMMENT 'Monthly salary of the employee. Must be greater than zero (enforced by constraint emp_salary_min)',
    COMMISSION_PCT double               DEFAULT NULL COMMENT 'Commission percentage of the employee; Only employees in sales department elgible for commission percentage',
    MANAGER_ID     int                  DEFAULT NULL COMMENT 'Manager id of the employee; has same domain as manager_id in departments table. Foreign key to employee_id column of employees table. (useful for reflexive joins and CONNECT BY query)',
    DEPARTMENT_ID  int                  DEFAULT NULL COMMENT 'Department id where employee works; foreign key to department_id column of the departments table',
    PRIMARY KEY (EMPLOYEE_ID),
    UNIQUE KEY EMAIL (EMAIL)
);

CREATE TABLE IF NOT EXISTS HR_JOB_HISTORY
(
    EMPLOYEE_ID   int         NOT NULL DEFAULT '0' COMMENT 'A not null column in the complex primary key employee_id+start_date.\r\nForeign key to employee_id column of the employee table',
    START_DATE    date        NOT NULL DEFAULT '0000-00-00' COMMENT 'A not null column in the complex primary key employee_id+start_date.\r\nMust be less than the end_date of the job_history table. (enforced by\r\nconstraint jhist_date_interval)',
    END_DATE      date        NOT NULL COMMENT 'Last day of the employee in this job role. A not null column. Must be\r\ngreater than the start_date of the job_history table.\r\n(enforced by constraint jhist_date_interval)',
    JOB_ID        varchar(10) NOT NULL COMMENT 'Job role in which the employee worked in the past; foreign key to\r\njob_id column in the jobs table. A not null column.',
    DEPARTMENT_ID int                  DEFAULT NULL COMMENT 'Department id in which the employee worked in the past; foreign key to deparment_id column in the departments table',
    PRIMARY KEY (EMPLOYEE_ID, START_DATE)
);

-- ALTER TABLE HR_COUNTRIES ADD CONSTRAINT COUNTR_REF_FK FOREIGN KEY (REGION_ID) REFERENCES HR_REGIONS (REGION_ID);
-- ALTER TABLE HR_LOCATIONS ADD CONSTRAINT LOC_C_ID_FK FOREIGN KEY (COUNTRY_ID) REFERENCES HR_COUNTRIES (COUNTRY_ID);
-- ALTER TABLE HR_DEPARTMENTS ADD CONSTRAINT DEPT_LOC_FK FOREIGN KEY (LOCATION_ID) REFERENCES HR_LOCATIONS (LOCATION_ID);
-- ALTER TABLE HR_DEPARTMENTS ADD CONSTRAINT DEPT_MGR_FK FOREIGN KEY (MANAGER_ID) REFERENCES HR_EMPLOYEES (EMPLOYEE_ID);
-- ALTER TABLE HR_EMPLOYEES ADD CONSTRAINT EMP_DEPT_FK FOREIGN KEY (DEPARTMENT_ID) REFERENCES HR_DEPARTMENTS (DEPARTMENT_ID);
-- ALTER TABLE HR_EMPLOYEES ADD CONSTRAINT EMP_JOB_FK FOREIGN KEY (JOB_ID) REFERENCES HR_JOBS (JOB_ID);
-- ALTER TABLE HR_EMPLOYEES ADD CONSTRAINT EMP_MANAGER_FK FOREIGN KEY (MANAGER_ID) REFERENCES HR_EMPLOYEES (EMPLOYEE_ID);
-- ALTER TABLE HR_JOB_HISTORY ADD CONSTRAINT JHIST_DEPT_FK FOREIGN KEY (DEPARTMENT_ID) REFERENCES HR_DEPARTMENTS (DEPARTMENT_ID);
-- ALTER TABLE HR_JOB_HISTORY ADD CONSTRAINT JHIST_EMP_FK FOREIGN KEY (EMPLOYEE_ID) REFERENCES HR_EMPLOYEES (EMPLOYEE_ID);
-- ALTER TABLE HR_JOB_HISTORY ADD CONSTRAINT JHIST_JOB_FK FOREIGN KEY (JOB_ID) REFERENCES HR_JOBS (JOB_ID);
