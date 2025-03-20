# Zadanie 2 Scala

Obraz na DockerHub: [konradbaran/scala-play-app](https://hub.docker.com/repository/docker/konradbaran/scala-play-app/general)

## Wymagania:
Należy stworzyć aplikację na frameworku Play w Scali 3.
- [x] 3.0 Należy stworzyć kontroler do Produktów
- [x] 3.5 Do kontrolera należy stworzyć endpointy zgodnie z CRUD - dane pobierane z listy
- [x] 4.0 Należy stworzyć kontrolery do Kategorii oraz Koszyka + endpointy zgodnie z CRUD
- [x] 4.5 Należy aplikację uruchomić na dockerze (stworzyć obraz) oraz dodać skrypt uruchamiający aplikację via ngrok
- [ ] 5.0 Należy dodać konfigurację CORS dla dwóch hostów dla metod CRUD

Kontrolery mogą bazować na listach zamiast baz danych. CRUD: show all, show by id (get), update (put), delete (delete), add (post). 