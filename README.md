# Workplace Booking reference application
Two microservices developed for a workplace booking reference application.

Original workplace booking reference application was designed and developed to fit the purpose of Spring 4 internal training prepared during summer 2015.

Logging Service (**loggingSrv**) is a microservice that provides a simple logging interface.
Notification Service (**notificationSrv**) is a microservice that manages booking notifications.

Both modules follow the same guidelines:
 * Developed using Java 8
 * Use of Spring Boot
 * Spring 4 with 100% annotation configuration

Notification Service includes also:
 * RESTful API implementation (RestController Spring annotation), exposed under **/api/notifications**
 * AOP to manage logging for controller classes
 * Persistence with in-memory H2 Database (version delivered for the training used PostgreSQL)
 * Use of JPA Spring repositories
 * Stream and Optional features from Java 8

## How to run and test the microservices
Requirements:
 * Java 8
 * Apache Maven 3+

After cloning the repository, launch first the Logging Service and then the Notification Service. Notification Service can be easilly tested using curl or Postman app. Logging Service can be tested by checking **/tmp/appLogs.log** or **c:\tmp\appLogs.log** file after Notification Service testing.
 
## License
Released under MIT license. Please refer to LICENSE file
