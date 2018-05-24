Feature: Verschiedene Turingmaschinen werden getestet.

  Scenario: Turingmaschine kann das Zeichen a erkennen.
    Given eine TM mit dem Namen Testgedöns
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
    Given eine TM mit dem Namen AB
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
      | ab |
      | aab |
      | aaab |
      | abb |
      | abbb |
      | aabbb |
      | aaabbb |
    And die TM AB erkennt nicht die Wörter:
      | a |
      | aa |
      | b |
      | bb |
      | irgendwas |
    And bei Eingabe von ab bei der TM AB enthält das Band ♥♥
