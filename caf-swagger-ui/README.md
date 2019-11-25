# CAF Swagger UI
Centralised and easy to use configuration for serving the Swagger UI for Spring Boot RESTful services

Simply add the `CAFSwaggerUI` annotation to a SpringBoot application and specify the package where the Swagger contract.yaml file is
 located.
 
 The annotation will also read the `management.server.port` property and, if it exists will register an interceptor to prevent loading the
  Swagger UI from this port. 
 
 ## Example
 
 ```
 @CAFSwaggerUI("my.package.with.contract")
 @SpringBootApplication
 public class MyService implements WebMvcConfigurer {
 
 }
```
