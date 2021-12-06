-- INSERT INTO SESSIONS VALUES(1,'HIVER',2021);
-- INSERT INTO SESSIONS VALUES(2,'ETE',2021);
INSERT INTO SESSIONS VALUES(3,'HIVER',2022);
INSERT INTO MANAGER VALUES(1, 'admin@email.com', 'richard', 'gautier', 'adminadmin', '5145145514');
INSERT INTO MONITOR VALUES(2, 'monitor@email.com', 'Verron', 'Lambert', 'adminadmin', '5141234567', '1000, avenue Bouchard', 'Lasalle', 'Informatique', 'H0H0H0');
INSERT INTO OFFER VALUES(1, '1000, avenue Bouchard', now(), now(), now(), 'Concevoir des solutions pour répondre aux besoins d''affaires', 'Concevoir des solutions pour répondre aux besoins d''affaires', 'Lundi au Vendredi', '35h', '15 semaines', 19, 'Programmeur Java', true, 2, 3);
INSERT INTO OFFER VALUES(14, '1000, avenue Bouchard', now(), now(), now(), 'Informatique', 'Travailler sur des projets porteurs en mode agile', 'Lundi au Vendredi', '35h', '15 semaines', 21, 'Programmeur Java', true, 2, 3);
INSERT INTO OFFER VALUES(15, '1000, avenue Bouchard', now(), now(), now(), 'Informatique', 'Contribuer au développement de systèmes, programmes et applications informatiques', 'Lundi au Vendredi', '35h', '15 semaines', 19, 'Programmeur React', true, 2, 3);
INSERT INTO OFFER VALUES(16, '1000, avenue Bouchard', now(), now(), now(), 'Informatique', 'Configurez, administrez, maintenez et faites évoluer les réseaux informatiques', 'Lundi au Vendredi', '35h', '15 semaines', 20, 'Technicien Informatique', true, 2, 3);
INSERT INTO SUPERVISOR VALUES(4, 'supervisor@email.com', 'Sup', 'ervisor', 'adminadmin','5141234567', 'Informatique', '12345');
INSERT INTO STUDENT VALUES(3, 'student@email.com', 'Imem', 'Belcadi', 'adminadmin', '5141234567', '1100, avenue Bouchard', 'Lachine', 'Informatique', '1234567', 'H0H0H0', NULL, NULL);
INSERT INTO STUDENT VALUES(33, 'scott@email.com', 'Jordan', 'Scott', 'adminadmin', '5148886666', '1100, avenue Bouchard', 'Lachine', 'Informatique', '2343243', 'H0H0H0', NULL, NULL);
INSERT INTO STUDENT VALUES(34, 'tom@email.com', 'Tom', 'Knope', 'adminadmin', '4509998888', '1100, avenue Bouchard', 'Lachine', 'Informatique', '2343242', 'H0H0H0', NULL, NULL);
INSERT INTO STUDENT VALUES(5, 'student1@email.com', 'John', 'Kalouy', 'adminadmin', '5141234567', '1100, avenue Bouchard', 'Lachine', 'Informatique', '7654321', 'H0H0H0', NULL, 4);

INSERT INTO CURRICULUM VALUES(13, FILE_READ('src/main/resources/contratTemplate.pdf'), true, 'test.pdf', 'application/pdf', 5)
INSERT INTO OFFER_APPLICATION VALUES(12, now(), 'STAGE_TROUVE', 13, 1, 3);
-- INSERT INTO OFFER_APPLICATION VALUES(14, null, 'CV_ENVOYE', 13, 14, 3);
INSERT INTO CONTRACT (ID, STUDENT_ID, OFFER_ID, MONITOR_ID, MANAGER_ID, CONTRACTPDF, SESSION_ID, MANAGER_SIGNATURE,MONITOR_SIGNATURE, STUDENT_SIGNATURE) VALUES (1, 5, 1, 2, 1, FILE_READ('src/main/resources/contratTemplate.pdf'), 3, 'Sign_manager', 'Sign_monitor', 'Sign_student');
INSERT INTO STAGE (ID, CONTRACT_ID) VALUES (1, 1);