# exercises of the book "Microservices in Action"

- cap 1 - simple hello world   
- cap 2 - licensing-service   
- cap 3 - configuration service
- cap 4 - service discovery

> test url http://localhost:9000/v1/organizations/{organizationId}/licenses/{licenceId}

### annotations
*@RestController* tells Spring Boot this is a REST-based services and will automatically serialize/deserialize service request/response to JSON.

Guidelines to name endpoints:   
1. Use clear URL names that establish what resource the service represents — Having a canonical format for defining URLs will help your API feel more intuitive and easier to use. Be consistent in your naming conventions.   
2. Use the URL to establish relationships between resources — Oftentimes you’ll have a parent-child relationship between resources within your microservices where the child doesn’t exist outside the context of the parent (hence you might not have a separate microservice for the child). Use the URLs to express these relationships. But if you find that your URLs tend to be excessively long and nested, your microservice may be trying to do too much.   
3. Establish a versioning scheme for URLS early — The URL and its corresponding endpoints represent a contract between the service owner and consumer of the service. One common pattern is to prepend all endpoints with a version number. Establish your versioning scheme early and stick to it. It’s extremely difficult to retrofit versioning to URLS after you already have several consumers using them.   

Microservice development principles:         
1. A microservice should be self-contained and independently deployable with multiple instances of the service being started up and torn down with a single software artifact.   
2. A microservice should be configurable. When a service instance starts up, it should read the data it needs to configure itself from a central location or have its configuration information passed on as environment variables. No human intervention should be required to configure the service.   
3. A microservice instance needs to be transparent to the client. The client should never know the exact location of a service. Instead, a microservice client should talk to a service discovery agent that will allow the application to locate an instance of a microservice without having to know its physical location.   
4. A microservice should communicate its health. This is a critical part of your cloud architecture. Microservice instances will fail and clients need to route around bad service instances.

Microservice lifecicle steps:   
1. Service assembly—How do you package and deploy your service to guarantee repeatability and consistency so that the same service code and runtime is deployed exactly the same way?   
2. Service bootstrapping—How do you separate your application and environment specific configuration code from the runtime code so you can start and deploy a microservice instance quickly in any environment without human intervention to configure the microservice?   
3. Service registration/discovery—When a new microservice instance is deployed, how do you make the new service instance discoverable by other application clients?    
4. Service monitoring—In a microservices environment it’s extremely common for multiple instances of the same service to be running due to high availability needs. From a DevOps perspective, you need to monitor microservice instances and ensure that any faults in your microservice are routed around and that ailing service instances are taken down.

#### Links
1. alternatives to json:    
[Apache Thrift](http://thrift.apache.org)      
[Apache Avro protocol](http://avro.apache.org)       
2. [Heroku 12 factor manifesto](https://12factor.net/)   
3. Logs   
[Splunk](http://splunk.com)   
[Fluentd](http://fluentd.org)   
4. [actuators endpoints](https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-endpoints.html)   
5. [spring cloud centralized configuration](https://spring.io/guides/gs/centralized-configuration/)
6. github:   
[cap3](https://github.com/carnellj/spmia-chapter3)
[cap4](https://github.com/carnellj/spmia-chapter4)
#### books
- REST in Practice by Ian Robinson, et al (O’Reilly, 2010).
