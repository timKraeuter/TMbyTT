Feature: Verschiedene Turingmaschinen werden getestet.

  Scenario: TM kopiert das Eingabeband auf das Ausgabeband.
    Given eine TM mit dem Namen Decrement und 1 Bändern
    And die TM mit dem Namen Decrement hat den Startzustand nachR
    And die TM mit dem Namen Decrement hat die Überführungsfunktion:
      | vonZustand         | zuZustand          | eingabeZeichen | zuSchreibendeZeichen | schreibLesekopfBewegungen |
      | nachR              | nachR              | ~              | ~                    | R                         |
      | nachR              | decrement          | _              | _                    | L                         |
      | decrement          | decrement          |              0 |                    9 | L                         |
      #Der gute fall, in welchem man nicht negativ wird
      | decrement          | nachL              |              1 |                    0 | L                         |
      | decrement          | nachL              |              2 |                    1 | L                         |
      | decrement          | nachL              |              3 |                    2 | L                         |
      | decrement          | nachL              |              4 |                    3 | L                         |
      | decrement          | nachL              |              5 |                    4 | L                         |
      | decrement          | nachL              |              6 |                    5 | L                         |
      | decrement          | nachL              |              7 |                    6 | L                         |
      | decrement          | nachL              |              8 |                    7 | L                         |
      | decrement          | nachL              |              9 |                    8 | L                         |
      #Der schlechte Fall, in dem man negativ wird, weshalb das ganze Band mit Käse beschmiert wird
      | decrement          | noNegativesAllowed | _              | _                    | R                         |
      #markieren aller felder als ungültig
      | noNegativesAllowed | noNegativesAllowed | ~              | §                    | R                         |
      | noNegativesAllowed | nachL              | _              | _                    | L                         |
      #Schreiberkopf nach Links resetten
      | nachL              | nachL              | ~              | ~                    | L                         |
      | nachL              | fertig             | _              | _                    | R                         |
    And die TM mit dem Namen Decrement hat den Endzustand fertig
    Then die TM mit dem Namen Decrement hat bei folgender Eingabe die folgende Ausgabe auf Band 1:
      | eingabe  | ausgabe  |
      |        0 | §        |
      |        1 |        0 |
      |        2 |        1 |
      |        3 |        2 |
      |        4 |        3 |
      |        5 |        4 |
      |        6 |        5 |
      |        7 |        6 |
      |        8 |        7 |
      |        9 |        8 |
      |       10 |        9 |
      |       11 |       10 |
      |       99 |       98 |
      |      100 |       99 |
      |      105 |      104 |
      | 99999999 | 99999998 |
      |  1000000 |   999999 |
      |   000000 | §§§§§§   |
    #And persistiere die TM Decrement to decrementerTM
