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
#### 5. [Frontend](zad5)
- [x] **3.0** W ramach projektu należy stworzyć dwa komponenty: Produkty oraz Płatności; Płatności powinny wysyłać do aplikacji serwerowej dane, a w Produktach powinniśmy pobierać dane o produktach z aplikacji serwerowej; - [commit](https://github.com/barankonrad/eBiznes/commit/d09997047570b085e5246a46395f1d4f340b544b)
- [x] **3.5** Należy dodać Koszyk wraz z widokiem; należy wykorzystać routing - [commit](https://github.com/barankonrad/eBiznes/commit/843db5b984c0446101c0be42cc908aae97bc5eb5)
- [x] **4.0** Dane pomiędzy wszystkimi komponentami powinny być przesyłane za pomocą React hooks - [commit](https://github.com/barankonrad/eBiznes/commit/cf1e0d4baa3cc4b1540514d7ce848ff3ff1ba978)
- [x] **4.5** Należy dodać skrypt uruchamiający aplikację serwerową oraz kliencką na dockerze via docker-compose - [commit](https://github.com/barankonrad/eBiznes/commit/1ec1cb3f6c574c2921234b0260a758107c698d6f)
- [ ] **5.0** Należy wykorzystać axios’a oraz dodać nagłówki pod CORS
#### 6. [Testy](zad6)
- [x] **3.0** Należy stworzyć 20 przypadków testowych w CypressJS lub Selenium (Kotlin, Python, Java, JS, Go, Scala) - [commit](https://github.com/barankonrad/eBiznes/commit/135c80069d1fbd4bf3bc69907d09af5d640dfec9)
- [x] **3.5** Należy rozszerzyć testy funkcjonalne, aby zawierały minimum 50 asercji - [commit](https://github.com/barankonrad/eBiznes/commit/e566ac67058d1a02eb30ddbf355591f05c4d4340)
- [x] **4.0** Należy stworzyć testy jednostkowe do wybranego wcześniejszego projektu z minimum 50 asercjami - [commit](https://github.com/barankonrad/eBiznes/commit/cafaa3d2bd5050d501284ece8ebdedcd72be622b)
- [x] **4.5** Należy dodać testy API, należy pokryć wszystkie endpointy z minimum jednym scenariuszem negatywnym per endpoint - [commit](https://github.com/barankonrad/eBiznes/commit/f6b3a86275bb153f0bd1a10d070394f030f39aea)
- [ ] **5.0** Należy uruchomić testy funkcjonalne na Browserstacku
#### 7. [Sonar](zad7)
 - [x] **3.0** Należy dodać litera do odpowiedniego kodu aplikacji serwerowej w hookach gita - [commit](https://github.com/barankonrad/eBiznesZad7/commit/e8c6b3d424fdcef18d4ac716a493a207bca4733e)
 - [x] **3.5** Należy wyeliminować wszystkie bugi w kodzie w Sonarze (kod aplikacji serwerowej) - [commit](https://github.com/barankonrad/eBiznesZad7/commit/822c0b05376a01a430db11abe504c981432e9a64)
 - [x] **4.0** Należy wyeliminować wszystkie zapaszki w kodzie w Sonarze (kod aplikacji serwerowej) - [commit](https://github.com/barankonrad/eBiznesZad7/commit/822c0b05376a01a430db11abe504c981432e9a64)
 - [x] **4.5** Należy wyeliminować wszystkie podatności oraz błędy bezpieczeństwa w kodzie w Sonarze (kod aplikacji serwerowej) - [commit](https://github.com/barankonrad/eBiznesZad7/commit/372ce593f58bbe71db29507483f24af7b8425ecb)
 - [ ] **5.0** Należy wyeliminować wszystkie błędy oraz zapaszki w kodzie aplikacji klienckiej
