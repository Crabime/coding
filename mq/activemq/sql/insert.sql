create table person (
  id int auto_increment primary key ,
  name varchar(30),
  sex char
);

create table corder (
  id int auto_increment primary key ,
  order_name varchar(255),
  person_id int,
  constraint foreign key (person_id) references person(id)
);

insert into person(name, sex) values ('ÀîÒ×·å', 'M'), ('·ëÉÜ·å', 'M'), ('ÕÔÀöÓ±', 'F');