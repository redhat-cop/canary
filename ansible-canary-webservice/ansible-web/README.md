Docker Compose comands to setup the postgres database

$ docker-compose up

# insert the data into the database
  $ psql -U redhat -h localhost -d redhat < setup.sql

