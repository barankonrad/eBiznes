# E-Biznes
Zadania z kursu E-Biznes na Informatyce Stosowanej, UJ WFAIS 2024/2025

## Zadania
#### 1. [Docker](zad1)
- [x] **3.0** obraz ubuntu z Pythonem w wersji 3.10 - [commit](https://github.com/barankonrad/eBiznes/commit/08666ab02340e04e61bc625442f25190fc6ae1e9)
- [x] **3.5** obraz ubuntu:24.02 z Javą w wersji 8 oraz Kotlinem - [commit](https://github.com/barankonrad/eBiznes/commit/08666ab02340e04e61bc625442f25190fc6ae1e9)
- [x] **4.0** do powyższego należy dodać najnowszego Gradle’a oraz paczkę JDBC SQLite w ramach projektu na Gradle (build.gradle) - [commit](https://github.com/barankonrad/eBiznes/commit/08666ab02340e04e61bc625442f25190fc6ae1e9)
- [x] **4.5** stworzyć przykład typu HelloWorld oraz uruchomienie aplikacji przez CMD oraz gradle - [commit](https://github.com/barankonrad/eBiznes/commit/08666ab02340e04e61bc625442f25190fc6ae1e9)
- [x] **5.0** dodać konfigurację docker-compose - [commit](https://github.com/barankonrad/eBiznes/commit/08666ab02340e04e61bc625442f25190fc6ae1e9)

#### 2. [Scala](zad2)
- [x] 3.0 Należy stworzyć kontroler do Produktów - [commit](https://github.com/barankonrad/eBiznes/commit/283e1398334f7ac5596ca596ad5cceefd90c173d)
- [x] 3.5 Do kontrolera należy stworzyć endpointy zgodnie z CRUD - dane pobierane z listy - [commit](https://github.com/barankonrad/eBiznes/commit/283e1398334f7ac5596ca596ad5cceefd90c173d)
- [x] 4.0 Należy stworzyć kontrolery do Kategorii oraz Koszyka + endpointy zgodnie z CRUD - [commit](https://github.com/barankonrad/eBiznes/commit/283e1398334f7ac5596ca596ad5cceefd90c173d)
- [x] 4.5 Należy aplikację uruchomić na dockerze (stworzyć obraz) oraz dodać skrypt uruchamiający aplikację via ngrok - [commit](https://github.com/barankonrad/eBiznes/commit/283e1398334f7ac5596ca596ad5cceefd90c173d)
- [ ] 5.0 Należy dodać konfigurację CORS dla dwóch hostów dla metod CRUD
#### 3. [Kotlin](zad3)
- [x] **3.0** Należy stworzyć aplikację kliencką w Kotlinie we frameworku Ktor, która pozwala na przesyłanie wiadomości na platformę Discord - [commit](https://github.com/barankonrad/eBiznes/commit/5ddeb0e96cd5a47d643e1cfaf7ba59e8952b758f)
- [x] **3.5** Aplikacja jest w stanie odbierać wiadomości użytkowników z platformy Discord skierowane do aplikacji (bota) - [commit](https://github.com/barankonrad/eBiznes/commit/5a33c526939f6316148e0bd4456ce2ca8e66c174)
- [x] **4.0** Zwróci listę kategorii na określone żądanie użytkownika - [commit](https://github.com/barankonrad/eBiznes/commit/c71968a2cd58dc82a35650bdab3eaaa7bd25c4e6)
- [x] **4.5** Zwróci listę produktów wg żądanej kategorii - [commit](https://github.com/barankonrad/eBiznes/commit/f7b58919fc858d0ce69b69a769074808a8500838)
- [ ] **5.0** Aplikacja obsłuży dodatkowo jedną z platform: Slack, Messenger, Webex
#### 4. [Go](zad4)
- [x] **3.0** Należy stworzyć aplikację we frameworki echo w j. Go, która będzie miała kontrolerbProduktów zgodny z CRUD - [commit](https://github.com/barankonrad/eBiznes/commit/28017102442a93c4c2be747c9e4b654dc7095938)
- [x] **3.5** Należy stworzyć model Produktów wykorzystując gorm oraz wykorzystać model do obsługi produktów (CRUD) w kontrolerze (zamiast listy) - [commit](https://github.com/barankonrad/eBiznes/commit/28017102442a93c4c2be747c9e4b654dc7095938)
- [x] **4.0** Należy dodać model Koszyka oraz dodać odpowiedni endpoint - [commit](https://github.com/barankonrad/eBiznes/commit/64eb750c495e118c3b9bd6f866aa5b0ab4cae18f)
- [x] **4.5** Należy stworzyć model kategorii i dodać relację między kategorią, a produktem - [commit](https://github.com/barankonrad/eBiznes/commit/64fe19ca95af50b373363e719cedfac16861cf9f)
- [ ] **5.0** pogrupować zapytania w gorm’owe scope'y
