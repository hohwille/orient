INSERT INTO DEBundesland (Code,CodeNo,Name,Area,Inhabitants,Country) VALUES ('SH',1,'Schleswig-Holstein',15799,2828986,(SELECT FROM Country WHERE Code='DE')),
('HH',2,'Hamburg',755,1774000,(SELECT FROM Country WHERE Code='DE')),
('NI',3,'Niedersachsen',47624,7997000,(SELECT FROM Country WHERE Code='DE')),
('HB',4,'Bremen',404,663000,(SELECT FROM Country WHERE Code='DE')),
('NW',5,'Nordrhein-Westfalen',34085.27,17996621,(SELECT FROM Country WHERE Code='DE')),
('HE',6,'Hessen',21115,6067021,(SELECT FROM Country WHERE Code='DE')),
('RP',7,'Rheinland-Pfalz',19853,4059604,(SELECT FROM Country WHERE Code='DE')),
('BW',8,'Baden-Württemberg',35752,10739000,(SELECT FROM Country WHERE Code='DE')),
('BY',9,'Bayern',70552,12488000,(SELECT FROM Country WHERE Code='DE')),
('SL',10,'Saarland',2569,1050000,(SELECT FROM Country WHERE Code='DE')),
('BE',11,'Berlin',891.85,3420786,(SELECT FROM Country WHERE Code='DE')),
('BB',12,'Brandenburg',29479,2559000,(SELECT FROM Country WHERE Code='DE')),
('MV',13,'Mecklenburg-Vorpommern',23180,1707000,(SELECT FROM Country WHERE Code='DE')),
('SN',14,'Sachsen',18416,4250000,(SELECT FROM Country WHERE Code='DE')),
('ST',15,'Sachsen-Anhalt',20446,2470000,(SELECT FROM Country WHERE Code='DE')),
('TH',16,'Thüringen',16172,2335000,(SELECT FROM Country WHERE Code='DE'))