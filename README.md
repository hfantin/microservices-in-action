# exercises of the book "Microservices in Action"

- chapter 1 - simple hello world   
- chapter 2 - licensing-service   
- chapter 3 - configuration service
- chapter 4 - service discovery
- chapter 5 - resiliency patterns - hystrix
- chapter 6 - service routing  and zuul
- chapter 7 - security
- chapter 8 - event driven architecture(EDA)

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

Client libraries in wich a service consumer can iteract with Ribbon(Client side loadbalancer):
- spring discovery client
- spring discovery client enabled restTemplate
- netflix feing client


Client side resiliency patterns:    
- client side load balancer   
ex.: Ribbon(chapter 4) - cahche the physical location
of said service instances. whenever the consumer needs
to call a service instance, the load balancer will return
the location from the pool of service location its maintaining
If the instance became slow, the load balancer can detect and remove it from the pool

- circuit breakers:   
this pattern is based on electrical circuit breaker, where it will detect if there is too much
current flowing through the wire and break the connection to avoid components being fried.
With the software, the circuit breaker will detect if a remote call takes too long and will kill the call if its necessary.
In addition, he will monitor future calls, and if it fails, he will failing fast and prevent future calls to the failing remote resource. 
- fallbacks:   
with thist pattern, when a remote service fails, rather than generating an exception, the consumer will execute an 
alternative code path, and try to carry out an action through another means. 
- bulkheads: 
this pattern is based on a concept from building ships. The ship is divided into segregated compartments(bulkheads), 
and if one is punctured, the ship will keep the water confined to that area, preventing the entire ship from sinking.
The same concept is applied to a service that must interact with multiple remotes resources. Bulkhead patter uses a thread pool
and if one is responding slowly, the thread pool will stop the processing request.   

Hystrix environment configuration levels:   
1. Default for the entire application   
2. Default for the class   
3. Thread-pool level defined within the class   

Hystrix annotations: 

|  property name | default value  | description | 
|---|---|---|
| fallbackMethod | none  | Identifies the method within the class that will be called if the remote call times out. The callback method must be in the same class as the @HystrixCommand annotation and must have the same method signature as the calling class. If no value, an exception will be thrown by Hystrix.  |
| threadPoolKey | none  | Gives the @HystrixCommand a unique name and creates a thread pool that is independent of the default thread pool. If no value is defined, the default Hystrix thread pool will be used.   |
| threadPoolProperties | none  | Core Hystrix annotation attribute that’s used to configure the behavior of a thread pool  |
| coreSize | 20 |  Sets the size of the thread pool  |
| maxQueueSize | -1 | Maximum queue size that will set in front of the thread pool. If set to -1, no queue is used and instead Hystrix will block until a thread becomes available for processing. |
| circuitBreaker.request-VolumeThreshold| 20 | Sets the minimum number of requests that must be processed within the rolling window before Hystrix will even begin examining whether the circuit breaker will be tripped. This value can only be set with the commandPoolProperties attribute. |
| circuitBreaker.error-ThresholdPercentage | 50  |  The percentage of failures that must occur within the rolling window before the circuit breaker is tripped. Note: This value can only be set with the commandPoolProperties attribute.  |
| circuitBreaker.sleep-WindowInMilliseconds | 5000  | The number of milliseconds Hystrix will wait before trying a service call after the circuit breaker has been tripped. Note: This value can only be set with the commandPoolProperties attribute   |
| metricsRollingStats.timeInMilliseconds | 10000 | The number of milliseconds Hystrix will collect and monitor statistics about service calls within a window   |
| metricsRollingStats.numBuckets | 10  |  The number of metrics buckets Hystrix will maintain within its monitoring window. The more buckets within the monitoring window, the lower the level of time Hystrix will monitor for faults within the window.  |

cross cutting concerns included in a **Service Gateway**:
- Static routing   
- dynamic routing   
- authentication and authorization   
- metric collection and logging   


security:   
- As you build your microservices for production use, you should be building your microservices security around the following practices:   

1. Use HTTP S/Secure Sockets Layer (SSL) for all service communication.
2. All service calls should go through an API gateway.
3. Zone your services into a public API and private API .
4. Limit the attack surface of your microservices by locking down unneeded network ports.

logs
1. [return the correlation id](http://cloud.spring.io/spring-cloud-static/spring-cloud-sleuth/1.0.12.RELEASE]




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
6. [jpa repository](https://docs.spring.io/spring-data/data-commons/docs/1.6.1.RELEASE/reference/html/repositories.html)      
7. github:   
[cap3](https://github.com/carnellj/spmia-chapter3)   
[cap4](https://github.com/carnellj/spmia-chapter4)   
[cap5](https://github.com/carnellj/spmia-chapter5)   
[cap6](https://github.com/carnellj/spmia-chapter6)   
[cap7](https://github.com/carnellj/spmia-chapter7)   
[cap8](https://github.com/carnellj/spmia-chapter8)
[cap9](https://github.com/carnellj/spmia-chapter9)      
[config server](https://github.com/carnellj/config-repo)    

8. [kafka](https://www.cloudera.com/documentation/kafka/latest/topics/kafka_command_line.html)    
   [another example](https://www.confluent.io/blog/spring-for-apache-kafka-deep-dive-part-2-apache-kafka-spring-cloud-stream)   
9. [cloud stream with rabbitmq](https://www.baeldung.com/spring-cloud-stream)    


#### books
- REST in Practice by Ian Robinson, et al (O’Reilly, 2010).


### TODOS: 
- default appliction test failure - https://spring.io/tools:   
```
com.github.hfantin.authenticationservice.ApplicationTests > contextLoads FAILED
    java.lang.IllegalStateException
        Caused by: org.springframework.beans.factory.BeanDefinitionStoreException
            Caused by: java.lang.IllegalStateException
                Caused by: java.lang.NoClassDefFoundError
                    Caused by: java.lang.ClassNotFoundException

1 test completed, 1 failed
```

- learn about spring-cloud-contract

- correlationId between EDA - licensing and organization
- EDA - updates cache in organization service
- replace organization-new by organization-service with param NEW
- converts specialroutes-service into kotlin 
- updates kotlin to latest version  


### Kafka: 




### papertrail:

logs3.papertrailapp.com:19373
