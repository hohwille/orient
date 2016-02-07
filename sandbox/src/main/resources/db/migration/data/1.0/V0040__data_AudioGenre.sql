INSERT INTO AudioGenre (Code,Id3,Parent,Name) VALUES ('Audio',null,null,'Audio'),
('Music',null,(SELECT FROM AudioGenre WHERE Code='Audio'),'Music'),
('Speech',null,(SELECT FROM AudioGenre WHERE Code='Audio'),'Speech'),
('Speech',null,(SELECT FROM AudioGenre WHERE Code='Audio'),'Speech'),
