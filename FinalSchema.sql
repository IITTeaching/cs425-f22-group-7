
create table if not exists bank(
    bank_addr varchar(255) primary key
);
create table if not exists customer(
                                       customer_name varchar(255),
                                       passcode varchar(255),
                                       address varchar(255),
                                       home_branch varchar(255) NOT NULL,
                                       primary key (customer_name,home_branch,passcode),
                                       foreign key (home_branch) references bank(bank_addr)
);
create table if not exists employee(
                                       employee_name varchar(255),
                                       passcode varchar(255),
                                       address varchar(255),
                                       SSN integer,
                                       salary integer,
                                       employee_type varchar(255),
                                       primary key (SSN)
);

Alter Table employee
    Add Constraint salary Check ( salary >= 0 );

create table if not exists account_type(
                                           type_name varchar(255),
                                           interest_rate integer,
                                           monthly_fees integer,
                                           overdraft_allowed bit,
                                           overdraft_fee integer,
                                           primary key (type_name)
);
create table if not exists account(
                                      customer_name varchar(255),
                                      account_isType varchar(255),
                                      balance_curr integer,
                                      account_id integer,
                                      primary key (account_id),
                                      foreign key (account_isType) references account_type
);
create table if not exists transaction(
                                          type varchar(255),
                                          quantity integer,
                                          description varchar(255),
                                          transaction_date TIMESTAMP,
                                          account_from integer,
                                          account_to integer,
                                          status bit,
                                          primary key (transaction_date, account_from, account_to),
                                          foreign key (account_from) references account(account_id),
                                          foreign key (account_to) references account(account_id)
);
