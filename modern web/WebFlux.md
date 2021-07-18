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

### Dependencies

```groovy
// Spring data framework for r2dbc
implementation 'org.springframework.boot:spring-boot-starter-data-r2dbc'
// h2 driver
runtimeOnly 'com.h2database:h2'
// r2dbc driver for h2
runtimeOnly 'io.r2dbc:r2dbc-h2'
```

### Transaction Management

- By default, each method in repository has its own transaction
- You should add `@Transactional` to a spring-managed java bean's method to enable transaction for that method
- **NOTE: `@Transactional` would only take effect if you invoke it from external class**
  - Check [java - Spring @Transaction method call by the method within the same class, does not work? - Stack Overflow](https://stackoverflow.com/questions/3423972/spring-transaction-method-call-by-the-method-within-the-same-class-does-not-wo) for detail
- **NOTE: `@Transactional` method in spring data r2dbc should return reactive type. e.g. Flux, Mono**

### An example with h2 DB

Model class

```java
public final class Video
{
	@Id
	public final Long id;
	public final String path;
	public final LocalDateTime lastModifiedTime;
	public final String fingerprint;

	@PersistenceConstructor
	public Video(final long id, final String path, final LocalDateTime lastModifiedTime, final String fingerprint)
	{
		this.id = id;
		this.path = path;
		this.lastModifiedTime = lastModifiedTime;
		this.fingerprint = fingerprint;
	}

	public Video(final String path, final LocalDateTime lastModifiedTime, final String fingerprint)
	{
		this.id = null;
		this.path = path;
		this.lastModifiedTime = lastModifiedTime;
		this.fingerprint = fingerprint;
	}
}
```

Repository interface

```java
// Spring data would create implementation for you
public interface VideoRepository extends R2dbcRepository<Video, Long>
{
}
```

Remember to add `@EnableR2dbcRepositories`

```java
@SpringBootApplication
@EnableR2dbcRepositories
public class Pygmalion
{
	public static void main(final String[] args)
	{
		SpringApplication.run(Pygmalion.class, args);
	}
}
```

Configure DB connection in application.yaml:

```yaml
spring:
  r2dbc:
    # File based embedded h2 DB is used
    url: r2dbc:h2:file:///C:/pygmalion/data/h2/pygmalion
    username: tony
    password: tony
```

Then you can inject `VideoRepository` and use it

## Support for frontend routing

- All `/api/**` would be handled by controller
- React built files would be put into `{project root}/public`, they should be served properly
- All other paths should be routed to `/public/index.html`

```java
@Configuration
@EnableWebFlux
public class WebConfig implements WebFluxConfigurer
{
	@Override
	public void addResourceHandlers(final ResourceHandlerRegistry registry)
	{
		// This pattern has lower priority than the following 2 methods. So it's save to match all here
		registry.addResourceHandler("/**")
				// Adding `file:` prefix to tell spring this is not relative to classpath
				// Instead, it's relative to current directory
				.addResourceLocations("file:public/")
				.setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS));
	}

	public void configurePathMatch(final PathMatchConfigurer configurer)
	{
		configurer
				.setUseCaseSensitiveMatch(true)
				.setUseTrailingSlashMatch(false)
				.addPathPrefix("/api", HandlerTypePredicate.forAnnotation(RestController.class));
	}

	@Bean
	public RouterFunction<ServerResponse> indexRouter(@Value("file:public/index.html") final Resource indexHtml)
	{
		return route(
				request ->
				{
					if (request.method() != HttpMethod.GET) return false;
					final String path = request.path();
					return !path.startsWith("/api") &&
							!path.startsWith("/static") &&
							// Path's last part should not have dot. Otherwise it's treated as a file
							path.indexOf('.', path.lastIndexOf('/')) < 0;
				},
				request -> ok().contentType(MediaType.TEXT_HTML).bodyValue(indexHtml));
	}
}
```

