drop table semana cascade constraint;

create table semana 
(
triangulo varchar2(15),
dia varchar2(20),
primary key (triangulo)
);

insert into semana values ('laranxa', 'luns');
insert into semana values ('verde','martes');
insert into semana values ('vermello','mercores');
insert into semana values ('amarelo','xoves');
insert into semana values ('azul','venres');
insert into semana values ('branco','sabado');
insert into semana values ('gris','domingo');

commit;

drop table taxistas cascade constraints;

create table taxistas(
	numa NUMBER,
	circulo varchar2(20),
	nombre varchar2(20),
	dni NUMBER,
	edad NUMBER, 
	primary key (numa),
	FOREIGN KEY (circulo) REFERENCES semana (triangulo)
);

insert into taxistas values ('1','verde','juan',3645,28);
insert into taxistas values ('2','azul','pedro',3628,30);
insert into taxistas values ('3','azul','luis',3652,40);
insert into taxistas values ('4','verde','ana',3611,25);
insert into taxistas values ('5','azul','juan',3614,50);
insert into taxistas values ('6','amarelo','luis',3618,20);
insert into taxistas values ('7','amarelo','pedro',3621,70);
insert into taxistas values ('8','verde','benito',3619,40);
insert into taxistas values ('9','vermello','ruben',3613,50);
insert into taxistas values ('10','verde','ricardo',3633,50);
insert into taxistas values ('11','amarelo','eva',3622,27);
insert into taxistas values ('12','vermello','luis',3677,33);

commit;
select * from taxistas;

DROP TABLE ingresos CASCADE CONSTRAINT;

CREATE TABLE ingresos (
	codigo NUMBER PRIMARY KEY,
	numerot NUMBER,
	cobro NUMBER,
	propina NUMBER,
	FOREIGN KEY (numerot) REFERENCES taxistas (numa)
);
INSERT INTO ingresos VALUES (1, 2, 9, 0.10);
INSERT INTO ingresos VALUES (2, 3, 9, 0.4);
INSERT INTO ingresos VALUES (3, 2, 5, 0.6);
INSERT INTO ingresos VALUES (4, 1, 9, 0.4);
INSERT INTO ingresos VALUES (5, 12, 6, 0.5);
INSERT INTO ingresos VALUES (6, 11, 6, 0.5);
INSERT INTO ingresos VALUES (7, 4, 4, 0.4);
INSERT INTO ingresos VALUES (8, 2, 4, 0.6);
INSERT INTO ingresos VALUES (9, 1, 6, 0.3);
INSERT INTO ingresos VALUES (10, 6, 10, 0.3);
INSERT INTO ingresos VALUES (11, 6, 7, 0.5);
INSERT INTO ingresos VALUES (12, 6, 8, 0.6);
INSERT INTO ingresos VALUES (13, 8, 5, 0.4);

COMMIT;
