# Scala macros, slick 3.x with evolutions example

## Requirements

1. [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
2. [sbt 1.x.x](https://www.scala-sbt.org/) 
3. [Docker](https://www.docker.com/products/overview)

### Start docker mysql containers

```sh
docker-compose up -d
```

### Start application

```sh
sbt run noname
```