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
	prefix int unique not null,#varchar(11)
    id_prov int not null,
    locality_name varchar(50) not null,
    constraint pk_prefix primary key (prefix),
    constraint fk_id_prov foreign key (id_prov) references provinces (id_prov)
);

CREATE TABLE users (
    id int auto_increment not null,
    user_type ENUM("backoffice", "client", "antenna") NOT NULL,
	dni int not null,
	username varchar(50) not null,
    name varchar(50) not null,
    surname varchar(50) not null,
    pass varchar(50) not null,
    suspended boolean default 0,
	constraint pk_id primary key (id)
);

CREATE TABLE phone_lines (
	id_line int auto_increment not null,
    id_user int not null,
    prefix int not null,# varchar(11)
    line_type ENUM("landline", "mobile") NOT NULL,
    line_number varchar(50),
    suspended boolean default 0,
    constraint pk_id_line primary key (id_line),
    constraint fk_id_line_user foreign key (id_user) references users (id),
	constraint fk_prefix foreign key (prefix) references localities (prefix)
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
    prefix_origin int not null,#varchar(11)
    prefix_destiny int not null,#varchar(11)
    price_per_minute float not null,
    start_date date,
    expiration_date date,
    cost float,
    constraint pk_id_rate primary key (id_rate),
    constraint fk_prefix_origin foreign key (prefix_origin) references localities (prefix),
	constraint fk_prefix_destiny foreign key (prefix_destiny) references localities (prefix)
);


CREATE TABLE calls(
	id_call int auto_increment not null,
	line_origin int,
    line_destiny int,
    id_bill int,
    id_rate int,
    price float,
    cost float,
    profit float,
    date_call datetime,
    hour_call_finish datetime,
    duration int,
    number_origin VARCHAR(50),
    number_destiny VARCHAR(50),
    constraint pk_id_call primary key (id_call),
    constraint fk_line_origin foreign key (line_origin) references phone_lines (id_line),
	constraint fk_line_destiny foreign key (line_destiny) references phone_lines (id_line),
	constraint fk_id_bill foreign key (id_bill) references bills (id_bill),
	constraint fk_id_rate foreign key (id_rate) references rates (id_rate)
);

DELIMITER //
CREATE TRIGGER bi_calls BEFORE INSERT ON calls FOR EACH ROW
BEGIN
	declare locality_origin int;
    declare locality_destiny int;
    declare temp_price float;

    # Get the locality from each number
    SET locality_origin = (SELECT prefix from phone_lines where line_number = new.number_origin);
	SET locality_destiny = (SELECT prefix from phone_lines where line_number = new.number_destiny);

	# Getting price x minute
    set temp_price = (select r.price_per_minute
					 from rates r
					 where r.prefix_origin = locality_origin AND r.prefix_destiny = locality_destiny);

    # We calculate the price of the call.
    # We also set the id of the line_origin and id of the line_destiny
    # Finally  we set the id of the rate.
    SET new.price = (temp_price * new.duration);
    SET new.line_origin = (select id_line from phone_lines where line_number = new.number_origin);
    SET new.line_destiny = (select id_line from phone_lines where line_number = new.number_destiny);
    SET new.id_rate = (select r.id_rate from rates r where r.prefix_origin = locality_origin AND r.prefix_destiny = locality_destiny);
    SET new.hour_call_finish = (new.date_call + INTERVAL new.duration MINUTE);
    SET new.cost = ( new.duration * (select r.cost from rates r where r.id_rate = new.id_rate));
    SET new.profit = (new.price - new.cost);
END//

DROP TRIGGER ai_calls;
SHOW TRIGGERS;

# After insert en bill trigger  : que hace un update de la tabla calls , se busca el id de la bill dependiendo del
# valor new.usuario que sacamos de bill.

drop procedure billing_sp;

DELIMITER //
CREATE PROCEDURE billing_sp ()
begin
	declare vFinished int default 0;
	declare vFinishedTwo int default 0;
	declare vIdUser int;
	declare vIdCall int;
	declare vPrice float default 0;
	declare vCost float default 0;
	declare vProfit float default 0;
    declare vLineNumber int;
	declare vLineNumberTwo int;

	declare cursor_bills cursor for
    select l.id_user, l.line_number, sum(c.price), sum(c.cost), sum(c.profit)
	from calls c
    inner join phone_lines l
    where l.id_line = c.line_origin
    AND c.id_bill IS NULL
    group by l.id_user;
	declare continue HANDLER for not found set  vFinished = 1;

    start transaction;
    open cursor_bills;
	setBills : LOOP
		fetch cursor_bills into vIdUser, vLineNumber, vPrice, vCost, vProfit;

		if vFinished = 1 THEN
		    set vFinished = 0;
			close cursor_bills;
			LEAVE setBills;
		END IF;

		insert into bills (id_user, total_price, emittion_date, expiration_date, bill_status, total_cost, total_profit)
		values(vIdUser, vPrice, now(), now() + INTERVAL 15 DAY, false, vCost, vProfit);

    BLOCK2: BEGIN
    declare cursor_calls cursor for
    select c.id_call, l.line_number
	from calls c
	inner join phone_lines l
	where l.id_line = c.line_origin and c.id_bill IS NULL
	group by c.id_call;
	declare continue HANDLER for not found set  vFinishedTwo = 1;
	open cursor_calls;

    setIdCall: LOOP
	fetch cursor_calls into vIdCall, vLineNumberTwo;

    if vFinishedTwo = 1 THEN
		set vFinishedTwo = 0;
        close cursor_calls;
		LEAVE setIdCall;
	END IF;

    if( vLineNumber = vLineNumberTwo) then
		update calls c
		set c.id_bill = last_insert_id()
		where c.id_call = vIdCall;
	end if;

    end loop setIdCall;
    END BLOCK2;
    end loop setBills;
    COMMIT;
END //

drop table bills;

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



DELIMITER //
CREATE TRIGGER bi_phone_lines BEFORE INSERT ON phone_lines FOR EACH ROW
BEGIN
    declare tempResult varchar(50);
    SET tempResult = CONCAT(new.prefix,new.line_number);
    SET new.line_number = tempResult;
END//

drop trigger bi_phone_lines;

SELECT CONCAT('223', '155999999')
from phone_lines a
limit 1;


INSERT INTO provinces (province_name) VALUES ('Buenos Aires');
INSERT INTO provinces (province_name) VALUES ('Cordoba');

INSERT INTO localities (prefix, id_prov, locality_name) VALUES ('223', 1, 'Mar del Plata');
INSERT INTO localities (prefix, id_prov, locality_name) VALUES ('226', 1, 'Miramar');
INSERT INTO localities (prefix, id_prov, locality_name) VALUES ('3541', 2, 'Carlos Paz');

# Rate de mardel a carlos paz
INSERT INTO rates (prefix_origin, prefix_destiny, price_per_minute, start_date, expiration_date, cost)
VALUES ('223', '3541', 2.5,now(),"2020/07/22", 0.5);

# Rate de carlos paz a mardel
INSERT INTO rates (prefix_origin, prefix_destiny, price_per_minute, start_date, expiration_date, cost)
VALUES ('3541', '223', 3.5, now(), "2020/07/22", 1);

# Rate de mardel a Miramar
INSERT INTO rates (prefix_origin, prefix_destiny, price_per_minute, start_date, expiration_date, cost)
VALUES ('223', '226', 4.5, now(), "2020/07/22", 1.5);

INSERT INTO users (user_type, dni, username, name, surname, pass)
VALUES (2, '37867266', 'dema', 'Felipe','Demaria', 'AA8C9E2985A40BDE1CB0443DA3BFEACC');#221901

INSERT INTO users (user_type, dni, username, name, surname, pass)
VALUES (2, '10204050', 'baci', 'Miguel','Bacigaluppi', 'ED1430D0DE792B9FB3C0953CF7049005');#221902
#pass=221902

INSERT INTO users (user_type, dni, username, name, surname, pass)
VALUES (1, '32156521', 'admin', 'Admin','Admin', '21232F297A57A5A743894A0E4A801FC3');#admin

#pass=admin

INSERT INTO users (user_type, dni, username, name, surname, pass)
VALUES (3, '32154684', 'antenna', 'Antenna','Antenna', 'F212100E38F782E152EBFAB712A0E6EC');

#pass=antenna

# Dos lineas : Dema
INSERT INTO phone_lines (id_user, prefix, line_type, line_number)
VALUES (1, '223', 2, '155210762');

INSERT INTO phone_lines (id_user, prefix, line_type, line_number)
VALUES (1, '223', 2, '155234567');

# 1 linea : Baci
INSERT INTO phone_lines (id_user, prefix, line_type, line_number)
VALUES (2, '3541', 2, '155201030');


# Llamo desde dema a baci:

INSERT INTO calls (number_origin, number_destiny, date_call, duration)
VALUES ('223155210762', '3541155201030', now(), 15);

INSERT INTO calls (number_origin, number_destiny, date_call, duration)
VALUES ('223155210762', '3541155201030', now(), 40);

# Llamo desde baci a dema:
INSERT INTO calls (number_origin, number_destiny, date_call, duration)
VALUES ('3541155201030',  '223155210762', now(), 2);

INSERT INTO calls (number_origin, number_destiny, date_call, duration)
VALUES ('3541155201030',  '223155210762', now(), 5);


# Inserto Bills para dema:
INSERT INTO bills(id_user, total_price, emittion_date, expiration_date, bill_status, total_cost, total_profit)
VALUES(1, 1500.2, "2020-07-01", "2020-07-15", false, 200, 20);

INSERT INTO bills(id_user, total_price, emittion_date, expiration_date, bill_status, total_cost, total_profit)
VALUES(1, 1100, "2020-07-01", "2020-07-15", false, 100, 10);

INSERT INTO bills(id_user, total_price, emittion_date, expiration_date, bill_status, total_cost, total_profit)
VALUES(1, 800, "2020-01-01", "2020-02-15", false, 50, 10);
