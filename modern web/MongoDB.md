## Use MongoDB in Java

### Dependency

```groovy
implementation 'org.mongodb:mongodb-driver-sync:4.2.3'
```

### Connect and Query

```java
final MongoDatabase mongoDatabase = 
    MongoClients.create("mongodb://localhost:27017").getDatabase("video-tagger");
final FindIterable<Document> videoDocuments = mongoDatabase.getCollection("Video").find();
for (final Document videoDocument : videoDocuments)
{
    final String path = videoDocument.getString("path");
    final ObjectId objectId = videoDocument.getObjectId("_id");
    ...
}
```

