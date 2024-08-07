Проект написан на Java 17(jdk 17 eclipse temurin) для сборки используется maven
Перед использованием необходимо создать пустую базу данных в Postgres и указать логин и пароль в /src/main/resources/application.yaml
После чего mvn clean install для сборки и java -jar out/artifacts/translator_jar/translator.jar
Выходные параметры(1 - исходный язык, 2 - целевой язык перевода, 3 и далее - слова дляя перевода)
ТАкже необходимо создать в директории /src/main/resources директорию keys и поместить туда файл с ключами который можно найти по этой ссылке: https://www.dropbox.com/scl/fi/8n1evgxgkewcuzvk5onjt/authorized_key.json?rlkey=ie0psc2695a342km2jj10r5fx&st=2oye1b0x&dl=0
