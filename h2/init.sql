-- drop schema if exists okeanos;

create schema if not exists okeanos;

use okeanos;

create table if not exists account (
	id IDENTITY,
	mail varchar(512),
	salt varchar(512),
	password varchar(512),
	admin boolean default false,
	createdOn timestamp default current_timestamp()
);

create table if not exists adherent_document (
	id IDENTITY,
	file_type varchar(512),
	content_disposition varchar(512),
	data BINARY,
	createdOn timestamp default current_timestamp()
);

create table if not exists adherent_info (
	id IDENTITY,
	fk_account_id bigint not null,
	fk_photo_id bigint,
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
	FOREIGN KEY (fk_account_id) REFERENCES account(id),
	FOREIGN KEY (fk_photo_id) REFERENCES adherent_document(id)
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

MERGE INTO subscription_type (id, label) KEY(id) VALUES (1, 'Plongée');
MERGE INTO subscription_type (id, label) KEY(id) VALUES (2, 'Hockey');

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
	-- Adherent document
	fk_sick_note_id bigint,
	fk_parental_agreement_id bigint,
	createdOn timestamp default current_timestamp(),
	FOREIGN KEY (fk_account_id) REFERENCES account(id),
	FOREIGN KEY (fk_saison_id) REFERENCES saison(id),
	FOREIGN KEY (fk_ffessm_licence_id) REFERENCES ffessm_licence(id),
	FOREIGN KEY (fk_subscription_id) REFERENCES subscription(id),
	FOREIGN KEY (fk_insurance_id) REFERENCES insurance(id),
	FOREIGN KEY (fk_actual_training_id) REFERENCES diving_training(id),
	FOREIGN KEY (fk_training_id) REFERENCES diving_training(id),
	FOREIGN KEY (fk_team_id) REFERENCES hockey_team(id),
	FOREIGN KEY (fk_sick_note_id) REFERENCES adherent_document(id),
	FOREIGN KEY (fk_parental_agreement_id) REFERENCES adherent_document(id)
);



MERGE INTO saison (id, label, start_date, end_date) KEY(id) VALUES (1, '2017 - 2018', '2017-09-01', '2018-08-31');

MERGE INTO ffessm_licence (id, label, fk_saison_id, price) KEY(id) VALUES (1, 'Enfant', 1, 11);
MERGE INTO ffessm_licence (id, label, fk_saison_id, price) KEY(id) VALUES (2, 'Jeune', 1, 25);
MERGE INTO ffessm_licence (id, label, fk_saison_id, price) KEY(id) VALUES (3, 'Adulte', 1, 40);

MERGE INTO subscription (id, label, fk_saison_id, fk_subscription_type_id, price) KEY(id) VALUES (1, 'Plongeur', 1, 1, 150);
MERGE INTO subscription (id, label, fk_saison_id, fk_subscription_type_id, price) KEY(id) VALUES (2, 'Hockeyeur', 1, 2, 119);

MERGE INTO insurance (id, label, fk_saison_id, price) KEY(id) VALUES (1, 'Pisince 1', 1, 11);
MERGE INTO insurance (id, label, fk_saison_id, price) KEY(id) VALUES (2, 'Piscine 2', 1, 25);