# CAF Swagger UI
Centralised and easy to use configuration for serving the Swagger UI for Spring Boot RESTful services

Simply add the `CAFSwaggerUI` annotation to a SpringBoot application and specify the package where the Swagger contract.yaml file is
 located.
 
 The annotation will also read the `management.server.port` property and, if it exists will register an interceptor to prevent loading the
  Swagger UI from this port. 
 
 ## Example
 
See [this commit](https://github.com/CAFDataProcessing/staging-service/commit/f4a051f08b522a1cf806083dd1d1b2237a88cddc) to the Staging Service for an example of using this annotation.
