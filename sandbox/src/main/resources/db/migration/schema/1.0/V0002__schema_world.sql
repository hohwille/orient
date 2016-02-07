--Language (e.g. German, French, etc. - Code is 639-2/T)
CREATE CLASS Language EXTENDS AbstractLocalizedName,AbstractCode
CREATE PROPERTY Language.Code1 String --ISO 639-1
CREATE PROPERTY Language.Code2T String --ISO 639-2/T
CREATE PROPERTY Language.Locale String --e.g. "en" or "en_US"

--AbstractArea
CREATE CLASS AbstractArea EXTENDS AbstractLocation,AbstractLocalizedName,AbstractCode ABSTRACT
CREATE PROPERTY AbstractArea.Inhabitants Long
CREATE PROPERTY AbstractArea.Area Long --km²

--Country
CREATE CLASS Country EXTENDS AbstractArea
CREATE PROPERTY Country.AreaCode String --Calling Code (e.g. +49)
CREATE PROPERTY Country.Tld String --Top Level Domain (e.g. .de)
CREATE PROPERTY Country.CodeIso3 String --ISO 3166-1 alpha-3 (e.g. DEU)
CREATE PROPERTY Country.CodeIoc String --International Olympic Committee (e.g. GER)
CREATE PROPERTY Country.CodeIsoNo Integer --ISO 3166-1 numeric code (e.g. 276)
CREATE PROPERTY Country.Currency String --National Currency (e.g. EUR)
CREATE PROPERTY Country.PostalCodePattern String --Regex for Postal-Codes
CREATE PROPERTY Country.PostalCodeMode String --Postal-Code mode (none=country has no postal codes, auto=city is autocompleted via PostalCode class, text=no completion but ony pattern match)
ALTER PROPERTY Country.PostalCodeMode REGEXP (none|text|auto)

--State
CREATE CLASS State EXTENDS AbstractArea
CREATE PROPERTY State.Country Link Country

--City
CREATE CLASS City EXTENDS AbstractArea

--PostalCode
CREATE CLASS PostalCode EXTENDS AbstractCode
CREATE PROPERTY PostalCode.Country Link Country
CREATE PROPERTY PostalCode.Cities LinkList City
