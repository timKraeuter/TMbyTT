Feature: Verschiedene Turingmaschinen werden getestet.

  Scenario: Nicht deterministische TM kann die Zeichen ab und ac erkennen.
    Given eine TM mit dem Namen ABAC und 1 Bändern
    And die TM mit dem Namen ABAC hat den Startzustand z0
    And die TM mit dem Namen ABAC hat die Überführungsfunktion:
      | vonZustand | zuZustand | eingabeZeichen | zuSchreibendeZeichen | schreibLesekopfBewegungen |
      | z0         | z1        | a              | ♥                    | R                         |
      | z0         | z2        | a              | ♥                    | R                         |
      | z1         | z4        | b              | ♥                    | R                         |
      | z2         | z4        | c              | ♥                    | R                         |
    And die TM mit dem Namen ABAC hat den Endzustand z4
    Then die TM ABAC erkennt die Wörter:
      | ab |
      | ac |
    And die TM ABAC erkennt nicht die Wörter:
      | a |
      | b |
      | c |
