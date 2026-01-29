# Dokumentacja Projektowa: Battleship Commander (Gra w Statki)

## 1. Opis Projektu

Celem projektu było stworzenie silnika gry w statki (Battleship) w architekturze konsolowej, z naciskiem na modularność, rozszerzalność i wykorzystanie sprawdzonych wzorców projektowych.

System umożliwia:

- Rozgrywkę w trybie **Gracz vs Komputer**.
- Tryb **Symulacji (Komputer vs Komputer)** pozwalający na obserwację starcia algorytmów SI.
- Wybór poziomu trudności (strategii) dla każdego z graczy.
- Śledzenie statystyk, odblokowywanie osiągnięć i budowanie rankingu zwycięzców.
- Personalizację jednostek (skórki statków).

Aplikacja została zaprojektowana tak, aby logika gry była odseparowana od interfejsu oraz od logiki decyzyjnej graczy (AI).

---

## 2. Zastosowane Wzorce Projektowe

W projekcie wykorzystano 6 kluczowych wzorców projektowych. Poniżej znajduje się ich opis wraz z uzasadnieniem użycia.

### A. Singleton (Singleton)

- **Gdzie użyty:** Klasa `GameEngine`.
- **Cel:** Zapewnienie istnienia wyłącznie jednej instancji silnika gry w całym cyklu życia aplikacji.
- **Uzasadnienie:** Silnik gry zarządza stanem globalnym rozgrywki (tura graczy, lista obserwatorów, stan "Game Over"). Istnienie wielu silników jednocześnie mogłoby prowadzić do niespójności danych (np. podwójne logowanie zdarzeń, błędy w rankingu). Singleton gwarantuje centralny punkt dostępu do konfiguracji meczu (`GameEngine.getInstance()`).

### B. Builder (Budowniczy)

- **Gdzie użyty:** Klasa `MatchBuilder`.
- **Cel:** Oddzielenie procesu konfiguracji skomplikowanego obiektu rozgrywki od jego uruchomienia.
- **Uzasadnienie:** Konfiguracja meczu wymaga ustawienia wielu parametrów: rozmiaru planszy, nazw graczy, a przede wszystkim ich strategii (kto jest botem łatwym, trudnym, a kto człowiekiem).
- Zamiast teleskopowego konstruktora: `new Game(player1, strategy1, player2, strategy2, size...)`, używamy czytelnego łańcucha wywołań:

- Pozwala to łatwo tworzyć różne warianty gry (PvE, EvE) bez zmieniania kodu silnika.

### C. Strategy (Strategia)

- **Gdzie użyty:** Interfejs `ShootingStrategy` oraz klasy `RandomStrategy`, `SmartStrategy`, `InteractiveStrategy`.
- **Cel:** Zdefiniowanie rodziny algorytmów (sposobów oddawania strzału), otoczenie ich interfejsem i uczynienie ich wymiennymi.
- **Uzasadnienie:** Jest to serce logiki "sztucznej inteligencji" w projekcie. Zamiast tworzyć klasy `HumanPlayer` i `ComputerPlayer`, klasa `Player` posiada pole `strategy`.
- Dzięki temu zmiana poziomu trudności to po prostu "wstrzyknięcie" innej strategii (np. `SmartStrategy` z logiką dobijania statków).
- Pozwala to na symulację AI vs AI poprzez przypisanie obu graczom strategii komputerowych.

### D. Observer (Obserwator)

- **Gdzie użyty:** Interfejs `Observer` oraz klasy `StatsLogger`, `AchievementSystem`, `RankingSystem`. Klasa `GameEngine` pełni rolę obiektu obserwowanego (Subject).
- **Cel:** Zdefiniowanie zależności jeden-do-wielu, gdzie zmiana stanu jednego obiektu powoduje automatyczne powiadomienie innych.
- **Uzasadnienie:** Silnik gry (`GameEngine`) powinien zajmować się tylko logiką rozgrywki (strzelanie, tury). Nie powinien wiedzieć o logach, osiągnięciach czy rankingu.
- Gdy pada strzał, silnik wysyła sygnał `notifyObservers("Trafiony")`.
- `AchievementSystem` reaguje, sprawdzając, czy gracz zdobył odznakę "Snajper".
- `RankingSystem` reaguje tylko na sygnał "Wygrana", aktualizując tabelę wyników.
- Umożliwia to dodawanie nowych funkcji (np. dźwięków) bez modyfikacji kodu silnika (Open/Closed Principle).

### E. Factory Method (Metoda Wytwórcza)

- **Gdzie użyty:** Klasa `ShipFactory`.
- **Cel:** Zdefiniowanie interfejsu do tworzenia obiektów, pozwalając podklasom lub metodom statycznym decydować o typie tworzonej instancji.
- **Uzasadnienie:** Centralizuje proces tworzenia statków. Klient (silnik gry) prosi o "niszczyciel" (`createShip("destroyer")`), nie martwiąc się o szczegóły implementacji (ile ma masztów, jakie punkty życia). Ułatwia to dodawanie nowych typów jednostek w przyszłości.

### F. Decorator (Dekorator)

- **Gdzie użyty:** Klasa abstrakcyjna `ShipDecorator` oraz `GoldenSkin`.
- **Cel:** Dynamiczne przydzielanie nowych obowiązków (cech) obiektom.
- **Uzasadnienie:** Pozwala na modyfikację wyglądu lub zachowania statków (np. "Złoty Niszczyciel") bez tworzenia osobnych klas dziedziczących dla każdej kombinacji (unikamy `GoldenDestroyer`, `GoldenBattleship`). Dekorator opakowuje istniejący statek, zmieniając jego nazwę (`getDescription`), ale zachowując logikę gry (`hit`, `isSunk`).

---

## 3. Architektura i Modularność

Projekt został podzielony na logiczne warstwy, co spełnia wymagania dotyczące czystego kodu:

1. **Warstwa Modelu (Dane):**

- `Board`, `Cell`, `Ship` - przechowują stan gry. Nie wiedzą nic o logice sterowania ani o interfejsie.

2. **Warstwa Logiki Biznesowej (Core):**

- `GameEngine` - zarządza przepływem gry.
- `Player` - łączy dane (planszę) z zachowaniem (strategią).

3. **Warstwa Algorytmów (AI):**

- Implementacje `ShootingStrategy` - całkowicie odseparowane algorytmy decyzyjne.

4. **Warstwa Prezentacji i Dodatków:**

- `Observer` - moduły "dookoła" gry (statystyki, rankingi).
- System wyświetlania w konsoli (metody `printSideBySide`) jest odseparowany od logiki gry.

---

## 4. Możliwości Rozwoju (Extensibility)

Dzięki zastosowanym wzorcom, projekt jest otwarty na rozbudowę:

- **Dodanie trybu sieciowego:** Wystarczy napisać nową klasę `NetworkStrategy`, która zamiast losować liczby, pobiera koordynaty z serwera. Nie trzeba zmieniać klasy `Player`.
- **Dodanie GUI (Okienka):** Klasa `GameEngine` jest niezależna od konsoli `System.out`. Można podpiąć widok JavaFX/Swing, który będzie nasłuchiwał zmian jako `Observer`.
- **Nowe Statki:** Dodanie klasy `Submarine` i aktualizacja `ShipFactory` nie wpłynie na resztę systemu.
- **Zapisywanie gry (Save/Load):** Można wykorzystać wzorzec **Memento** (łatwe do dodania dzięki hermetyzacji stanu w `Board`).

---
