# [Hibernate 5.6](https://docs.jboss.org/hibernate/orm/5.6/userguide/html_single/Hibernate_User_Guide.html)

## [2. Domain Model](https://docs.jboss.org/hibernate/orm/5.6/userguide/html_single/Hibernate_User_Guide.html#domain-model)

### [Set up rules to map JPA table/column name to DB table/column name](https://docs.jboss.org/hibernate/orm/5.6/userguide/html_single/Hibernate_User_Guide.html#PhysicalNamingStrategy)

- e.g. All DB table/column names are joined by `_`, while in JPA model class, table/column name are camel case
- Instead of explicitly specifying table/column name in annotation, you can implement your own `PhysicalNamingStrategyStandardImpl` to implement this

### [Basic Types](https://docs.jboss.org/hibernate/orm/5.6/userguide/html_single/Hibernate_User_Guide.html#basic)

## [3. Bootstrap](https://docs.jboss.org/hibernate/orm/5.6/userguide/html_single/Hibernate_User_Guide.html#bootstrap)

- There are 2 ways to bootstrap: hibernate way and JPA standard way
- Hibernate way:
  - This is not recommended way to bootstrap. But when you want to do some hibernate-specific customization while bootstrapping, it's worth checking this doc
  - Even if your app is boostrapped in JPA standard way, since JPA `EntityManagerFactory` is backed by hibernate's `SessionFactory`, hibernate provides a way to customization mentioned in hibernate bootstrapping (check [this doc](https://docs.jboss.org/hibernate/orm/5.6/userguide/html_single/Hibernate_User_Guide.html#bootstrap-jpa-metadata) for more detail)
- JPA way:
  - Create a `META-INF/persistence.xml` file, check [this doc](https://docs.jboss.org/hibernate/orm/5.6/userguide/html_single/Hibernate_User_Guide.html#bootstrap-jpa-compliant) for a sample
  - Invoke `Persistence.createEntityManagerFactory("pygmalion", Map.of("javax.persistence.jdbc.password", "tony"))` to create an `EntityManagerFactory`
    - `pygmalion` is name of `persistence-unit` node in `persistence.xml`
    - The second parameter is optional, inside which you can override properties defined in `persistence.xml`
  - You can also choose to initialize without `persistence.xml`:
    1. Implement your own `PersistenceUnitInfo`
    2. Call `HibernatePersistenceProvider.createContainerEntityManagerFactory` to create `EntityManagerFactory`