# Веб-приложение для выбора и покупки книг
[![Typing SVG](https://readme-typing-svg.herokuapp.com?color=%2336BCF7&lines=House+of+Book)](https://git.io/typing-svg)

В проекте представлена серверная часть сайта **Дом книг**, где представлены все книги из ассортимента магазина с 
описанием, ценой на цифровую и бумажную версию, количество в наличии и т.п.
Пользователь, после регистрации и авторизации, может купить цифровой вариант книги или выбрать бумажный, 
добавлять книги в избранное и в корзину, искать интересующую книгу с помощью 
поиска и фильтра.

## Содержание
- [Технологии](#технологии)
- [О проекте](#о-проекте)
- [Использование](#использование)
- [Зависимости](#зависимости)
- [Тестирование](#тестирование)
- [To do](#to-do)

## Технологии
- Java 19
- Gradle 8.6
- Spring Boot 3.2.4
- Hibernate
- Postgresql
- Liquibase
- OpenAPI 3.0
- Lombok

## О проекте

Проект взаимодействует с базой данных при использованни **postgresql** и развернутой в
**docker** образе. База данных валидируется при запуске, используя **liquibase**, и добавляет
новые изменения при добавлении changelog.

Сам проект расслоен на Controller, Service и Repository.

Разберем жизненный цикл приложения от запроса до ответа
на примере страницы **/main**

### Controller

При попадании на страницу мы увидим список книг и фильтр с
возможностью отобрать и отсортировать книги при необходимости.

```java
@RestController
@RequestMapping("/main")
@RequiredArgsConstructor
public class MainController {

    private final BookService bookService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BookInMainReadDto> findAllBooks(BookFilter bookFilter,
                                                Pageable pageable) {
        return bookService.findAllByFilter(bookFilter, pageable);
    }
}
```

### Service
На уровне сервиса мы вызываем findAllByFilter из BookRepository
и маппим полученный результат в BookInMainReadDto.

```java
public List<BookInMainReadDto> findAllByFilter(BookFilter bookFilter, Pageable pageable) {
        return bookRepository.findAllByFilter(bookFilter, pageable).stream()
        .map(bookInMainReadMapper::map)
        .toList();
}
```

### Service
Далее с помощью CriteriaBuilder и EntityManager преобразуем BookFilter и Pageable
в sql-запрос для получения списка нужных книг.

```java
public List<Book> findAllByFilter(BookFilter filter, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> query = cb.createQuery(Book.class);
        Root<Book> book = query.from(Book.class);
        query.select(book);

        List<Predicate> predicates = new ArrayList<>();

        if(filter.name() != null) predicates.add(cb.like(book.get("name"), capitalized(filter.name())));
        if(filter.author() != null) predicates.add(cb.like(book.get("author"), capitalizedAndContains(filter.author())));
        if(filter.genre() != null) predicates.add(cb.like(book.get("genre"), capitalizedAndContains(filter.genre())));
        if(filter.yearOfPublishGe() != null) predicates.add(cb.ge(book.get("yearOfPublish"), filter.yearOfPublishGe()));
        if(filter.pagesLe() != null) predicates.add(cb.le(book.get("pages"), filter.pagesLe()));

        Integer pricePaperFrom = filter.pricePaperFrom();
        Integer pricePaperTo = filter.pricePaperTo();
        if(pricePaperFrom != null && pricePaperTo != null && pricePaperFrom < pricePaperTo) {
        predicates.add(cb.between(book.get("pricePaper"), pricePaperFrom, pricePaperTo));
        } else if (pricePaperFrom == null && pricePaperTo != null) {
        predicates.add(cb.le(book.get("pricePaper"), pricePaperTo));
        } else if (pricePaperFrom != null && pricePaperTo == null) {
        predicates.add(cb.ge(book.get("pricePaper"), pricePaperFrom));
        }

        Integer priceDigitalFrom = filter.priceDigitalFrom();
        Integer priceDigitalTo = filter.priceDigitalTo();
        if(priceDigitalFrom != null && priceDigitalTo != null && priceDigitalFrom < priceDigitalTo) {
        predicates.add(cb.between(book.get("priceDigital"), priceDigitalFrom, priceDigitalTo));
        } else if (priceDigitalFrom == null && priceDigitalTo != null) {
        predicates.add(cb.le(book.get("priceDigital"), priceDigitalTo));
        } else if (priceDigitalFrom != null && priceDigitalTo == null) {
        predicates.add(cb.ge(book.get("priceDigital"), priceDigitalFrom));
        }

        query.where(predicates.toArray(Predicate[]::new));
        query.orderBy(QueryUtils.toOrders(pageable.getSort(), book, cb));

        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
//        int offset = (int) pageable.getOffset();
        return entityManager.createQuery(query)
//                .setFirstResult(offset)
        .setFirstResult(pageNumber * pageSize)
        .setMaxResults(pageSize)
        .getResultList();
        }
```

## Использование
Запускается проект через класс ApplicationRunner

```java
@SpringBootApplication
public class ApplicationRunner {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationRunner.class, args);
    }
}
```

Картинки книг хранятся в папке images, которая в свою очередь
находится в корневой папке проекта. Вы можете изменить
путь к папке через **application.yml**(app.image.bucket) или в
классе **ImageService**

```java
public ImageService(@Value("${app.image.bucket:C:\\Users\\Frost\\IdeaProjects\\Book_House\\images}") String bucket) {
        this.bucket = bucket;
}
```

Для проверки проекта использовать **Swagger-UI**

В ресурсах находится файл **data.sql** с примерными данными для бд, а сами запросы
для создания и валидации бд находятся в папке **changelog**

## Зависимости

Зависимости проекта из **build.gradle**. Версии можно найти в файле
**version.gradle**

```groovy
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter'
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    implementation 'org.springframework.boot:spring-boot-starter-security'
    implementation 'org.springframework.boot:spring-boot-starter-validation'
    implementation 'org.springframework.data:spring-data-envers'
    implementation "org.springdoc:springdoc-openapi-starter-webmvc-ui:${versions.springdoc}"
    implementation 'org.postgresql:postgresql'
    implementation 'org.liquibase:liquibase-core'
    implementation 'org.springframework.boot:spring-boot-starter-actuator'

    testImplementation 'org.springframework.boot:spring-boot-starter-test'
    testImplementation 'org.springframework.security:spring-security-test'
    testImplementation "org.testcontainers:postgresql:${versions.testcontainers}"
}
```

## Тестирование

Для тестирования в проекте были использованы библиотеки 
testcontainers:postgresql,
junit и springframework.

Проект покрыт integration тестами.
При вызове тестов динамически поднимает docker образ и
бд используя liquibase и файл **test-data.sql** для заполнения бд.

```java
@IT
@Sql({
        "classpath:sql/test-data.sql"
})
@WithMockUser(username = "test@gmail.com", password = "test", authorities = {"ADMIN", "USER"})
public abstract class IntegrationTestBase {

    private static final PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:16.2");

    @BeforeAll
    static void runContainer() {
        container.start();
    }

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
    }
}
```
Аннотация **@WithMockUser** позволяет выполнять все
тесты как авторизованный пользователь, кроме переопределенных тестов

```java
@Test
@WithAnonymousUser
    void anonymousFindAllBooks() throws Exception {
            mockMvc.perform(get("/main")
            .param("name", "Хоббит"))
            .andExpect(status().is2xxSuccessful());
}
```

Аннотация **@IT** для запуска тестового сервера.
```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Transactional
@SpringBootTest
public @interface IT {
}
```

## To do
- [ ] Добавить возможность покупать книги из корзины выборочно
- [ ] Добавить кнопку "Уведомить при поступлении"
- [ ] Покрыть тестами всё приложение
- [x] Добавить крутое README
