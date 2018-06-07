Feature: Verschiedene Turingmaschinen werden getestet.

  Scenario: MehrbandTuringmaschine kann binär addieren.
    Given eine TM mit dem Namen BinärerAddierer und 3 Bändern
    And die TM mit dem Namen BinärerAddierer hat den Startzustand z0
    And die TM mit dem Namen BinärerAddierer hat die Überführungsfunktion:
      | vonZustand    | zuZustand     | eingabeZeichen | zuSchreibendeZeichen | schreibLesekopfBewegungen |
      | z0            | z0            | 0;0;_          | 0;0;_                | R;R;N                     |
      | z0            | z0            | 0;1;_          | 0;1;_                | R;R;N                     |
      | z0            | z0            | 1;0;_          | 1;0;_                | R;R;N                     |
      | z0            | z0            | 1;1;_          | 1;1;_                | R;R;N                     |
      | z0            | z0            | _;0;_          | _;0;_                | N;R;N                     |
      | z0            | z0            | 0;_;_          | 0;_;_                | R;N;N                     |
      | z0            | z0            | _;1;_          | _;1;_                | N;R;N                     |
      | z0            | z0            | 1;_;_          | 1;_;_                | R;N;N                     |
      | z0            | keinUebertrag | _;_;_          | _;_;_                | L;L;N                     |
      | keinUebertrag | keinUebertrag | 0;_;_          | 0;_;0                | L;L;L                     |
      | keinUebertrag | keinUebertrag | _;0;_          | _;0;0                | L;L;L                     |
      | keinUebertrag | keinUebertrag | 1;_;_          | 1;_;1                | L;L;L                     |
      | keinUebertrag | keinUebertrag | _;1;_          | _;1;1                | L;L;L                     |
      | keinUebertrag | keinUebertrag | 0;0;_          | 0;0;0                | L;L;L                     |
      | keinUebertrag | keinUebertrag | 0;1;_          | 0;1;1                | L;L;L                     |
      | keinUebertrag | keinUebertrag | 1;0;_          | 1;0;1                | L;L;L                     |
      | keinUebertrag | Ze            | _;_;_          | _;_;_                | N;N;N                     |
      | keinUebertrag | uebertrag     | 1;1;_          | 1;1;0                | L;L;L                     |
      | uebertrag     | keinUebertrag | 0;_;_          | 0;_;1                | L;L;L                     |
      | uebertrag     | keinUebertrag | _;0;_          | _;0;1                | L;L;L                     |
      | uebertrag     | uebertrag     | _;1;_          | _;1;0                | L;L;L                     |
      | uebertrag     | uebertrag     | 1;_;_          | 1;_;0                | L;L;L                     |
      | uebertrag     | uebertrag     | 0;1;_          | 0;1;0                | L;L;L                     |
      | uebertrag     | uebertrag     | 1;0;_          | 1;0;0                | L;L;L                     |
      | uebertrag     | uebertrag     | 1;1;_          | 1;1;1                | L;L;L                     |
      | uebertrag     | keinUebertrag | 0;0;_          | 0;0;1                | L;L;L                     |
      | uebertrag     | Ze            | _;_;_          | _;_;1                | N;N;N                     |
    And die TM mit dem Namen BinärerAddierer hat den Endzustand Ze
    Then die TM mit dem Namen BinärerAddierer hat bei folgender Eingabe die folgende Ausgabe auf Band 3:
      | eingabe     | ausgabe |
      | 1;0;_       | 1       |
      | 101;100;_   | 1001    |
      | 111;111;_   | 1110    |
      | 1111;1111;_ | 11110   |
      | 0011;1000;_ | 1011    |
      | 0011;0000;_ | 0011    |
      | 0000;0000;_ | 0000    |
      | 100;1000;_  | 1100    |
      | 110;1100;_  | 10010   |
    And die TM BinärerAddierer erkennt die Wörter und erzeugt dabei eine Konsolenausgabe:
      | 110;1100;_ |
#    And persistiere die TM BinärerAddierer to binaryAddition

