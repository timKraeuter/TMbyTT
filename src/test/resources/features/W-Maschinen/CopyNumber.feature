Feature: Turing-Maschine zum Kopieren von Eingaben.

  Scenario: TM kopiert das Eingabeband auf das Ausgabeband.
    Given eine TM mit dem Namen CopyNumbers und 2 Bändern
    And die TM mit dem Namen CopyNumbers hat den Startzustand z0
    And die TM mit dem Namen CopyNumbers hat die Überführungsfunktion:
      | vonZustand    | zuZustand     | eingabeZeichen | zuSchreibendeZeichen | schreibLesekopfBewegungen |
      | z0            | z0            | *;~            | *;_                  | N;R                       |
      | z0            | wiped         | *;_            | *;_                  | N;N                       |
      | wiped         | wiped         | 0;_            | 0;0                  | R;R                       |
      | wiped         | wiped         | 1;_            | 1;1                  | R;R                       |
      | wiped         | wiped         | 2;_            | 2;2                  | R;R                       |
      | wiped         | wiped         | 3;_            | 3;3                  | R;R                       |
      | wiped         | wiped         | 4;_            | 4;4                  | R;R                       |
      | wiped         | wiped         | 5;_            | 5;5                  | R;R                       |
      | wiped         | wiped         | 6;_            | 6;6                  | R;R                       |
      | wiped         | wiped         | 7;_            | 7;7                  | R;R                       |
      | wiped         | wiped         | 8;_            | 8;8                  | R;R                       |
      | wiped         | wiped         | 9;_            | 9;9                  | R;R                       |

      | wiped         | geheNachLinks | _;_            | _;_                  | L;L                       |
      | geheNachLinks | geheNachLinks | ~;*            | ~;*                  | L;L                       |

      | geheNachLinks | Ze            | _;_            | _;_                  | R;R                       |

    And die TM mit dem Namen CopyNumbers hat den Endzustand Ze
    Then die TM mit dem Namen CopyNumbers hat bei folgender Eingabe die folgende Ausgabe auf Band 2:
    # Testfälle ohne Übertrag
      | eingabe                               | ausgabe   |
      | _;_                                   |           |
      | 1;_                                   | 1         |
      | 2;_                                   | 2         |
      | 3;_                                   | 3         |
      | 4;_                                   | 4         |
      | 5;_                                   | 5         |
      | 6;_                                   | 6         |
      | 7;_                                   | 7         |
      | 8;_                                   | 8         |
      | 9;_                                   | 9         |
      | 10;_                                  | 10        |
      | 123456789;_                           | 123456789 |
      | 7;123456789123456789123456789         | 7         |
      | 436801243;123456789                   | 436801243 |
      | 436801243;123456789123456789123456789 | 436801243 |
      | 436801243;23                          | 436801243 |
#    And persistiere die TM CopyNumbers to copyTM
