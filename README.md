# App setup

### Setup database:

- Run MySql server
- Execute db/setup-db.sql script - creates database
- Execute db/create-schema.sql script - creates table in the database
- Execute the scripts located in db/test-data - fill the table with test data (each script contains 1 mln records)
- Execute db/index-db.sql script - creates indices to the table
- In case you want to delete the table, execute db/drop-schema.sql script

### Run RabbitMQ container via docker

We need a RabbitMQ broker available to connect to.

Add to Dockerfile:

```
FROM rabbitmq
 
RUN rabbitmq-plugins enable --offline rabbitmq_management
 
EXPOSE 15671 15672
```
 
Download RabbitMQ management image:

`docker pull rabbitmq:3-management`

Run docker container:

`docker run -d -p 5672:5672 -p 15672:15672 --name my-rabbit rabbitmq:3-management`

Port 5672 is exposed so that our application can connect to RabbitMQ.

And port 15672 is exposed so that we can see what our RabbitMQ broker is doing.

### Run the application

The application runs on default port 8080

http://localhost:8080/users - user operations

http://localhost:8080/amqp - bulk insert via AMQP
