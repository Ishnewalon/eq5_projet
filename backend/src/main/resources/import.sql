-- INSERT INTO SESSIONS VALUES(1,'HIVER',2021);
-- INSERT INTO SESSIONS VALUES(2,'ETE',2021);
INSERT INTO SESSIONS VALUES(3,'HIVER',2022);
INSERT INTO MANAGER VALUES(1, 'admin@email.com', 'richard', 'gautier', 'UnMotDePasse1234', '5145145514');
INSERT INTO MONITOR VALUES(2, 'monitor@email.com', 'Verron', 'Lambert', 'UnMotDePasse1234', '5141234567', '1000, avenue Bouchard', 'Lasalle', 'Informatique', 'H0H0H0');
INSERT INTO OFFER VALUES(1, '1000, avenue Bouchard', now(), now(), now(), 'Informatique', 'Programmeur Java', 'Lundi au Vendredi', '35h', '15 semaines', 28, 'Programmeur', NULL, 2, 3);
-- INSERT INTO OFFER VALUES(14, '1000, avenue Bouchard', now(), now(), now(), 'Informatique', 'Dev React', 'Lundi au Vendredi', '35h', '15 semaines', 28, 'Dev', NULL, 2, 3);
INSERT INTO SUPERVISOR VALUES(4, 'supervisor@email.com', 'Sup', 'ervisor', 'UnMotDePasse1234','5141234567', 'Informatique', '12345');
INSERT INTO STUDENT VALUES(3, 'student@email.com', 'Imem', 'Belcadi', 'UnMotDePasse1234', '5141234567', '1100, avenue Bouchard', 'Lachine', 'Informatique', '1234567', 'H0H0H0', NULL, NULL);
INSERT INTO STUDENT VALUES(5, 'student1@email.com', 'John', 'Kalouy', 'UnMotDePasse1234', '5141234567', '1100, avenue Bouchard', 'Lachine', 'Informatique', '7654321', 'H0H0H0', NULL, 4);

INSERT INTO CURRICULUM VALUES(13, FILE_READ('src/main/resources/contratTemplate.pdf'), true, 'test.pdf', 'application/pdf', 5)
INSERT INTO OFFER_APPLICATION VALUES(12, now(), 'STAGE_TROUVE', 13, 1, 3);
-- INSERT INTO OFFER_APPLICATION VALUES(14, null, 'CV_ENVOYE', 13, 14, 3);
INSERT INTO CONTRACT (ID, STUDENT_ID, OFFER_ID, MONITOR_ID, MANAGER_ID, CONTRACTPDF, SESSION_ID, MANAGER_SIGNATURE,MONITOR_SIGNATURE, STUDENT_SIGNATURE) VALUES (1, 5, 1, 2, 1, FILE_READ('src/main/resources/contratTemplate.pdf'), 3, 'Sign_manager', 'Sign_monitor', 'Sign_student');
INSERT INTO STAGE (ID, CONTRACT_ID) VALUES (1, 1);