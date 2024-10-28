---
author: Lektion 7
date: MMMM dd, YYYY
paging: "%d / %d"
---

# Teams lektion 7

Hej och välkommen!

## Agenda

1. Frågor och repetition
2. Genomgång av reflection & annotations
3. Övning med handledning
4. Genomgång av reflection övning 3

---

# Fråga

Är det god programmeringssed att kringgå att man inte kan referera till en static metod från en non-static metod genom att lägga till package-namnet innan klassens namn, e.g:
`MyMoneyPackage.ExitProgramClass.exitProgramNow()`
eller finns det bättre sätt?

# Svar

Man kan importera klassen:
`import MyMoneyPackage.ExitProgramClass`

Det är dock okej att skriva hela paket-namnet. 
