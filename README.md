# TaskFlow Manager

**TaskFlow Manager** — это полнофункциональное веб-приложение для управления задачами (Task Tracker).
Проект реализует REST архитектуру с разделением на Backend (Spring Boot) и Frontend (Angular), упакованную в Docker контейнеры.

Реализована безопасная система аутентификации с использованием **JWT** и **OAuth2** (Google, GitHub).

---

## Технологический стек

### Backend (Java)
*   **Java 17** & **Spring Boot**
*   **Spring Security** (Stateless Authentication)
*   **JWT** (Access + Refresh Tokens flow architecture)
*   **OAuth2 Client** (Интеграция с Google и GitHub)
*   **Spring Data JPA** + **PostgreSQL**
*   **Hibernate** (ORM)
*   **Lombok** (Boilerplate code reduction)
*   **Maven** (Build tool)

### Frontend (Angular)
*   **Angular** 
*   **TypeScript**
*   **RxJS** (Reactive Programming)
*   **CSS** (Стилизация)
*   **Nginx** (Reverse Proxy & Static serving)

### DevOps & Tools
*   **Docker** & **Docker Compose** (Полная контейнеризация)
*   **Postman** (API Testing)

---

## Ключевые возможности

*   **Безопасность:**
    *   Регистрация и вход по Email/Password.
    *   Вход через социальные сети (**Google**, **GitHub**).
    *   Аутентификация с помощью **JWT токенов**.
*   **Управление задачами:**
    *   CRUD операции (Создание, Чтение, Обновление, Удаление).
    *   Каждый пользователь видит и управляет только своими задачами.
    *   Статусы задач (TODO, IN_PROGRESS, DONE).
