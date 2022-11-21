create schema if not exists customer{
    customer_name varchar(255),
    address varchar(255),
    home_branch varchar(255) NOT NULL,
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

Alter Table employee
    Add Constraint salary Check ( salary >= 0 );

create schema if not exists account_type{
    type_name varchar(255),
    interest_rate int(10),
    monthly_fees int(10),
    overdraft_allowed bit,
    overdraft_fee int(10),
    primary key (type_name)
    };
create schema if not exists account{
    customer_name varchar(255),
    account_isType varchar(255),
    balance_curr int(10),
    account_id int(10),
    primary key (account_id),
    foreign key (account_isType) references account_type
    };
create schema if not exists transaction{
    type varchar(255),
    quantity int(10),
    description varchar(255),
    transaction_date date,
    account_from int(10),
    account_to int(10),
    status bit,
    primary key (transaction_date, account_from, account_to),
    foreign key (account_from, account_to) references account
    };
