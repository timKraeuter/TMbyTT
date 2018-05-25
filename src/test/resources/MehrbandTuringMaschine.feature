Feature: Verschiedene Turingmaschinen werden getestet.

  Scenario: MehrbandTuringmaschine kann binär addieren.
    Given eine TM mit dem Namen Testgedöns und 3 Bändern
    And die TM mit dem Namen Testgedöns hat den Startzustand z0
    And die TM mit dem Namen Testgedöns hat die Überführungsfunktion:
      | vonZustand    | zuZustand     | eingabeZeichen | zuSchreibendeZeichen | schreibLesekopfBewegungen |
      | z0            | z0            | 0;0;_          | 0;0;_                | R;R;N                     |
      | z0            | z0            | 0;1;_          | 0;1;_                | R;R;N                     |
      | z0            | z0            | 1;0;_          | 1;0;_                | R;R;N                     |
      | z0            | z0            | 1;1;_          | 1;1;_                | R;R;N                     |
      | z0            | z0            | _;0;_          | _;0;_                | R;R;N                     |
      | z0            | z0            | 0;_;_          | 0;_;_                | R;R;N                     |
      | z0            | z0            | _;1;_          | _;1;_                | R;R;N                     |
      | z0            | z0            | 1;_;_          | 1;_;_                | R;R;N                     |
      | z0            | keinUebertrag | _;_;_          | _;_;_                | L;L;N                     |
      | keinUebertrag | keinUebertrag | 0;0;_          | 0;0;0                | L;L;L                     |
      | keinUebertrag | keinUebertrag | 0;1;_          | 0;1;1                | L;L;L                     |
      | keinUebertrag | keinUebertrag | 1;0;_          | 1;0;1                | L;L;L                     |
      | keinUebertrag | Ze            | _;_;_          | _;_;_                | N;N;N                     |
      | keinUebertrag | uebertrag     | 1;1;_          | 1;1;0                | L;L;L                     |
      | uebertrag     | uebertrag     | 0;1;_          | 0;1;0                | L;L;L                     |
      | uebertrag     | uebertrag     | 1;0;_          | 1;0;0                | L;L;L                     |
      | uebertrag     | uebertrag     | 1;1;_          | 1;1;1                | L;L;L                     |
      | uebertrag     | keinUebertrag | 0;0;_          | 0;0;1                | L;L;L                     |
      | uebertrag     | Ze            | _;_;_          | _;_;1                | N;N;N                     |
    And die TM mit dem Namen Testgedöns hat den Endzustand Ze
    Then die TM mit dem Namen Testgedöns hat bei folgender Eingabe die folgende Ausgabe auf Band 2:
      | eingabe | ausgabe |
      | 1;0;_   |       1 |
  
