
--Genre
CREATE CLASS AbstractGenre EXTENDS AbstractName,AbstractCode ABSTRACT

--Track
CREATE CLASS AbstractTrack EXTENDS AbstractName ABSTRACT
CREATE PROPERTY AbstractTrack.Length Long --length in seconds
CREATE PROPERTY AbstractTrack.Tags String --comma separated list of tags (e.g "hits,cool,party")
