Feature: TM prüft das Eingabeband auf Gleichheit mit 0.

  Scenario: TM prüft das Eingabeband auf 0.
    Given eine TM mit dem Namen PruefeEqual0 und 2 Bändern
    And die TM mit dem Namen PruefeEqual0 hat den Startzustand z0
    And die TM mit dem Namen PruefeEqual0 hat die Überführungsfunktion:
      | vonZustand     | zuZustand      | eingabeZeichen | zuSchreibendeZeichen | schreibLesekopfBewegungen |
      | z0             | z0             | 0;*            | 0;_                  | R;N                       |
      | z0             | geheNachLinks  | _;*            | _;1                  | L;N                       |
      | z0             | geheNachRechts | 1;*            | 1;0                  | R;N                       |
      | z0             | geheNachRechts | 2;*            | 2;0                  | R;N                       |
      | z0             | geheNachRechts | 3;*            | 3;0                  | R;N                       |
      | z0             | geheNachRechts | 4;*            | 4;0                  | R;N                       |
      | z0             | geheNachRechts | 5;*            | 5;0                  | R;N                       |
      | z0             | geheNachRechts | 6;*            | 6;0                  | R;N                       |
      | z0             | geheNachRechts | 7;*            | 7;0                  | R;N                       |
      | z0             | geheNachRechts | 8;*            | 8;0                  | R;N                       |
      | z0             | geheNachRechts | 9;*            | 9;0                  | R;N                       |

      | geheNachRechts | geheNachRechts | ~;*            | ~;*                  | R;N                       |
      | geheNachRechts | geheNachLinks  | _;*            | _;*                  | L;N                       |

      | geheNachLinks  | geheNachLinks  | ~;*            | ~;*                  | L;N                       |
      | geheNachLinks  | Ze             | _;*            | _;*                  | R;N                       |


    And die TM mit dem Namen PruefeEqual0 hat den Endzustand Ze
    Then die TM mit dem Namen PruefeEqual0 hat bei folgender Eingabe die folgende Ausgabe auf Band 2:
    # 1 == false
    # 0 == true
      | eingabe      | ausgabe |
      | 0;_          | 1       |
      | 00;_         | 1       |
      | 000;_        | 1       |
      | 000;_        | 1       |
      | 000000;_     | 1       |
      | 00000000;_   | 1       |
      | 000000000;_  | 1       |
      | 0000000000;_ | 1       |
      | _;_          | 1       |
      | 1;_          | 0       |
      | 2;_          | 0       |
      | 3;_          | 0       |
      | 4;_          | 0       |
      | 5;_          | 0       |
      | 6;_          | 0       |
      | 7;_          | 0       |
      | 8;_          | 0       |
      | 9;_          | 0       |
      | 10;_         | 0       |
      | 123456789;_  | 0       |
      | 436801243;_  | 0       |
    And persistiere die TM PruefeEqual0 to pruefeEqual0Maschine
