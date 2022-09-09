insert into player(username, password, name)
values ('admin', '1234', 'Administrator');


create table if not exists game_session
(
    id           varchar(9),
    target       int       not null,
    start_time   timestamp not null,
    end_time     timestamp,
    is_completed int(1)    not null default 0,
    is_active    int(1)    not null default 0,
    username     varchar(255),
    primary key (id)
);
create table if not exists guess
(
    id         int auto_increment,
    value      int not null,
    moment     timestamp default CURRENT_TIMESTAMP,
    session_id varchar(9),
    result     int(1),
    primary key (id)
);


insert into player(username, password, name)
values ('phuc', '1234', 'Phúc');
insert into player(username, password, name)
values ('vinh', '1234', 'Vinh');

insert into game_session(id, target, start_time, end_time, is_completed, is_active, username)
values ('GAME12345', '124', '2022-08-17T00:00', '2022-08-17T01:00', 1, 1, 'phuc');

insert into guess(id, value, moment, session_id, result)
values ('30', '124', '2022-08-17T01:00', 'GAME12358', -1);
insert into guess(id, value, moment, session_id, result)
values ('31', '164', '2022-08-17T01:018', 'GAME12358', 1);
insert into guess(id, value, moment, session_id, result)
values ('32', '124', '2022-08-17T01:00', 'GAME12349', -1);