# Zadanie rektutacyjne Volanto

Dane apki:
  - baza danych posgresql
  - nazwa bazy: rektutacja
  - user: rekrutacja
  - bez hasła
  - uruchamianie z wtyczki maven w root folderze projektu: mvn spring-boot:run
  
Wszystko dostępne w application.properties

Security: gdyby w bazie nie było userów z odpowiednimi opcjami apka tworzy:
  - jednego admina: admin, hasło: volanto@
  - jednego usera: user, hasło: user

Aplikacja startuje z portu 8081, bo pomyślałem, że domyślny port tomcata każdy ma zajęty,
  - po zalogowaniu sie apka przesyła nas na adres http://localhost:8081/swagger/index.html
gdzie dostępna jest graficzna dokumentacja API Swaggera (swagger.io).
  - Miałem kłopoty z uploadem zdjęć, teoretycznie powinien on działać, controllery są zaimplementowane (/api/contacs/{id}/file - dla dodawania zdjęć)
 - Backend nie jest wykonany w 100% - brakło mi czasu na implementacje logowania się przez Facebooka, oraz nie ma testów co każdego zdenerwuje.
