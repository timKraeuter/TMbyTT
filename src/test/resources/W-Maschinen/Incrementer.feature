Feature: Verschiedene Turingmaschinen werden getestet.

  Scenario: TM kopiert das Eingabeband auf das Ausgabeband.
    Given eine TM mit dem Namen Increment und 1 Bändern
    And die TM mit dem Namen Increment hat den Startzustand nachR
    And die TM mit dem Namen Increment hat die Überführungsfunktion:
      | vonZustand | zuZustand | eingabeZeichen | zuSchreibendeZeichen | schreibLesekopfBewegungen |

      | nachR      | nachR     | ~              | ~                    | R                         |
      | nachR      | add       | _              | _                    | L                         |

      | add        | add       | 9              | 0                    | L                         |

      | add        | nachL     | _              | 1                    | L                         |
      | add        | nachL     | 0              | 1                    | L                         |
      | add        | nachL     | 1              | 2                    | L                         |
      | add        | nachL     | 2              | 3                    | L                         |
      | add        | nachL     | 3              | 4                    | L                         |
      | add        | nachL     | 4              | 5                    | L                         |
      | add        | nachL     | 5              | 6                    | L                         |
      | add        | nachL     | 6              | 7                    | L                         |
      | add        | nachL     | 7              | 8                    | L                         |
      | add        | nachL     | 8              | 9                    | L                         |

      | nachL      | nachL     | ~              | ~                    | L                         |
      | nachL      | fertig    | _              | _                    | R                         |
    And die TM mit dem Namen Increment hat den Endzustand fertig
    Then die TM mit dem Namen Increment hat bei folgender Eingabe die folgende Ausgabe auf Band 1:
      | eingabe  | ausgabe   |
      | 0        | 1         |
      | 1        | 2         |
      | 2        | 3         |
      | 3        | 4         |
      | 4        | 5         |
      | 5        | 6         |
      | 6        | 7         |
      | 7        | 8         |
      | 8        | 9         |
      | 9        | 10        |
      | 10       | 11        |
      | 99       | 100       |
      | 99999999 | 100000000 |
      | 99989999 | 99990000  |
      | 99979999 | 99980000  |
