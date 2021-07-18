## Operators

### reduceWith

With it, you can convert `Flux` into `Mono<Map>`, `Mono<List>`, `Mono<Set>`...

```java
Flux<Tuple2<ObjectId, Video>> videos = xxx
Mono<Map<ObjectId, Video>> mapMono = videos.reduceWith(HashMap::new, (map, tuple) ->
	{
		map.put(tuple.getT1(), tuple.getT2());
		return map;
	});
```

