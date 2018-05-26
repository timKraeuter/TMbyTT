Feature: Verschiedene Turingmaschinen werden getestet.

  Scenario: MehrbandTuringmaschine erkennt die Sprache 0^n1^n2^n.
    Given eine TM mit dem Namen 0^n1^n2^n und 1 Bändern
    And die TM mit dem Namen 0^n1^n2^n hat den Startzustand z0
    And die TM mit dem Namen 0^n1^n2^n hat die Überführungsfunktion:
      | vonZustand | zuZustand | eingabeZeichen | zuSchreibendeZeichen | schreibLesekopfBewegungen |
      | z0         | ok        | _              | _                    | N                         |
      | z0         | z1        | 0              | X                    | R                         |
      | z1         | z1        | 0              | 0                    | R                         |
      | z1         | z1        | 1              | 1                    | R                         |
      | z1         | z1        | 2              | 2                    | R                         |
      | z1         | z2        | X              | X                    | L                         |
      | z1         | z2        | _              | _                    | L                         |
      | z2         | z3        | 2              | X                    | L                         |
      | z3         | z6        | 1              | X                    | L                         |
      | z6         | ok        | X              | X                    | N                         |
      | z3         | z4        | 2              | X                    | L                         |
      | z4         | z5        | 1              | 2                    | L                         |
      | z4         | z4        | 2              | 2                    | L                         |
      | z5         | z5        | 0              | 0                    | L                         |
      | z5         | z5        | 1              | 1                    | L                         |
      | z5         | z5        | 2              | 2                    | L                         |
      | z5         | z0        | X              | X                    | R                         |
    And die TM mit dem Namen 0^n1^n2^n hat den Endzustand ok
    Then die TM 0^n1^n2^n erkennt die Wörter:
      | 012          |
      | 001122       |
      | 000111222    |
      | 000011112222 |
    And die TM 0^n1^n2^n erkennt nicht die Wörter:
      | 0     |
      | 1     |
      | 2     |

      | 01    |
      | 12    |

      | 00112 |
      | 01122 |

      | 0112  |
      | 0012  |
      | 0122  |

