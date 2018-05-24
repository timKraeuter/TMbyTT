Feature: Verschiedene Turingmaschinen werden getestet.

  Scenario: Turingmaschine kann das Zeichen a erkennen.
    Given eine Turingmaschine mit dem Namen Testgedöns
    And die Turingmaschine mit dem Namen Testgedöns hat den Startzustand z0
    And die Turingmaschine mit dem Namen Testgedöns hat die Überführungsfunktion:
      | vonZustand | zuZustand | eingabeZeichen | zuSchreibendeZeichen | schreibLesekopfBewegungen |
      | z0         | Ze        | a              | ♥                    | N                         |
    And die Turingmaschine mit dem Namen Testgedöns hat den Endzustand Ze
    When das Wort a in die Turingmaschine mit dem Namen Testgedöns eingegeben wird
    Then wurde das Wort a erkannt
    And das Band enthält bei Eingabe von a ♥


  Scenario: Turingmaschine kann die Sprache a^xb^y erkennen. x und y element aus N >= 1.
    Given eine Turingmaschine mit dem Namen AB
    And die Turingmaschine mit dem Namen AB hat den Startzustand z0
    And die Turingmaschine mit dem Namen AB hat die Überführungsfunktion:
      | vonZustand | zuZustand | eingabeZeichen | zuSchreibendeZeichen | schreibLesekopfBewegungen |
      | z0         | z1        | a              | ♥                    | R                         |
      | z1         | z1        | a              | ♥                    | R                         |
      | z1         | z2        | b              | ♥                    | R                         |
      | z2         | z2        | b              | ♥                    | R                         |
      | z2         | z3        | _              | ♥                    | N                         |
    And die Turingmaschine mit dem Namen AB hat den Endzustand z3
    When das Wort ab in die Turingmaschine mit dem Namen AB eingegeben wird
    When das Wort aba in die Turingmaschine mit dem Namen AB eingegeben wird
    Then wurde das Wort ab erkannt
    And bei Eingabe von ab enthält das Band ♥♥
