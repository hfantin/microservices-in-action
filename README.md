# exercises of the book "Microservices in Action"

- cap 1 - simple hello world   
- cap 2 - licensing-service   
> test url http://localhost:9000/v1/organizations/{organizationId}/licenses/{licenceId}





### annotations
@RestController tells Spring Boot this is a REST-based services and will automatically serialize/deserialize service request/response to JSON.

- Guidelines to name endpoints
1. Use clear URL names that establish what resource the service represents — Having a canonical format for defining URLs will help your API feel more intuitive and easier to use. Be consistent in your naming conventions.   
2. Use the URL to establish relationships between resources — Oftentimes you’ll have a parent-child relationship between resources within your microservices where the child doesn’t exist outside the context of the parent (hence you might not have a separate microservice for the child). Use the URLs to express these relationships. But if you find that your URLs tend to be excessively long and nested, your microservice may be trying to do too much.   
3. Establish a versioning scheme for URLS early — The URL and its corresponding endpoints represent a contract between the service owner and consumer of the service. One common pattern is to prepend all endpoints with a version number. Establish your versioning scheme early and stick to it. It’s extremely difficult to retrofit versioning to URLS after you already have several consumers using them.   


#### alternatives to json
- [Apache Thrift](http://thrift.apache.org)   
- [Apache Avro protocol](http://avro.apache.org)    

#### books
- REST in Practice by Ian Robinson, et al (O’Reilly, 2010).
