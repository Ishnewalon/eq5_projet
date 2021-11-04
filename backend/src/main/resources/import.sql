INSERT INTO MANAGER VALUES(1, 'admin@email.com', 'richard', 'gautier', 'UnMotDePasse1234', '5145145514');
INSERT INTO MONITOR VALUES(2, 'monitor@email.com', 'Verron', 'Lambert', 'UnMotDePasse1234', '5141234567', '1000, avenue Bouchard', 'Lasalle', 'Informatique', 'H0H0H0');
INSERT INTO OFFER VALUES(1, '1000, avenue Bouchard', now(), 'Informatique', 'UneDescription', 28, 'Un Titre', NULL, 2);
INSERT INTO OFFER VALUES(2, '1000, avenue Bouchard', now(), now(), now(), 'Informatique', 'Programmeur Java', 'Lundi au Vendredi', '35h', '15 semaines', 28, 'Programmeur', NULL, 2);
INSERT INTO STUDENT VALUES(3, 'student@email.com', 'Imem', 'Belcadi', 'UnMotDePasse1234', '5141234567', '1100, avenue Bouchard', 'Lachine', 'Informatique', '1234567', 'H0H0H0', NULL);
INSERT INTO SUPERVISOR VALUES(4, 'supervisor@email.com', 'Sup', 'ervisor', 'UnMotDePasse1234','5141234567', 'Informatique', '12345');
INSERT INTO CONTRACT (ID, STUDENT_ID, OFFER_ID, CONTRACTPDF) VALUES (1, 3, 2, FILE_READ('src/main/resources/contratTemplate.pdf'));