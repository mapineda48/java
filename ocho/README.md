# Ocho

This project was carried out as the final activity of cycle 4 in training as a [software programmer at the Sergio Arboleda University](https://www.usergioarboleda.edu.co/noticias/la-sergio-hara-parte-de-mision-tic-2022-el-programa-para-capacitar-a-mas-de-50-mil-colombianos-en-habilidades-y-competencias-4-0/).

# Usage

- ## docker-compose

Do not complicate configuring an environment to run the application, this demo has a [container](https://hub.docker.com/r/mapineda48/ocho) available in [dockerhub](https://hub.docker.com/), the only thing you should have installed on your pc is [docker-compose](https://docs.docker.com/compose/), create a directory, in this create a file called `docker-compose.yml` and add the following:

```yml
version: "3.8"
services: 
  web:
    image: "mapineda48/ocho"
    environment:
      MONGO_URI: "mongodb://app:example@db:27017/ocho?authSource=admin"
    volumes:
      - "$PWD:/home/app"
    ports:
      - "3000:8080"
    depends_on:
      - db
  db:
    image: mongo:4.4.11-rc0-focal
    environment:
      MONGO_INITDB_ROOT_USERNAME: app
      MONGO_INITDB_ROOT_PASSWORD: example
      MONGO_INITDB_DATABASE: ocho

```
now open a terminal and navigate to the directory where you created the file and run:

```sh
docker-compose up
```
Wait a few minutes while the dependencies are downloaded, the services are configured and when you see that the services are ready go to the browser and go to http://localhost:3000

- ## docker

If you prefer you can also use the container without docker-compose:

```sh
docker run \
    --name demo \
    -p 3000:8080 \
    -e "MONGO_URI=<here your uri connectio to mongodb>" \
    -d \
    mapineda48/ocho
```

## License

MIT

**Free Software, Hell Yeah!**

## Disclaimer

- This project was bootstrapped with [Spring Initializr](https://start.spring.io/).
