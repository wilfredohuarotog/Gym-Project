INSERT INTO coach (first_name, last_name, phone, dni) VALUES ('Jaime','Espinoza','983234122','78898231');
INSERT INTO coach (first_name, last_name, phone, dni) VALUES ('Raul','Mendez','983234125','78898232');
INSERT INTO coach (first_name, last_name, phone, dni) VALUES ('Ximena','Calderon','983234126','78898233');

INSERT INTO class (name, capacity, coach_id) VALUES ('ZUMBA', 50, 3);
INSERT INTO class (name, capacity, coach_id) VALUES ('YOGA', 15, 1);
INSERT INTO class (name, capacity, coach_id) VALUES ('CROSSFIT', 20, 1);

INSERT INTO schedule (day, start_time, end_time, classes_id) VALUES ('MONDAY', '10:00', '12:00', 1);
INSERT INTO schedule (day, start_time, end_time, classes_id) VALUES ('WEDNESDAY', '16:00', '18:00',1);
INSERT INTO schedule (day, start_time, end_time, classes_id) VALUES ('FRIDAY', '18:30', '20:30',2);
INSERT INTO schedule (day, start_time, end_time, classes_id) VALUES ('THURSDAY', '12:15', '12:20',3);

INSERT INTO member_ship (name) VALUES ("PREMIUM");
INSERT INTO member_ship (name) VALUES ("BASIC");
INSERT INTO member_ship (name) VALUES ("GOLD");

INSERT INTO member_ship_and_classes (member_ship_id, class_id) VALUES (1,1);
INSERT INTO member_ship_and_classes (member_ship_id, class_id) VALUES (1,2);
INSERT INTO member_ship_and_classes (member_ship_id, class_id) VALUES (1,3);
INSERT INTO member_ship_and_classes (member_ship_id, class_id) VALUES (2,1);
INSERT INTO member_ship_and_classes (member_ship_id, class_id) VALUES (3,2);
INSERT INTO member_ship_and_classes (member_ship_id, class_id) VALUES (3,3);

-- Acuerdo de 6 meses para PREMIUM
INSERT INTO agreements (months, start_date, end_date, member_ship_id) VALUES (6, '2025-09-01', '2025-11-01', 1);

-- Acuerdo de 3 meses para BASIC
INSERT INTO agreements (months, start_date, end_date, member_ship_id) VALUES (3, '2025-02-01', '2025-11-01', 2);

-- Acuerdo de 12 meses para GOLD
INSERT INTO agreements (months, start_date, end_date, member_ship_id) VALUES (12, '2025-03-01', '2026-11-01', 3);

INSERT INTO members (first_name, last_name, dni, email, phone_number, status, agreement_id) VALUES ('Carlos', 'Ramirez', '12345678', 'carlos.ramirez@gmail.com', '987654321', 'ACTIVE', 1);

INSERT INTO members (first_name, last_name, dni, email, phone_number, status, agreement_id) VALUES ('Lucia', 'Fernandez', '87654321', 'lucia.fernandez@gmail.com', '912345678', 'ACTIVE', 2);

INSERT INTO members (first_name, last_name, dni, email, phone_number, status, agreement_id) VALUES ('Pedro', 'Lopez', '11223344', 'pedro.lopez@gmail.com', '956123478', 'ACTIVE', 3);

-- Carlos (member_id=1) se inscribe a YOGA (class_id=2, schedule_id=2 - miércoles 16:00 a 18:00)
INSERT INTO registrations (class_id, member_id, schedule_id) VALUES (2, 1, 2);

-- Lucia (member_id=2) se inscribe a ZUMBA (class_id=1, schedule_id=1 - lunes 10:00 a 12:00)
INSERT INTO registrations (class_id, member_id, schedule_id) VALUES (1, 2, 1);

-- Pedro (member_id=3) se inscribe a CROSSFIT (class_id=3, schedule_id=3 - viernes 18:30 a 20:30)
INSERT INTO registrations (class_id, member_id, schedule_id) VALUES (3, 3, 3);


-- Expiró hace poco en agosto 2025 (6 meses GOLD)
INSERT INTO agreements (months, start_date, end_date, member_ship_id) VALUES (6, '2025-02-01', '2025-08-01', 3);
INSERT INTO members (first_name, last_name, dni, email, phone_number, status, agreement_id) VALUES ('Melissa', 'Lopez', '11223399', 'wasa@gmail.com', '956123111', 'ACTIVE', 4);