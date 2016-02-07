--Person (there are no links from Person to Address or ContactInfo as this is not flexible, use TypedEdge instead)
CREATE CLASS Person EXTENDS V
CREATE PROPERTY Person.NamePrefix String
CREATE PROPERTY Person.FirstName String
CREATE PROPERTY Person.MiddleNames String
CREATE PROPERTY Person.LastName String
CREATE PROPERTY Person.NameSuffix String
CREATE PROPERTY Person.BirthName String
CREATE PROPERTY Person.Birthday Date
CREATE PROPERTY Person.Gender String
ALTER PROPERTY Person.Gender REGEXP [M|F|N]

--Address
CREATE CLASS Address EXTENDS AbstractLocation
CREATE PROPERTY Address.Country Link Country
CREATE PROPERTY Address.State Link State
CREATE PROPERTY Address.City String
CREATE PROPERTY Address.PostalCode String
CREATE PROPERTY Address.Street String
CREATE PROPERTY Address.StreetNumber String
CREATE PROPERTY Address.PoBox String
CREATE PROPERTY Address.Addition String

--ContactInfo
CREATE CLASS ContactInfo EXTENDS V
CREATE PROPERTY ContactInfo.Phone String
CREATE PROPERTY ContactInfo.Phone2 String
CREATE PROPERTY ContactInfo.Fax String
CREATE PROPERTY ContactInfo.Email String
CREATE PROPERTY ContactInfo.Email2 String
CREATE PROPERTY ContactInfo.Homepage String
CREATE PROPERTY ContactInfo.Messaging String

--Recurrence
CREATE CLASS Recurrence EXTENDS V
CREATE PROPERTY Recurrence.Unit String
ALTER PROPERTY Recurrence.Unit MANDATORY TRUE
ALTER PROPERTY Recurrence.Unit REGEXP [D|W|M|Y]
CREATE PROPERTY Recurrence.Step Integer
CREATE PROPERTY Recurrence.Weekdays String
CREATE PROPERTY Recurrence.WeekOfMonth String

--Event
CREATE CLASS Event EXTENDS V
CREATE PROPERTY Event.InitialStartDate DateTime
CREATE PROPERTY Event.NextStartDate DateTime
ALTER PROPERTY Event.NextStartDate MANDATORY TRUE
CREATE PROPERTY Event.Name String
CREATE PROPERTY Event.Description String
CREATE PROPERTY Event.Recurrence Embedded Recurrence

--Appointment
CREATE CLASS Appointment EXTENDS Event
CREATE PROPERTY Appointment.Description String
CREATE PROPERTY Appointment.InitialEndDate DateTime
