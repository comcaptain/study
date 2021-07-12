## [Reactive Stream](https://www.reactive-streams.org/)

It provides a very small API jar that contains following interfaces

- Publisher
- Subscriber
- Subscription
- Processor
  - An interface that does nothing but to extend Subscriber and Publisher

It provides the lowest level interfaces, and there are some implementations of it:

- Project Reactor (default implementation for Spring Boot)
- RxJava
- ...

## Reactive DB

### [R2DBC](https://r2dbc.io/) (Reactive Relational Database Connectivity)

- This is a standard whose position is similar to JDBC
- Most popular DBs already provide r2dbc drivers, e.g. Oracle, MySQL, MariaDB, PostgreSQL, H2
- But unluckily Sybase does not have r2dbc driver yet
- Luckily, according to [this](https://stackoverflow.com/a/63899436/2334320), we can use JPA and r2dbc in the same spring project
  - Then for main DB, which is oracle, we use r2dbc
  - And for 3rd party system, we use JPA

### Dependencies

```groovy
// Spring data framework for r2dbc
implementation 'org.springframework.boot:spring-boot-starter-data-r2dbc'
// h2 driver
runtimeOnly 'com.h2database:h2'
// r2dbc driver for h2
runtimeOnly 'io.r2dbc:r2dbc-h2'
```

### An example with spring data

Model class

```java
public class Customer {

    @Id
    public Long id;

    public final String firstName;

    public final String lastName;

    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return String.format(
            "Customer[id=%d, firstName='%s', lastName='%s']",
            id, firstName, lastName);
    }
}

```

Repository interface

```java
// Spring data would create implementation for you
public interface CustomerRepository extends ReactiveCrudRepository<Customer, Long> {
    @Query("SELECT * FROM customer WHERE last_name = :lastname")
    Flux<Customer> findByLastName(String lastName);
}
```

Insert and find

```java
    @Bean
    public CommandLineRunner demo(CustomerRepository repository) {

        return (args) -> _demo(repository);
    }

	private void _demo(CustomerRepository repository) {
        _insertNewCustomers(repository)
            .thenMany(Flux.merge(_testFindAll(repository), _testFindByID(repository), _testFindByLastName(repository)))
            .blockLast();
    }

    private Flux<Customer> _testFindByLastName(CustomerRepository repository) {
        return repository.findByLastName("Bauer").doOnNext(bauer -> {
            log.info(bauer.toString());
        });
    }

    private Mono<Customer> _testFindByID(CustomerRepository repository) {
        return repository.findById(1L).doOnNext(customer -> {
            log.info("Customer found with findById(1L):");
            log.info("--------------------------------");
            log.info(customer.toString());
            log.info("");
        });
    }

    private Flux<Customer> _testFindAll(CustomerRepository repository) {
        return repository.findAll().doOnNext(customer -> {
            log.info(customer.toString());
        });
    }

    private Flux<Customer> _insertNewCustomers(CustomerRepository repository) {
        Flux<Customer> saveAll = repository.saveAll(Arrays.asList(new Customer("Jack", "Bauer"),
                new Customer("Chloe", "O'Brian"),
                new Customer("Kim", "Bauer"),
                new Customer("David", "Palmer"),
                new Customer("Michelle", "Dessler")));
        return saveAll;
    }
```

