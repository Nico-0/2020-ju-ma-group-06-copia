# 2020-ju-ma-group-06
# Gestión de Proyectos Sociales

## Run with Docker

- `docker compose up`
- Re run backend a second time to get rid of "Table not found"


## Run locally

- Change `mysql-gesoc-2020` to `localhost` in `src\main\resources\META-INF\persistence.xml`
- Configure your database in the same file
- Build with `mvn package` and run with `java -jar target/app.jar`


## Scheduled tasks

- Contains extra main classes at `ar.edu.frba.dds.gesoc`, to run with an external task scheduler.
