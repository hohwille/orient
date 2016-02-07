--Bundesland
CREATE CLASS DEBundesland EXTENDS State
CREATE PROPERTY DEBundesland.CodeNo Integer

--Regierungsbezirk
CREATE CLASS DERegierungsbezirk EXTENDS AbstractArea
CREATE PROPERTY DERegierungsbezirk.Bundesland Link DEBundesland

--Landkreis
CREATE CLASS DELandkreis EXTENDS AbstractArea
CREATE PROPERTY DELandkreis.Regierungsbezirk Link DERegierungsbezirk

--Gemeinde
CREATE CLASS DEGemeinde EXTENDS City
CREATE PROPERTY DEGemeinde.Landkreis Link DELandkreis

--Gemeindeteil
CREATE CLASS DEGemeindeteil EXTENDS City
CREATE PROPERTY DEGemeindeteil.Gemeinde Link DEGemeinde

--
CREATE PROPERTY DEBundesland.Capital Link DEGemeinde
