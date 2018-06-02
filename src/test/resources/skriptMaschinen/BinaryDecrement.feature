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
      | dekrementiere | eingabeWar0   | _              | _                    | R                         |
      | eingabeWar0   | fertig        | 1              | 0                    | N                         |
      | dekrementiere | fertig        | 1              | 0                    | N                         |
    And die TM mit dem Namen BinaryDecrement hat den Endzustand fertig
    Then die TM mit dem Namen BinaryDecrement hat bei folgender Eingabe die folgende Ausgabe auf Band 1:
      | eingabe    | ausgabe    |
      | 0          | 0          |
      | 1          | 0          |
      | 10         | 1          |
      | 1011       | 1010       |
      | 1001000    | 1000111    |
      | 10001100   | 10001011   |
      | 1000100010 | 1000100001 |
