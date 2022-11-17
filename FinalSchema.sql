create schema if not exists customer{
    customer_name varchar(255),
    address varchar(255),
    home_branch varchar(255),
    primary key (customer_name,home_branch),
    foreign key (home_branch) references bank
    };
create schema if not exists bank{
    bank_addr varchar(255) primary key
    };
create schema if not exists employee{
    employee_name varchar(255),
    address varchar(255),
    SSN int(10),
    salary int(255),
    employee_type varchar(255),
    primary key (SSN),
    };
create schema if not exists account_type{

    };
create schema if not exists account{

    };
create schema if not exists transaction{

    };
