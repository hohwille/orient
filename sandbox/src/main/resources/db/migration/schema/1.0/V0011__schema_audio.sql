--AudioGenre
CREATE CLASS AudioGenre EXTENDS AbstractGenre
CREATE PROPERTY AudioGenre.Parent Link AudioGenre
CREATE PROPERTY AudioGenre.Id3 Integer

--AudioTrack
CREATE CLASS AudioTrack EXTENDS AbstractTrack
CREATE PROPERTY AudioTrack.Genre Link AudioGenre
CREATE PROPERTY AudioTrack.PrimaryLanguage Link Language
CREATE PROPERTY AudioTrack.AdditionalLanguages LinkList Language
CREATE PROPERTY AudioTrack.Bpm Integer
CREATE PROPERTY AudioTrack.MusicalKey String
