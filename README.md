# Restaurant App

Jednostavna Android aplikacija za upravljanje restoranima.  
Omogućuje dodavanje, uređivanje i brisanje restorana, sa sljedećim podacima:

- Naziv restorana
- Lokacija
- Tip kuhinje (Talijanska, Meksička, Američka, Azijska, Mediteranska)
- Datum otvaranja
- Slika

---

## Zaslon aplikacije

1. **Lista restorana**
    - Prikazuje sve unesene restorane u listi.
    - Svaka stavka pokazuje ime, lokaciju, tip kuhinje i sliku (ako postoji).
    - Gumb za brisanje i klik na stavku za uređivanje.

2. **Dodavanje / Uređivanje restorana**
    - Forma s poljima: naziv, lokacija, tip kuhinje (dropdown), datum otvaranja i slika.
    - Gumbovi `Spremi` i `Otkaži`.
    - Datum prikazan u formatu `dd.MM.yyyy` s opcijom odabira.

---

## Tehnologije

- Kotlin
- Jetpack Compose
- Material3
- Coil (za prikaz slika)

