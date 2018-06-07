Feature: Turing-Maschine zum Prüfen auf 0.

  Scenario: TM prüft das Eingabeband auf 0.
    Given eine TM mit dem Namen Pruefe0 und 2 Bändern
    And die TM mit dem Namen Pruefe0 hat den Startzustand z0
    And die TM mit dem Namen Pruefe0 hat die Überführungsfunktion:
      | vonZustand     | zuZustand      | eingabeZeichen | zuSchreibendeZeichen | schreibLesekopfBewegungen |
      | z0             | z0             | 0;*            | 0;_                  | R;N                       |
      | z0             | geheNachLinks  | _;*            | _;0                  | L;N                       |
      | z0             | geheNachRechts | 1;*            | 1;1                  | R;N                       |
      | z0             | geheNachRechts | 2;*            | 2;1                  | R;N                       |
      | z0             | geheNachRechts | 3;*            | 3;1                  | R;N                       |
      | z0             | geheNachRechts | 4;*            | 4;1                  | R;N                       |
      | z0             | geheNachRechts | 5;*            | 5;1                  | R;N                       |
      | z0             | geheNachRechts | 6;*            | 6;1                  | R;N                       |
      | z0             | geheNachRechts | 7;*            | 7;1                  | R;N                       |
      | z0             | geheNachRechts | 8;*            | 8;1                  | R;N                       |
      | z0             | geheNachRechts | 9;*            | 9;1                  | R;N                       |

      | geheNachRechts | geheNachRechts | ~;*            | ~;*                  | R;N                       |
      | geheNachRechts | geheNachLinks  | _;*            | _;*                  | L;N                       |

      | geheNachLinks  | geheNachLinks  | ~;*            | ~;*                  | L;N                       |
      | geheNachLinks  | Ze             | _;*            | _;*                  | R;N                       |


    And die TM mit dem Namen Pruefe0 hat den Endzustand Ze
    Then die TM mit dem Namen Pruefe0 hat bei folgender Eingabe die folgende Ausgabe auf Band 2:
    # 1 == false
    # 0 == true
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
#    And persistiere die TM Pruefe0 to pruefe0Maschine
