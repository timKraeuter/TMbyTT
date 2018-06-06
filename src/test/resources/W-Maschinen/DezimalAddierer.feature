Feature: Turing-Maschine als Dezimal-Addierer.

  Scenario: Dezimal addieren mit einer TM.
    Given eine TM mit dem Namen DezimalAddierer und 3 Bändern
    And die TM mit dem Namen DezimalAddierer hat den Startzustand z0
    And die TM mit dem Namen DezimalAddierer hat die Überführungsfunktion:
      | vonZustand    | zuZustand     | eingabeZeichen | zuSchreibendeZeichen | schreibLesekopfBewegungen |
      | z0            | z0            | ~;~;*          | ~;~;_                | R;R;N                     |
      | z0            | z0            | ~;_;*          | ~;_;_                | R;N;N                     |
      | z0            | z0            | _;~;*          | _;~;_                | N;R;N                     |

      | z0            | keinUebertrag | _;_;*          | _;_;_                | L;L;N                     |
    # Nun steht man auf den ersten beiden Rechten zeichen
    # Alle Kombinationen ohne Übertrag
      | keinUebertrag | keinUebertrag | _;1;_          | _;1;1                | L;L;L                     |
      | keinUebertrag | keinUebertrag | _;2;_          | _;2;2                | L;L;L                     |
      | keinUebertrag | keinUebertrag | _;3;_          | _;3;3                | L;L;L                     |
      | keinUebertrag | keinUebertrag | _;4;_          | _;4;4                | L;L;L                     |
      | keinUebertrag | keinUebertrag | _;5;_          | _;5;5                | L;L;L                     |
      | keinUebertrag | keinUebertrag | _;6;_          | _;6;6                | L;L;L                     |
      | keinUebertrag | keinUebertrag | _;7;_          | _;7;7                | L;L;L                     |
      | keinUebertrag | keinUebertrag | _;8;_          | _;8;8                | L;L;L                     |
      | keinUebertrag | keinUebertrag | _;9;_          | _;9;9                | L;L;L                     |
      | keinUebertrag | keinUebertrag | _;0;_          | _;0;0                | L;L;L                     |

      | keinUebertrag | keinUebertrag | 0;1;_          | 0;1;1                | L;L;L                     |
      | keinUebertrag | keinUebertrag | 0;2;_          | 0;2;2                | L;L;L                     |
      | keinUebertrag | keinUebertrag | 0;3;_          | 0;3;3                | L;L;L                     |
      | keinUebertrag | keinUebertrag | 0;4;_          | 0;4;4                | L;L;L                     |
      | keinUebertrag | keinUebertrag | 0;5;_          | 0;5;5                | L;L;L                     |
      | keinUebertrag | keinUebertrag | 0;6;_          | 0;6;6                | L;L;L                     |
      | keinUebertrag | keinUebertrag | 0;7;_          | 0;7;7                | L;L;L                     |
      | keinUebertrag | keinUebertrag | 0;8;_          | 0;8;8                | L;L;L                     |
      | keinUebertrag | keinUebertrag | 0;9;_          | 0;9;9                | L;L;L                     |
      | keinUebertrag | keinUebertrag | 0;0;_          | 0;0;0                | L;L;L                     |
      | keinUebertrag | keinUebertrag | 0;_;_          | 0;_;0                | L;L;L                     |

      | keinUebertrag | keinUebertrag | 1;1;_          | 1;1;2                | L;L;L                     |
      | keinUebertrag | keinUebertrag | 1;2;_          | 1;2;3                | L;L;L                     |
      | keinUebertrag | keinUebertrag | 1;3;_          | 1;3;4                | L;L;L                     |
      | keinUebertrag | keinUebertrag | 1;4;_          | 1;4;5                | L;L;L                     |
      | keinUebertrag | keinUebertrag | 1;5;_          | 1;5;6                | L;L;L                     |
      | keinUebertrag | keinUebertrag | 1;6;_          | 1;6;7                | L;L;L                     |
      | keinUebertrag | keinUebertrag | 1;7;_          | 1;7;8                | L;L;L                     |
      | keinUebertrag | keinUebertrag | 1;8;_          | 1;8;9                | L;L;L                     |
      | keinUebertrag | uebertrag     | 1;9;_          | 1;9;0                | L;L;L                     |
      | keinUebertrag | keinUebertrag | 1;0;_          | 1;0;1                | L;L;L                     |
      | keinUebertrag | keinUebertrag | 1;_;_          | 1;_;1                | L;L;L                     |

      | keinUebertrag | keinUebertrag | 2;1;_          | 2;1;3                | L;L;L                     |
      | keinUebertrag | keinUebertrag | 2;2;_          | 2;2;4                | L;L;L                     |
      | keinUebertrag | keinUebertrag | 2;3;_          | 2;3;5                | L;L;L                     |
      | keinUebertrag | keinUebertrag | 2;4;_          | 2;4;6                | L;L;L                     |
      | keinUebertrag | keinUebertrag | 2;5;_          | 2;5;7                | L;L;L                     |
      | keinUebertrag | keinUebertrag | 2;6;_          | 2;6;8                | L;L;L                     |
      | keinUebertrag | keinUebertrag | 2;7;_          | 2;7;9                | L;L;L                     |
      | keinUebertrag | uebertrag     | 2;8;_          | 2;8;0                | L;L;L                     |
      | keinUebertrag | uebertrag     | 2;9;_          | 2;9;1                | L;L;L                     |
      | keinUebertrag | uebertrag     | 2;0;_          | 2;0;2                | L;L;L                     |
      | keinUebertrag | keinUebertrag | 2;_;_          | 2;_;2                | L;L;L                     |

      | keinUebertrag | keinUebertrag | 3;1;_          | 3;1;4                | L;L;L                     |
      | keinUebertrag | keinUebertrag | 3;2;_          | 3;2;5                | L;L;L                     |
      | keinUebertrag | keinUebertrag | 3;3;_          | 3;3;6                | L;L;L                     |
      | keinUebertrag | keinUebertrag | 3;4;_          | 3;4;7                | L;L;L                     |
      | keinUebertrag | keinUebertrag | 3;5;_          | 3;5;8                | L;L;L                     |
      | keinUebertrag | keinUebertrag | 3;6;_          | 3;6;9                | L;L;L                     |
      | keinUebertrag | uebertrag     | 3;7;_          | 3;7;0                | L;L;L                     |
      | keinUebertrag | uebertrag     | 3;8;_          | 3;8;1                | L;L;L                     |
      | keinUebertrag | uebertrag     | 3;9;_          | 3;9;2                | L;L;L                     |
      | keinUebertrag | keinUebertrag | 3;0;_          | 3;0;3                | L;L;L                     |
      | keinUebertrag | keinUebertrag | 3;_;_          | 3;_;3                | L;L;L                     |

      | keinUebertrag | keinUebertrag | 4;1;_          | 4;1;5                | L;L;L                     |
      | keinUebertrag | keinUebertrag | 4;2;_          | 4;2;6                | L;L;L                     |
      | keinUebertrag | keinUebertrag | 4;3;_          | 4;3;7                | L;L;L                     |
      | keinUebertrag | keinUebertrag | 4;4;_          | 4;4;8                | L;L;L                     |
      | keinUebertrag | keinUebertrag | 4;5;_          | 4;5;9                | L;L;L                     |
      | keinUebertrag | uebertrag     | 4;6;_          | 4;6;0                | L;L;L                     |
      | keinUebertrag | uebertrag     | 4;7;_          | 4;7;1                | L;L;L                     |
      | keinUebertrag | uebertrag     | 4;8;_          | 4;8;2                | L;L;L                     |
      | keinUebertrag | uebertrag     | 4;9;_          | 4;9;3                | L;L;L                     |
      | keinUebertrag | keinUebertrag | 4;0;_          | 4;0;4                | L;L;L                     |
      | keinUebertrag | keinUebertrag | 4;_;_          | 4;_;4                | L;L;L                     |

      | keinUebertrag | keinUebertrag | 5;1;_          | 5;1;6                | L;L;L                     |
      | keinUebertrag | keinUebertrag | 5;2;_          | 5;2;7                | L;L;L                     |
      | keinUebertrag | keinUebertrag | 5;3;_          | 5;3;8                | L;L;L                     |
      | keinUebertrag | keinUebertrag | 5;4;_          | 5;4;9                | L;L;L                     |
      | keinUebertrag | uebertrag     | 5;5;_          | 5;5;0                | L;L;L                     |
      | keinUebertrag | uebertrag     | 5;6;_          | 5;6;1                | L;L;L                     |
      | keinUebertrag | uebertrag     | 5;7;_          | 5;7;2                | L;L;L                     |
      | keinUebertrag | uebertrag     | 5;8;_          | 5;8;3                | L;L;L                     |
      | keinUebertrag | uebertrag     | 5;9;_          | 5;9;4                | L;L;L                     |
      | keinUebertrag | keinUebertrag | 5;0;_          | 5;0;5                | L;L;L                     |
      | keinUebertrag | keinUebertrag | 5;_;_          | 5;_;5                | L;L;L                     |

      | keinUebertrag | keinUebertrag | 6;1;_          | 6;1;7                | L;L;L                     |
      | keinUebertrag | keinUebertrag | 6;2;_          | 6;2;8                | L;L;L                     |
      | keinUebertrag | keinUebertrag | 6;3;_          | 6;3;9                | L;L;L                     |
      | keinUebertrag | uebertrag     | 6;4;_          | 6;4;0                | L;L;L                     |
      | keinUebertrag | uebertrag     | 6;5;_          | 6;5;1                | L;L;L                     |
      | keinUebertrag | uebertrag     | 6;6;_          | 6;6;2                | L;L;L                     |
      | keinUebertrag | uebertrag     | 6;7;_          | 6;7;3                | L;L;L                     |
      | keinUebertrag | uebertrag     | 6;8;_          | 6;8;4                | L;L;L                     |
      | keinUebertrag | uebertrag     | 6;9;_          | 6;9;5                | L;L;L                     |
      | keinUebertrag | keinUebertrag | 6;0;_          | 6;0;6                | L;L;L                     |
      | keinUebertrag | keinUebertrag | 6;_;_          | 6;_;6                | L;L;L                     |

      | keinUebertrag | keinUebertrag | 7;1;_          | 7;1;8                | L;L;L                     |
      | keinUebertrag | keinUebertrag | 7;2;_          | 7;2;9                | L;L;L                     |
      | keinUebertrag | uebertrag     | 7;3;_          | 7;3;0                | L;L;L                     |
      | keinUebertrag | uebertrag     | 7;4;_          | 7;4;1                | L;L;L                     |
      | keinUebertrag | uebertrag     | 7;5;_          | 7;5;2                | L;L;L                     |
      | keinUebertrag | uebertrag     | 7;6;_          | 7;6;3                | L;L;L                     |
      | keinUebertrag | uebertrag     | 7;7;_          | 7;7;4                | L;L;L                     |
      | keinUebertrag | uebertrag     | 7;8;_          | 7;8;5                | L;L;L                     |
      | keinUebertrag | uebertrag     | 7;9;_          | 7;9;6                | L;L;L                     |
      | keinUebertrag | keinUebertrag | 7;0;_          | 7;0;7                | L;L;L                     |
      | keinUebertrag | keinUebertrag | 7;_;_          | 7;_;7                | L;L;L                     |

      | keinUebertrag | keinUebertrag | 8;1;_          | 8;1;9                | L;L;L                     |
      | keinUebertrag | uebertrag     | 8;2;_          | 8;2;0                | L;L;L                     |
      | keinUebertrag | uebertrag     | 8;3;_          | 8;3;1                | L;L;L                     |
      | keinUebertrag | uebertrag     | 8;4;_          | 8;4;2                | L;L;L                     |
      | keinUebertrag | uebertrag     | 8;5;_          | 8;5;3                | L;L;L                     |
      | keinUebertrag | uebertrag     | 8;6;_          | 8;6;4                | L;L;L                     |
      | keinUebertrag | uebertrag     | 8;7;_          | 8;7;5                | L;L;L                     |
      | keinUebertrag | uebertrag     | 8;8;_          | 8;8;6                | L;L;L                     |
      | keinUebertrag | uebertrag     | 8;9;_          | 8;9;7                | L;L;L                     |
      | keinUebertrag | keinUebertrag | 8;0;_          | 8;0;8                | L;L;L                     |
      | keinUebertrag | keinUebertrag | 8;_;_          | 8;_;8                | L;L;L                     |

      | keinUebertrag | uebertrag     | 9;1;_          | 9;1;0                | L;L;L                     |
      | keinUebertrag | uebertrag     | 9;2;_          | 9;2;1                | L;L;L                     |
      | keinUebertrag | uebertrag     | 9;3;_          | 9;3;2                | L;L;L                     |
      | keinUebertrag | uebertrag     | 9;4;_          | 9;4;3                | L;L;L                     |
      | keinUebertrag | uebertrag     | 9;5;_          | 9;5;4                | L;L;L                     |
      | keinUebertrag | uebertrag     | 9;6;_          | 9;6;5                | L;L;L                     |
      | keinUebertrag | uebertrag     | 9;7;_          | 9;7;6                | L;L;L                     |
      | keinUebertrag | uebertrag     | 9;8;_          | 9;8;7                | L;L;L                     |
      | keinUebertrag | uebertrag     | 9;9;_          | 9;9;8                | L;L;L                     |
      | keinUebertrag | keinUebertrag | 9;0;_          | 9;0;9                | L;L;L                     |
      | keinUebertrag | keinUebertrag | 9;_;_          | 9;_;9                | L;L;L                     |
      # Alle Kombinationen bei Übertrag
      | uebertrag     | keinUebertrag | _;1;_          | 0;1;2                | L;L;L                     |
      | uebertrag     | keinUebertrag | _;2;_          | 0;2;3                | L;L;L                     |
      | uebertrag     | keinUebertrag | _;3;_          | 0;3;4                | L;L;L                     |
      | uebertrag     | keinUebertrag | _;4;_          | 0;4;5                | L;L;L                     |
      | uebertrag     | keinUebertrag | _;5;_          | 0;5;6                | L;L;L                     |
      | uebertrag     | keinUebertrag | _;6;_          | 0;6;7                | L;L;L                     |
      | uebertrag     | keinUebertrag | _;7;_          | 0;7;8                | L;L;L                     |
      | uebertrag     | keinUebertrag | _;8;_          | 0;8;9                | L;L;L                     |
      | uebertrag     | uebertrag     | _;9;_          | 0;9;0                | L;L;L                     |
      | uebertrag     | keinUebertrag | _;0;_          | _;0;1                | L;L;L                     |

      | uebertrag     | keinUebertrag | 0;1;_          | 0;1;2                | L;L;L                     |
      | uebertrag     | keinUebertrag | 0;2;_          | 0;2;3                | L;L;L                     |
      | uebertrag     | keinUebertrag | 0;3;_          | 0;3;4                | L;L;L                     |
      | uebertrag     | keinUebertrag | 0;4;_          | 0;4;5                | L;L;L                     |
      | uebertrag     | keinUebertrag | 0;5;_          | 0;5;6                | L;L;L                     |
      | uebertrag     | keinUebertrag | 0;6;_          | 0;6;7                | L;L;L                     |
      | uebertrag     | keinUebertrag | 0;7;_          | 0;7;8                | L;L;L                     |
      | uebertrag     | keinUebertrag | 0;8;_          | 0;8;9                | L;L;L                     |
      | uebertrag     | uebertrag     | 0;9;_          | 0;9;0                | L;L;L                     |
      | uebertrag     | keinUebertrag | 0;0;_          | 0;0;1                | L;L;L                     |
      | uebertrag     | keinUebertrag | 0;_;_          | 0;_;1                | L;L;L                     |

      | uebertrag     | keinUebertrag | 1;1;_          | 1;1;3                | L;L;L                     |
      | uebertrag     | keinUebertrag | 1;2;_          | 1;2;4                | L;L;L                     |
      | uebertrag     | keinUebertrag | 1;3;_          | 1;3;5                | L;L;L                     |
      | uebertrag     | keinUebertrag | 1;4;_          | 1;4;6                | L;L;L                     |
      | uebertrag     | keinUebertrag | 1;5;_          | 1;5;7                | L;L;L                     |
      | uebertrag     | keinUebertrag | 1;6;_          | 1;6;8                | L;L;L                     |
      | uebertrag     | keinUebertrag | 1;7;_          | 1;7;9                | L;L;L                     |
      | uebertrag     | uebertrag     | 1;8;_          | 1;8;0                | L;L;L                     |
      | uebertrag     | uebertrag     | 1;9;_          | 1;9;1                | L;L;L                     |
      | uebertrag     | keinUebertrag | 1;0;_          | 1;0;2                | L;L;L                     |
      | uebertrag     | keinUebertrag | 1;_;_          | 1;_;2                | L;L;L                     |

      | uebertrag     | keinUebertrag | 2;1;_          | 2;1;4                | L;L;L                     |
      | uebertrag     | keinUebertrag | 2;2;_          | 2;2;5                | L;L;L                     |
      | uebertrag     | keinUebertrag | 2;3;_          | 2;3;6                | L;L;L                     |
      | uebertrag     | keinUebertrag | 2;4;_          | 2;4;7                | L;L;L                     |
      | uebertrag     | keinUebertrag | 2;5;_          | 2;5;8                | L;L;L                     |
      | uebertrag     | keinUebertrag | 2;6;_          | 2;6;9                | L;L;L                     |
      | uebertrag     | uebertrag     | 2;7;_          | 2;7;0                | L;L;L                     |
      | uebertrag     | uebertrag     | 2;8;_          | 2;8;1                | L;L;L                     |
      | uebertrag     | uebertrag     | 2;9;_          | 2;9;2                | L;L;L                     |
      | uebertrag     | uebertrag     | 2;0;_          | 2;0;3                | L;L;L                     |
      | uebertrag     | keinUebertrag | 2;_;_          | 2;_;3                | L;L;L                     |

      | uebertrag     | keinUebertrag | 3;1;_          | 3;1;5                | L;L;L                     |
      | uebertrag     | keinUebertrag | 3;2;_          | 3;2;6                | L;L;L                     |
      | uebertrag     | keinUebertrag | 3;3;_          | 3;3;7                | L;L;L                     |
      | uebertrag     | keinUebertrag | 3;4;_          | 3;4;8                | L;L;L                     |
      | uebertrag     | keinUebertrag | 3;5;_          | 3;5;9                | L;L;L                     |
      | uebertrag     | uebertrag     | 3;6;_          | 3;6;0                | L;L;L                     |
      | uebertrag     | uebertrag     | 3;7;_          | 3;7;1                | L;L;L                     |
      | uebertrag     | uebertrag     | 3;8;_          | 3;8;2                | L;L;L                     |
      | uebertrag     | uebertrag     | 3;9;_          | 3;9;3                | L;L;L                     |
      | uebertrag     | keinUebertrag | 3;0;_          | 3;0;4                | L;L;L                     |
      | uebertrag     | keinUebertrag | 3;_;_          | 3;_;4                | L;L;L                     |

      | uebertrag     | keinUebertrag | 4;1;_          | 4;1;6                | L;L;L                     |
      | uebertrag     | keinUebertrag | 4;2;_          | 4;2;7                | L;L;L                     |
      | uebertrag     | keinUebertrag | 4;3;_          | 4;3;8                | L;L;L                     |
      | uebertrag     | keinUebertrag | 4;4;_          | 4;4;9                | L;L;L                     |
      | uebertrag     | uebertrag     | 4;5;_          | 4;5;0                | L;L;L                     |
      | uebertrag     | uebertrag     | 4;6;_          | 4;6;1                | L;L;L                     |
      | uebertrag     | uebertrag     | 4;7;_          | 4;7;2                | L;L;L                     |
      | uebertrag     | uebertrag     | 4;8;_          | 4;8;3                | L;L;L                     |
      | uebertrag     | uebertrag     | 4;9;_          | 4;9;4                | L;L;L                     |
      | uebertrag     | keinUebertrag | 4;0;_          | 4;0;5                | L;L;L                     |
      | uebertrag     | keinUebertrag | 4;_;_          | 4;_;5                | L;L;L                     |

      | uebertrag     | keinUebertrag | 5;1;_          | 5;1;7                | L;L;L                     |
      | uebertrag     | keinUebertrag | 5;2;_          | 5;2;8                | L;L;L                     |
      | uebertrag     | keinUebertrag | 5;3;_          | 5;3;9                | L;L;L                     |
      | uebertrag     | uebertrag     | 5;4;_          | 5;4;0                | L;L;L                     |
      | uebertrag     | uebertrag     | 5;5;_          | 5;5;1                | L;L;L                     |
      | uebertrag     | uebertrag     | 5;6;_          | 5;6;2                | L;L;L                     |
      | uebertrag     | uebertrag     | 5;7;_          | 5;7;3                | L;L;L                     |
      | uebertrag     | uebertrag     | 5;8;_          | 5;8;4                | L;L;L                     |
      | uebertrag     | uebertrag     | 5;9;_          | 5;9;5                | L;L;L                     |
      | uebertrag     | keinUebertrag | 5;0;_          | 5;0;6                | L;L;L                     |
      | uebertrag     | keinUebertrag | 5;_;_          | 5;_;6                | L;L;L                     |

      | uebertrag     | keinUebertrag | 6;1;_          | 6;1;8                | L;L;L                     |
      | uebertrag     | keinUebertrag | 6;2;_          | 6;2;9                | L;L;L                     |
      | uebertrag     | uebertrag     | 6;3;_          | 6;3;0                | L;L;L                     |
      | uebertrag     | uebertrag     | 6;4;_          | 6;4;1                | L;L;L                     |
      | uebertrag     | uebertrag     | 6;5;_          | 6;5;2                | L;L;L                     |
      | uebertrag     | uebertrag     | 6;6;_          | 6;6;3                | L;L;L                     |
      | uebertrag     | uebertrag     | 6;7;_          | 6;7;4                | L;L;L                     |
      | uebertrag     | uebertrag     | 6;8;_          | 6;8;5                | L;L;L                     |
      | uebertrag     | uebertrag     | 6;9;_          | 6;9;6                | L;L;L                     |
      | uebertrag     | keinUebertrag | 6;0;_          | 6;0;7                | L;L;L                     |
      | uebertrag     | keinUebertrag | 6;_;_          | 6;_;7                | L;L;L                     |

      | uebertrag     | keinUebertrag | 7;1;_          | 7;1;9                | L;L;L                     |
      | uebertrag     | uebertrag     | 7;2;_          | 7;2;0                | L;L;L                     |
      | uebertrag     | uebertrag     | 7;3;_          | 7;3;1                | L;L;L                     |
      | uebertrag     | uebertrag     | 7;4;_          | 7;4;2                | L;L;L                     |
      | uebertrag     | uebertrag     | 7;5;_          | 7;5;3                | L;L;L                     |
      | uebertrag     | uebertrag     | 7;6;_          | 7;6;4                | L;L;L                     |
      | uebertrag     | uebertrag     | 7;7;_          | 7;7;5                | L;L;L                     |
      | uebertrag     | uebertrag     | 7;8;_          | 7;8;6                | L;L;L                     |
      | uebertrag     | uebertrag     | 7;9;_          | 7;9;7                | L;L;L                     |
      | uebertrag     | keinUebertrag | 7;0;_          | 7;0;8                | L;L;L                     |
      | uebertrag     | keinUebertrag | 7;_;_          | 7;_;8                | L;L;L                     |

      | uebertrag     | uebertrag     | 8;1;_          | 8;1;0                | L;L;L                     |
      | uebertrag     | uebertrag     | 8;2;_          | 8;2;1                | L;L;L                     |
      | uebertrag     | uebertrag     | 8;3;_          | 8;3;2                | L;L;L                     |
      | uebertrag     | uebertrag     | 8;4;_          | 8;4;3                | L;L;L                     |
      | uebertrag     | uebertrag     | 8;5;_          | 8;5;4                | L;L;L                     |
      | uebertrag     | uebertrag     | 8;6;_          | 8;6;5                | L;L;L                     |
      | uebertrag     | uebertrag     | 8;7;_          | 8;7;6                | L;L;L                     |
      | uebertrag     | uebertrag     | 8;8;_          | 8;8;7                | L;L;L                     |
      | uebertrag     | uebertrag     | 8;9;_          | 8;9;8                | L;L;L                     |
      | uebertrag     | keinUebertrag | 8;0;_          | 8;0;9                | L;L;L                     |
      | uebertrag     | keinUebertrag | 8;_;_          | 8;_;9                | L;L;L                     |

      | uebertrag     | uebertrag     | 9;1;_          | 9;1;1                | L;L;L                     |
      | uebertrag     | uebertrag     | 9;2;_          | 9;2;2                | L;L;L                     |
      | uebertrag     | uebertrag     | 9;3;_          | 9;3;3                | L;L;L                     |
      | uebertrag     | uebertrag     | 9;4;_          | 9;4;4                | L;L;L                     |
      | uebertrag     | uebertrag     | 9;5;_          | 9;5;5                | L;L;L                     |
      | uebertrag     | uebertrag     | 9;6;_          | 9;6;6                | L;L;L                     |
      | uebertrag     | uebertrag     | 9;7;_          | 9;7;7                | L;L;L                     |
      | uebertrag     | uebertrag     | 9;8;_          | 9;8;8                | L;L;L                     |
      | uebertrag     | uebertrag     | 9;9;_          | 9;9;9                | L;L;L                     |
      | uebertrag     | uebertrag     | 9;0;_          | 9;0;0                | L;L;L                     |
      | uebertrag     | uebertrag     | 9;_;_          | 9;_;0                | L;L;L                     |

    # Endzustand, wenn keine zeichen mehr da sind auf beiden Bändern
      | keinUebertrag | Ze            | _;_;_          | _;_;_                | R;R;R                     |
      | uebertrag     | Ze            | _;_;_          | _;_;1                | R;R;N                     |

    And die TM mit dem Namen DezimalAddierer hat den Endzustand Ze
    Then die TM mit dem Namen DezimalAddierer hat bei folgender Eingabe die folgende Ausgabe auf Band 3:
    # Testfälle ohne Übertrag
      | eingabe          | ausgabe |
      | 5;3;_            | 8       |
      | _;_;_            |         |
      | 0;0;_            | 0       |
      | 0;1;_            | 1       |
      | 0;2;_            | 2       |
      | 0;3;_            | 3       |
      | 0;4;_            | 4       |
      | 0;5;_            | 5       |
      | 0;6;_            | 6       |
      | 0;7;_            | 7       |
      | 0;8;_            | 8       |
      | 0;9;_            | 9       |
    # invers
      | 0;0;_            | 0       |
      | 1;0;_            | 1       |
      | 2;0;_            | 2       |
      | 3;0;_            | 3       |
      | 4;0;_            | 4       |
      | 5;0;_            | 5       |
      | 6;0;_            | 6       |
      | 7;0;_            | 7       |
      | 8;0;_            | 8       |
      | 9;0;_            | 9       |

      | _;0;_            | 0       |
      | _;1;_            | 1       |
      | _;2;_            | 2       |
      | _;3;_            | 3       |
      | _;4;_            | 4       |
      | _;5;_            | 5       |
      | _;6;_            | 6       |
      | _;7;_            | 7       |
      | _;8;_            | 8       |
      | _;9;_            | 9       |
    # invers
      | 0;_;_            | 0       |
      | 1;_;_            | 1       |
      | 2;_;_            | 2       |
      | 3;_;_            | 3       |
      | 4;_;_            | 4       |
      | 5;_;_            | 5       |
      | 6;_;_            | 6       |
      | 7;_;_            | 7       |
      | 8;_;_            | 8       |
      | 9;_;_            | 9       |

      | 0;90;_           | 90      |
      | 0;100;_          | 100     |
      | 111;111;_        | 222     |
      | 123;345;_        | 468     |
    # invers
      | 90;0;_           | 90      |
      | 100;0;_          | 100     |
      | 345;123;_        | 468     |
      | _;1564567;_      | 1564567 |
      | 1564567;_;_      | 1564567 |

    # Testfälle mit Übertrag
      | 11;19;_          | 30      |
      | 19;11;_          | 30      |
      | 156;173;_        | 329     |
      | 173;156;_        | 329     |
      | 15667;1349;_     | 17016   |
      | 1349;15667;_     | 17016   |
      | 1564567;173349;_ | 1737916 |
      | 173349;1564567;_ | 1737916 |
      | 5;3;_            | 8       |
      | 8;3;_            | 11      |
      | 11;3;_           | 14      |
      | 14;3;_           | 17      |
  And die TM DezimalAddierer erkennt die Wörter und erzeugt dabei eine Konsolenausgabe:
    |173349;1564567;_|
#    And persistiere die TM DezimalAddierer to decimalAdditionTM
