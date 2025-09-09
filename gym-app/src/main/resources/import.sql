INSERT INTO coach (first_name, last_name, phone, dni) VALUES ('Jaime','Espinoza','983234122','78898231');
INSERT INTO coach (first_name, last_name, phone, dni) VALUES ('Raul','Mendez','983234125','78898232');
INSERT INTO coach (first_name, last_name, phone, dni) VALUES ('Ximena','Calderon','983234126','78898233');

INSERT INTO class (name, capacity, coach_id) VALUES ('ZUMBA', 50, 3);
INSERT INTO class (name, capacity, coach_id) VALUES ('YOGA', 15, 1);
INSERT INTO class (name, capacity, coach_id) VALUES ('CROSSFIT', 20, 1);

INSERT INTO schedule (day, start_time, end_time, schedule_id) VALUES ('MONDAY', '10:00', '12:00', 1);
INSERT INTO schedule (day, start_time, end_time, schedule_id) VALUES ('WEDNESDAY', '16:00', '18:00',1);
INSERT INTO schedule (day, start_time, end_time, schedule_id) VALUES ('FRIDAY', '18:30', '20:30',2);
INSERT INTO schedule (day, start_time, end_time, schedule_id) VALUES ('THURSDAY', '16:00', '18:00',3);

INSERT INTO member_ship (name) VALUES ("PREMIUM");
INSERT INTO member_ship (name) VALUES ("BASIC");

INSERT INTO member_ship_and_classes (member_ship_id, class_id) VALUES (1,1);
INSERT INTO member_ship_and_classes (member_ship_id, class_id) VALUES (1,2);
INSERT INTO member_ship_and_classes (member_ship_id, class_id) VALUES (1,3);
INSERT INTO member_ship_and_classes (member_ship_id, class_id) VALUES (2,1);