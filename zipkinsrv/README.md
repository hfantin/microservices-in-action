To use with a broker(rabbitmq or kafka):
@EnableZipkinStreamServer

Data stores are
1. In-memory data
2. My SQL : http://mysql.com
3. Cassandra: http://cassandra.apa
4. Elasticsearch: http://elastic.co

to configure a zipkin data store:
https://github.com/openzipkin/zipkin/tree/master/zipkin-server


# TODO 
-  AlwaysSampler on Application.kt not working
```
@Bean
fun  defaultSampler() : Sampler  = AlwaysSampler()
```