Feature: Verschiedene Turingmaschinen werden getestet.

Feature: Verschiedene Turingmaschinen werden getestet.

  Scenario: TM kann binäre Zahlen inkrementieren.
    Given eine TM mit dem Namen BinaryDecrement und 1 Bändern
    And die TM mit dem Namen BinaryDecrement hat den Startzustand sucheEnde
    And die TM mit dem Namen BinaryDecrement hat die Überführungsfunktion:
      | vonZustand    | zuZustand     | eingabeZeichen | zuSchreibendeZeichen | schreibLesekopfBewegungen |
      | sucheEnde     | sucheEnde     | 0              | 0                    | R                         |
      | sucheEnde     | sucheEnde     | 1              | 1                    | R                         |
      | sucheEnde     | dekrementiere | _              | _                    | L                         |
      | dekrementiere | dekrementiere | 0              | 1                    | L                         |
      | dekrementiere | fertig        | 1              | 0                    | N                         |
    And die TM mit dem Namen BinaryDecrement hat den Endzustand fertig
    Then die TM mit dem Namen BinaryDecrement hat bei folgender Eingabe die folgende Ausgabe auf Band 1:
      | eingabe    | ausgabe    |
      | 10         | 1          |
      | 10         | 1          |
      | 1000100010 | 1000100001 |
      | 10001100   | 10001011   |
      | 1          | 0          |
      | 1001000    | 1000111    |
#    And persistiere die TM BinärerAddierer to binaryIncrement

