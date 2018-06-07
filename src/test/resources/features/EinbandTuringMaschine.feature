Feature: Verschiedene Turingmaschinen werden getestet.

  Scenario: Turingmaschine kann das Zeichen a erkennen.
    Given eine TM mit dem Namen Testgedöns und 1 Bändern
    And die TM mit dem Namen Testgedöns hat den Startzustand z0
    And die TM mit dem Namen Testgedöns hat die Überführungsfunktion:
      | vonZustand | zuZustand | eingabeZeichen | zuSchreibendeZeichen | schreibLesekopfBewegungen |
      | z0         | Ze        | a              | ♥                    | N                         |
    And die TM mit dem Namen Testgedöns hat den Endzustand Ze
    Then die TM Testgedöns erkennt die Wörter:
      | a |
    And bei Eingabe von a bei der TM Testgedöns enthält das Band ♥
    And die TM Testgedöns erkennt nicht die Wörter:
      | b |
      | c |
      | e |

  Scenario: Turingmaschine kann die Sprache a^xb^y erkennen. x und y element aus N >= 1.
    Given eine TM mit dem Namen AB und 1 Bändern
    And die TM mit dem Namen AB hat den Startzustand z0
    And die TM mit dem Namen AB hat die Überführungsfunktion:
      | vonZustand | zuZustand | eingabeZeichen | zuSchreibendeZeichen | schreibLesekopfBewegungen |
      | z0         | z1        | a              | ♥                    | R                         |
      | z1         | z1        | a              | ♥                    | R                         |
      | z1         | z2        | b              | ♥                    | R                         |
      | z2         | z2        | b              | ♥                    | R                         |
      | z2         | z3        | _              | ♥                    | N                         |
    And die TM mit dem Namen AB hat den Endzustand z3
    Then die TM AB erkennt die Wörter:
      | ab     |
      | aab    |
      | aaab   |
      | abb    |
      | abbb   |
      | aabbb  |
      | aaabbb |
    And die TM AB erkennt nicht die Wörter:
      | a         |
      | aa        |
      | b         |
      | bb        |
      | irgendwas |
    And bei Eingabe von ab bei der TM AB enthält das Band ♥♥

  Scenario: Turingmaschine kann die Sprache 0^n1^n erkennen. n element aus N >= 1.
    Given eine TM mit dem Namen 0^n1^n und 1 Bändern
    And die TM mit dem Namen 0^n1^n hat den Startzustand Start
    And die TM mit dem Namen 0^n1^n hat die Überführungsfunktion:
      | vonZustand | zuZustand  | eingabeZeichen | zuSchreibendeZeichen | schreibLesekopfBewegungen |
      | Start      | OK         | _              | _                    | N                         |
      | Start      | SucheMitte |              0 |                    0 | N                         |
      | Start      | SucheMitte |              1 |                    1 | N                         |
      | SucheMitte | SucheMitte |              0 |                    0 | R                         |
      | SucheMitte | Lösche0    |              1 |                    1 | L                         |
      | Lösche0    | Lösche1    |              0 | X                    | R                         |
      | Lösche0    | Lösche0    | X              | X                    | L                         |
      | Lösche0    | Kontrolle  | _              | _                    | R                         |
      | Lösche1    | Lösche1    | X              | X                    | R                         |
      | Lösche1    | Lösche0    |              1 | X                    | L                         |
      | Kontrolle  | Kontrolle  | X              | X                    | R                         |
      | Kontrolle  | OK         | _              | _                    | N                         |
    And die TM mit dem Namen 0^n1^n hat den Endzustand OK
    Then die TM 0^n1^n erkennt die Wörter:
      |          |
      |       01 |
      |     0011 |
      |   000111 |
      | 00001111 |
    And die TM 0^n1^n erkennt nicht die Wörter:
      |         0 |
      |        00 |
      |         1 |
      |        11 |
      |       010 |
      |       101 |
      | irgendwas |
