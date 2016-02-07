--TypedEdge (used as classified link, e.g. from Person to Address with types such as private,business,parents,etc.)
CREATE CLASS TypedEdge EXTENDS E
CREATE PROPERTY TypedEdge.Type String

--Named (Vertex with mandatory Name property)
CREATE CLASS AbstractName EXTENDS V ABSTRACT
CREATE PROPERTY AbstractName.Name String
ALTER PROPERTY AbstractName.Name MANDATORY TRUE

--Coded (Vertext with mandatory Code property)
CREATE CLASS AbstractCode EXTENDS V ABSTRACT
CREATE PROPERTY AbstractCode.Code String
ALTER PROPERTY AbstractCode.Code MANDATORY TRUE

--AbstractLocalizedName (Vertex with Name property and L10N e.g. for Language or Country)
CREATE CLASS AbstractLocalizedName EXTENDS AbstractName ABSTRACT
CREATE PROPERTY AbstractLocalizedName.LocalizedNames Embedded -- e.g. {"@type":"d","de":"Deutsch","fr":"allemand"}
CREATE PROPERTY AbstractLocalizedName.NativeName String

--AbstractLocation
CREATE CLASS AbstractLocation EXTENDS V ABSTRACT
CREATE PROPERTY AbstractLocation.Lattitude Double
CREATE PROPERTY AbstractLocation.Longitude Double
