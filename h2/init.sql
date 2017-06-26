-- drop schema if exists okeanos;

create schema if not exists okeanos;

use okeanos;

create table if not exists account (
	id IDENTITY,
	mail varchar(512),
	password varchar(512),
	admin boolean default false,
	createdOn timestamp default current_timestamp()
);

create table if not exists adherent_info (
	id IDENTITY,
	fk_account_id bigint not null,
	firstname varchar(512) not null,
	lastname varchar(512) not null,
	birsthday timestamp not null,
	birthplace varchar(512),
	licence_number varchar(512),
	adresse varchar(512),
	zip_code varchar(512),
	city varchar(512),
	job varchar(512),
	tel_number varchar(15),
	mobile_number varchar(15),
	emergency_contact varchar(512),
	emergency_tel_number varchar(15),
	createdOn timestamp default current_timestamp(),
	FOREIGN KEY (fk_account_id) REFERENCES account(id)
);

create table if not exists saison (
	id IDENTITY,
	label varchar(512),
	start_date timestamp,
	end_date timestamp,
	createdOn timestamp default current_timestamp()
);

create table if not exists ffessm_licence (
	id IDENTITY,
	fk_saison_id bigint not null,
	label varchar(512),
	price DOUBLE,
	createdOn timestamp default current_timestamp()
);

create table if not exists subscription_type (
	id IDENTITY,
	label varchar(56),
	createdOn timestamp default current_timestamp()
);

create table if not exists subscription (
	id IDENTITY,
	fk_saison_id bigint not null,
	fk_subscription_type_id bigint not null,
	label varchar(512),
	price DOUBLE,
	createdOn timestamp default current_timestamp(),
	FOREIGN KEY (fk_saison_id) REFERENCES saison(id),
	FOREIGN KEY (fk_subscription_type_id) REFERENCES subscription_type(id)
);

create table if not exists insurance (
	id IDENTITY,
	fk_saison_id bigint not null,
	label varchar(512) not null,
	price int not null,
	createdOn timestamp default current_timestamp(),
	FOREIGN KEY (fk_saison_id) REFERENCES saison(id)
);

create table if not exists require_insurance (
	id IDENTITY,
	fk_subscription_id bigint not null,
	fk_insurance_id bigint not null,
	createdOn timestamp default current_timestamp(),
	FOREIGN KEY (fk_subscription_id) REFERENCES subscription(id),
	FOREIGN KEY (fk_insurance_id) REFERENCES insurance(id)
);

create table if not exists diving_training (
	id IDENTITY,
	label varchar(512),
	createdOn timestamp default current_timestamp()
);

create table if not exists hockey_team (
	id IDENTITY,
	label varchar(512),
	createdOn timestamp default current_timestamp()
);

create table if not exists adherent_info_saison (
	id IDENTITY,
	fk_account_id bigint not null,
	fk_saison_id bigint not null,
	fk_ffessm_licence_id bigint not null,
	fk_subscription_id bigint not null,
	fk_insurance_id bigint,
	picture_authorisation boolean default false,
	-- Diving
	fk_actual_training_id bigint,
	fk_training_id bigint,
	-- Hockey
	fk_team_id bigint,
	createdOn timestamp default current_timestamp(),
	FOREIGN KEY (fk_account_id) REFERENCES account(id),
	FOREIGN KEY (fk_saison_id) REFERENCES saison(id),
	FOREIGN KEY (fk_ffessm_licence_id) REFERENCES ffessm_licence(id),
	FOREIGN KEY (fk_subscription_id) REFERENCES subscription(id),
	FOREIGN KEY (fk_insurance_id) REFERENCES insurance(id),
	FOREIGN KEY (fk_actual_training_id) REFERENCES diving_training(id),
	FOREIGN KEY (fk_training_id) REFERENCES diving_training(id),
	FOREIGN KEY (fk_team_id) REFERENCES hockey_team(id)
);



