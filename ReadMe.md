### Getting started 
   In order to run the application with docker-compose, ensure that you have docker-compose installed on your machine.
               
               
   After running the command below docker will build two backend services, frontend application and postgres database.
   
   Database will be initialised by manager-api service, that will create schema and inject data (from files schema.sql, data.sql).         
               
   From root directory run the next command:
```
      - docker-compose up
```

### Example usages

   After the process of creating and running images is complete the application will be available on 
```
       - localhost:3000/ 
 ```

   In the manager-api directory you can find testAPI.http file, which contains a list of CRUD requests for testing the API. 
   A similar file exists in developer-api folder for testing notes history API.
