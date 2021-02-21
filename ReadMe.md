### Getting started without docker-compose
   In order to run the application without docker-compose, ensure that you have a Postgres instance running on localhost port 5432.
               
   Database creation:
```
      - docker pull postgres
      - docker run -d --name postgres-db -e POSTGRES_PASSWORD=secret -p 5432:5432  postgres
              
      - docker exec -ti <4 first letters from ID container> psql -U postgres
      - create database note_db;
```