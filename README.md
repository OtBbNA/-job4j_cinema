# Job4j. CinemaProject.

Проект кинотеатр реализует сайт с следующим функционалом:
- Просмотр сеансов и связнных с ними фильмов
- Просмотр списка всех фильмов
- Возможность покупки билета с выбором ряда и места
- Возможность регистрации/авторизации/выхода

Используемые инструменты:
- Java 17,
- CheckStyle 3.1.2,
- Mockito 5.2.0,
- Spring Boot 2.7.6, 
- Thymeleaf,
- Liquibase 4.28.0,
- PostgreSQL 42.5.1,
- Sql2o 1.6.0,
- h2 2.1.214,
- Jacoco 0.8.12,
- css,
- js.

Как запустить проект:
- Скачасть проект
- Создать базу данных cinema
- Через инструмент Maven пройдите по пути job4j_cinema/Plugins/liquibase и выберете функцию liquibase:update
- Пройдите по пути src/main/java/ru/job4j/cinema, найдите и запустите класс Main.java
- Передите на страницу http://localhost:8080/ в браузере

Скриншоты основных страниц сайта:

Главная страница
![Главная страница](https://github.com/OtBbNA/job4j_cinema/blob/master/imageReadme/index.png)

Список сеансов
![Список сеансов](https://github.com/OtBbNA/job4j_cinema/blob/master/imageReadme/schedule.png)

Список фильмов
![Список фильмов](https://github.com/OtBbNA/job4j_cinema/blob/master/imageReadme/list.png)

Отдельный фильм
![Отдельный фильм](https://github.com/OtBbNA/job4j_cinema/blob/master/imageReadme/listone.png)

Покупка билета
![Покупка билета](https://github.com/OtBbNA/job4j_cinema/blob/master/imageReadme/ticket.png)

Вход
![Вход](https://github.com/OtBbNA/job4j_cinema/blob/master/imageReadme/login.png)

Регистрация
![Регистрация](https://github.com/OtBbNA/job4j_cinema/blob/master/imageReadme/registration.png)
