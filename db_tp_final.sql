CREATE DATABASE db_tp_final;
USE db_tp_final;
SET GLOBAL event_scheduler = ON;
DROP DATABASE db_tp_final;

CREATE TABLE provinces (
	id_prov int auto_increment not null,
    province_name varchar(50) not null,
    constraint pk_id_prov primary key (id_prov),
    constraint unique_provinces_name UNIQUE (province_name)
);

CREATE TABLE localities (
	prefix int unique not null,
    id_prov int not null,
    locality_name varchar(50) not null,
    constraint pk_prefix primary key (prefix),
    constraint fk_id_prov foreign key (id_prov) references provinces (id_prov)
);

CREATE TABLE line_types (
	id_line_type int auto_increment not null,
    line_type_name varchar(50) not null,
    constraint pk_id_type primary key (id_line_type)
);

CREATE TABLE user_types (
	id_user_type int auto_increment not null,
    usertype_name varchar(50) not null,
    constraint pk_id_user_type primary key (id_user_type)
);

CREATE TABLE users (
    id int auto_increment not null,
    user_type int not null,
	dni int not null,
    name varchar(50) not null,
    surname varchar(50) not null,
    pass varchar(50) not null,
	constraint pk_id primary key (id),
	constraint fk_id_user_type foreign key (user_type) references user_types (id_user_type)
);

CREATE TABLE phone_lines (
	id_line int auto_increment not null,
    id_user int not null,
    id_locality int not null,
    id_line_type int not null,
    line_number varchar(50),
    constraint pk_id_line primary key (id_line),
    constraint fk_id_line_user foreign key (id_user) references users (id),
	constraint fk_id_locality foreign key (id_locality) references localities (prefix),
	constraint fk_id_line_type foreign key (id_line_type) references line_types (id_line_type)
);

CREATE TABLE bills (
	id_bill int auto_increment not null,
    id_user int not null,
    total_price float,
    emittion_date date,
    expiration_date date,
    bill_status boolean,
    total_cost float,
    total_profit float,
    constraint pk_id_bill primary key (id_bill),
	constraint fk_id_user_for_bill foreign key (id_user) references users (id)
);

CREATE TABLE rates (
	id_rate int auto_increment not null,
    prefix_origin int not null,
    prefix_destiny int not null,
    price_per_minute float not null,
    start_date date,
    expiration_date date,
    constraint pk_id_rate primary key (id_rate),
    constraint fk_prefix_origin foreign key (prefix_origin) references localities (prefix),
	constraint fk_prefix_destiny foreign key (prefix_origin) references localities (prefix)
);

CREATE TABLE calls(
	id_call int auto_increment not null,
	line_origin int not null,
    line_destiny int not null,
    id_bill int,
    id_rate int not null,
    price float,
    cost float,
    profit float,
    date_call date,
    hour_call_start time,
    hour_call_finish time,
    duration int,
    constraint pk_id_call primary key (id_call),
    constraint fk_line_origin foreign key (line_origin) references phone_lines (id_line),
	constraint fk_line_destiny foreign key (line_destiny) references phone_lines (id_line),
	constraint fk_id_bill foreign key (id_bill) references bills (id_bill),
	constraint fk_id_rate foreign key (id_rate) references rates (id_rate)
);

drop procedure billing_sp;
DELIMITER //
CREATE PROCEDURE billing_sp ()
begin
	declare vFinished int default 0;
	declare vIdUser int;
	declare vIdCall int;
	declare vPrice int default 0;
	declare vCost int default 0;
	declare vProfit int default 0;
	declare cursor_calls cursor for select l.id_user, l.id_line, c.id_call, sum (c.price) as totalprice , sum(c.cost) as totalcost, sum(c.profit) as totalprofit
	from calls c inner join phone_lines l where l.id_line = c.line_origin group by l.id_user;
	declare continue HANDLER for not found set vFinished = 1;
	open cursor_calls;
    start transaction;
	fetch cursor_calls into vIdUser, vIdCall, vPrice, vCost, vProfit;
	while(vFinished = 0) do
	insert into bills (id_user, total_price, emittion_date, expiration_date, bill_status, total_cost, total_profit)
	values(vIdUser, vPrice, now(), now() + INTERVAL 15 DAY, false, vCost, vProfit);
	update calls c set id_bill = last_insert_id() where c.id_call = vIdCall;
	fetch cursor_calls into vIdUser, vIdCall, vPrice, vCost, vProfit;

	end while;

	close cursor_calls;

	COMMIT;

END //

DELIMITER $$
CREATE EVENT IF NOT EXISTS call_billing
ON SCHEDULE EVERY '1' MONTH
STARTS '2020-05-1 00:00:00'
ON COMPLETION PRESERVE
DO
call billing_sp ();
$$

drop EVENT call_billing;
show procedure status






