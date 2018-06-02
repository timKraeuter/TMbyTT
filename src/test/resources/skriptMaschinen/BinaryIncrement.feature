Feature: Verschiedene Turingmaschinen werden getestet.

Feature: Verschiedene Turingmaschinen werden getestet.

  Scenario: TM kann binäre Zahlen inkrementieren.
    Given eine TM mit dem Namen BinaryIncrement und 1 Bändern
    And die TM mit dem Namen BinaryIncrement hat den Startzustand sucheEnde
    And die TM mit dem Namen BinaryIncrement hat die Überführungsfunktion:
      | vonZustand | zuZustand | eingabeZeichen | zuSchreibendeZeichen | schreibLesekopfBewegungen |
      | sucheEnde  | sucheEnde | 0              | 0                    | R                         |
      | sucheEnde  | sucheEnde | 1              | 1                    | R                         |
      | sucheEnde  | addiere   | _              | _                    | L                         |
      | addiere    | fertig    | 0              | 1                    | N                         |
      | addiere    | addiere   | 1              | 0                    | L                         |
      | addiere    | fertig    | _              | 1                    | N                         |
    And die TM mit dem Namen BinaryIncrement hat den Endzustand fertig
    Then die TM mit dem Namen BinaryIncrement hat bei folgender Eingabe die folgende Ausgabe auf Band 1:
      | eingabe    | ausgabe    |
      | 0          | 1          |
      | 1          | 10         |
      | 01         | 10         |
      | 1000100001 | 1000100010 |
      | 10001011   | 10001100   |
      | 1000111    | 1001000    |
#    And persistiere die TM BinärerAddierer to binaryIncrement

