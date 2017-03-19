Hotel Management System HireMe notes:

1. There are two ways of running application:
    a) by using "mvn clean package wildfly:run" command
    b) by using docker. This can be done in two steps:
        i. by using mvn command: "sudo mvn clean package -P docker"
        ii. and after changing directory to "docker/": "docker-compose build | docker-compose up"

2. All errors that are handled intentionally end up on Home Page

3. To have initial data there is 60 rooms created, each of them has random type and windows exposure. 30 of them has 5 initially booked dates


