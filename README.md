# Hotel-Rating-App
 This is Microservice Architecture Application using Spring Boot
 
 Implemnation Details of this Application--
 
 1. In Rating Microservice I have used MongoDB(NoSQL database), in Hotel Microservice I have used PostgreSQL, in User Microservice I have used MySQL.
 2. I have implemented Service Registry using Eureka Server and implemented sercvice Diacovery client in all Microservices.
 3. Communication among Microservices using Rest template as well as Feign Client.
 4. Implemented API Gateway.
 5. Created common config server which can read the configuration from Git Hub as well (https://github.com/SudhakarRathaur/microservice-hotel-config). 
 6. Implemented Fault tolerence, circuit breaker, RETRY, Rate Limiter using Resillience4J.
 7. Secured the Microservices using OKTA Auth.
 8. Implemented the interceptor for both Rest Template and Feign Client.
