Feature: Verschiedene Turingmaschinen werden getestet.

  Scenario: TM kopiert das Eingabeband auf das Ausgabeband.
    Given eine TM mit dem Namen CopyNumbers und 2 Bändern
    And die TM mit dem Namen CopyNumbers hat den Startzustand z0
    And die TM mit dem Namen CopyNumbers hat die Überführungsfunktion:
      | vonZustand | zuZustand | eingabeZeichen | zuSchreibendeZeichen | schreibLesekopfBewegungen |
      | z0         | z0        | 0;_            | 0;0                  | R;R                       |
      | z0         | z0        | 1;_            | 1;1                  | R;R                       |
      | z0         | z0        | 2;_            | 2;2                  | R;R                       |
      | z0         | z0        | 3;_            | 3;3                  | R;R                       |
      | z0         | z0        | 4;_            | 4;4                  | R;R                       |
      | z0         | z0        | 5;_            | 5;5                  | R;R                       |
      | z0         | z0        | 6;_            | 6;6                  | R;R                       |
      | z0         | z0        | 7;_            | 7;7                  | R;R                       |
      | z0         | z0        | 8;_            | 8;8                  | R;R                       |
      | z0         | z0        | 9;_            | 9;9                  | R;R                       |
      | z0         | Ze        | _;_            | _;_                  | N;N                       |

    And die TM mit dem Namen CopyNumbers hat den Endzustand Ze
    Then die TM mit dem Namen CopyNumbers hat bei folgender Eingabe die folgende Ausgabe auf Band 2:
    # Testfälle ohne Übertrag
      | eingabe     | ausgabe   |
      | _;_         | _         |
      | 1;_         | 1         |
      | 2;_         | 2         |
      | 3;_         | 3         |
      | 4;_         | 4         |
      | 5;_         | 5         |
      | 6;_         | 6         |
      | 7;_         | 7         |
      | 8;_         | 8         |
      | 9;_         | 9         |
      | 10;_        | 10        |
      | 123456789;_ | 123456789 |
      | 436801243;_ | 436801243 |
