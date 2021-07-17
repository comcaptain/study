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

## Project Reactor

References:

- [Flight of the Flux 1 - Assembly vs Subscription (spring.io)](https://spring.io/blog/2019/03/06/flight-of-the-flux-1-assembly-vs-subscription)

### Assembly Time

- Similar to `Stream` in Java, when creating/assembling `Flux`, `Mono`, the pipeline would not be triggered

- This **declarative** phase is called **assembly time**

- When you do operations on `Flux`/`Mono`, e.g. `map`. `filter`...

  - It would not mutate the flux itself

  - Instead, it would create a new flux instance. So flux is an **onion**

  - i.e. flux is immutable

  - So unlike `Stream`, which cannot be reused, you can reuse flux in anyway you like. e.g.

    ```java
    // You can create 2 separate fluxes from one single flux
    Mono<String> quote = xxx
    Mono<String> evenLength = quote.filter(str -> str.length() % 2 == 0);
    Mono<String> oddLength = quote.filter(str -> str.length() % 2 == 1);
    ```

- Unlike `CompletableFuture`, flux would never be triggered until a subscriber subscribes

### Subscription Time

- The most common way to subscribe is to invoke `Flux.subscribe(valueConsumer, errorConsumer)`

- Subscription subscription would be propagated backwords through the chain of operators, up until the *source* operator, i.e. the `Publisher` that acutally produces the initial data

- An example:

  ```java
  makeHttpRequest() //<5>
      .map(req -> parseJson(req)) //<4>
      .map(json -> json.getString("quote")) //<3>
      .flatMapMany(quote -> Flux.fromArray(quote.split(" "))) //<2>
      .subscribe(System.out::println, Throwable::printStackTrace); //<1>
  ```

### Hot Publisher & Cold Publisher

- The difference comes when
  - There are multiple subscriptions on the same publisher
  - More specificly, there are multiple flux/mono instances on the same source, and different flux/mono instances subscribe
- For cold publisher, each subscription would make publisher publish from the beginning
  - e.g. If source is a `makeHttpRequest()`, then each subscription would send a separate HTTP request
- For hot publisher
  - The publisher starts emitting events from the 1st subscription
  - For all incoming subscriptions, publisher would not re-emit events from the beginning
  - Instead, publisher would broadcast the same emiited event to all subsribers
  - e.g. publisher would emit 1, 2, 3...
  - First subsriber would receive 1, 2, 3...
  - If another subscriber subscribes when after publisher emits 2, then it would only be able to receive 3, 4, 5...
- We can convert cold publisher to hot publisher by invoking `share()`
  - `Flux.share()`
  - `Mono.share()`
    - This is a bit special. If subscription happens after the source emits event, the new subscriber would still get the previously-emiited event and publisher would not be triggered

#### ConnectableFlux

- We can convert Flux to ConnectableFlux by invoking `Flux.publish()`
- When subscriber subscribes, publisher would not publish until `ConnectableFlux.connect` is invoked
- When `connect` is invoked, it would request publisher for `Queues.SMALL_BUFFER_SIZE` events
- After `connect` is invoked, the flux would become hot. i.e. New subscription would not make publisher re-emit events from the beginning
- After if one subscriber is slower than the others and its request count is 0, then publisher would wait f



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

