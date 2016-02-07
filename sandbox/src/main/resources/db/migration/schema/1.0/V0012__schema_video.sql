--VideoGenre
CREATE CLASS VideoGenre EXTENDS AbstractGenre
CREATE PROPERTY VideGenre.Parent Link VideoGenre

--VideoTrack
CREATE CLASS VideoTrack EXTENDS AbstractTrack
CREATE PROPERTY VideoTrack.Genre Link AudioGenre
CREATE PROPERTY VideoTrack.File String
CREATE PROPERTY VideoTrack.PrimaryLanguage Link Language
CREATE PROPERTY VideoTrack.AdditionalLanguages LinkList Language
