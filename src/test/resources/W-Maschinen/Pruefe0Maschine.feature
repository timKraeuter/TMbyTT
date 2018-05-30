Feature: Verschiedene Turingmaschinen werden getestet.

  Scenario: TM kopiert das Eingabeband auf das Ausgabeband.
    Given eine TM mit dem Namen Pruefe0 und 2 Bändern
    And die TM mit dem Namen Pruefe0 hat den Startzustand z0
    And die TM mit dem Namen Pruefe0 hat die Überführungsfunktion:
      | vonZustand    | zuZustand     | eingabeZeichen | zuSchreibendeZeichen | schreibLesekopfBewegungen |
      | z0            | z0            | 0;*            | 0;_                  | R;N                       |
      | z0            | geheNachLinks | _;*            | _;0                  | R;N                       |
      | z0            | geheNachLinks | 1;*            | 1;1                  | R;N                       |
      | z0            | geheNachLinks | 2;*            | 2;1                  | R;N                       |
      | z0            | geheNachLinks | 3;*            | 3;1                  | R;N                       |
      | z0            | geheNachLinks | 4;*            | 4;1                  | R;N                       |
      | z0            | geheNachLinks | 5;*            | 5;1                  | R;N                       |
      | z0            | geheNachLinks | 6;*            | 6;1                  | R;N                       |
      | z0            | geheNachLinks | 7;*            | 7;1                  | R;N                       |
      | z0            | geheNachLinks | 8;*            | 8;1                  | R;N                       |
      | z0            | geheNachLinks | 9;*            | 9;1                  | R;N                       |

      | geheNachLinks | geheNachLinks | ~;*            | ~;*                  | L;N                       |
      | geheNachLinks | Ze            | _;*            | _;*                  | R;N                       |


    And die TM mit dem Namen Pruefe0 hat den Endzustand Ze
    Then die TM mit dem Namen Pruefe0 hat bei folgender Eingabe die folgende Ausgabe auf Band 2:
    # Testfälle ohne Übertrag
      | eingabe      | ausgabe |
      | 0;_          | 0       |
      | 00;_         | 0       |
      | 000;_        | 0       |
      | 000;_        | 0       |
      | 000000;_     | 0       |
      | 00000000;_   | 0       |
      | 000000000;_  | 0       |
      | 0000000000;_ | 0       |
      | _;_          | 0       |
      | 1;_          | 1       |
      | 2;_          | 1       |
      | 3;_          | 1       |
      | 4;_          | 1       |
      | 5;_          | 1       |
      | 6;_          | 1       |
      | 7;_          | 1       |
      | 8;_          | 1       |
      | 9;_          | 1       |
      | 10;_         | 1       |
      | 123456789;_  | 1       |
      | 436801243;_  | 1       |
