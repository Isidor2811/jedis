Jedis (Java Redis client) POC

To run commands you need to start Redis DB locally

```brew install redis```

```brew services start redis```

or you can use Docker

```docker run --name some-redis -d redis```

Most popular Redis commands you can find at `RedisClient.java` and from `Runner.java` you can test them