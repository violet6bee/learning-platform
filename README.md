# Learning Platform

Учебный проект на **Java + Spring Boot**, реализующий backend для платформы онлайн-обучения.  
Проект предназначен для практики работы с **Spring Data JPA**, **REST API**, **DTO**, **мапперами**, **тестированием** и **Docker**.

Репозиторий:  
https://github.com/violet6bee/learning-platform

---

## Описание проекта

**Learning Platform** — это серверное приложение для управления онлайн-обучением, включающее:

- пользователей и профили;
- курсы, модули и уроки;
- задания и отправки работ;
- тесты (квизы), вопросы и варианты ответов;
- прохождение тестов и сохранение результатов.

Проект построен по классической **слоистой архитектуре** и демонстрирует хороший практический стиль разработки backend-приложений на Spring Boot.

---

## Используемые технологии

- **Java 21**
- **Spring Boot 3**
- **Spring Data JPA**
- **Hibernate**
- **PostgreSQL**
- **H2 (для тестов)**
- **MapStruct**
- **Lombok**
- **JUnit 5 + Mockito**
- **Maven**
- **Docker / Docker Compose**

---

## Архитектура проекта

Controller → Service → Repository → Database
↓
DTO
↓
Mapper


### Основные слои:

- **controller** — REST API endpoints
- **service** — бизнес-логика, транзакции
- **repository** — Spring Data JPA
- **entity** — JPA-сущности + enum’ы
- **dto** — request / response DTO
- **mapper** — MapStruct-мапперы
- **resources** — конфигурация и SQL

---

## Структура проекта

learning-platform/
├── src/
│ ├── main/
│ │ ├── java/shvalieva/learning_platform/
│ │ │ ├── controller/
│ │ │ ├── dto/
│ │ │ ├── entity/
│ │ │ ├── enums/
│ │ │ ├── repository/
│ │ │ ├── service/
│ │ │ └── mapper/
│ │ └── resources/
│ │ ├── application.yml
│ │ ├── application-test.yml
│ │ └── data.sql
│ └── test/
│ └── java/shvalieva/learning_platform/
│     ├── integration/ 
│     └── service/
├── docker-compose.yml
├── pom.xml
└── README.md

---

## Сборка и запуск
1. Клонирование репозитория
git clone https://github.com/violet6bee/learning-platform.git
cd learning-platform

2. Сборка проекта (без тестов)
mvn clean install -DskipTests

3. Сборка с тестами (H2)
mvn clean test -P testh2

4. Запуск приложения
mvn spring-boot:run


Приложение будет доступно по адресу:

http://localhost:8080

## Тестирование
Запуск всех тестов
mvn test

Запуск тестов с H2
mvn test -P testh2

---

## Demo-данные

Проект поддерживает предзаполнение БД через файл:

src/main/resources/data.sql

---

## Реализованные возможности

CRUD для всех основных сущностей
Прохождение тестов с подсчётом результата
Проверка правильных ответов
Оценивание заданий
Работа с enum-типами
DTO + MapStruct
Unit-тесты сервисного слоя
Профили Spring (dev / testh2)
Docker-окружение

