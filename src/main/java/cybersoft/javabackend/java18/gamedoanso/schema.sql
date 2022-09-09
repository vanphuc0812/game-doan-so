create database if not exists gamedoanso;

use gamedoanso;

create table if not exists player(
	username varchar(255),
	password varchar(255) not null,
	name varchar(255) not null,
	primary key (username)
);

create table if not exists game_session(
	id varchar(9),
	target int not null,
	start_time timestamp not null,
	end_time timestamp,
	is_completed int(1) not null default 0,
	is_active int(1) not null default 0,
	username varchar(255),
	primary key (id)
);
-- statement
-- prepared statement
-- callable statement
create table if not exists guess(
	id int auto_increment,
	value int not null,
	moment timestamp default CURRENT_TIMESTAMP,
	session_id varchar(9),
    result int(1),
	primary key (id)
);

alter table game_session
	add constraint FK_GAME_SESSION_PLAYER
	foreign key (username) references player(username);

alter table guess
	add constraint FK_GUESS_GAME_SESSION
	foreign key (session_id) references game_session(id);
