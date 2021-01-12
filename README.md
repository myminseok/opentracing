
##  webclient sample app tracing with jaeger 
 
### run jaeger server (opentracing UI)
- getting start  https://www.jaegertracing.io/docs/1.21/getting-started/
- bosh release: https://github.com/andy-paine/jaeger-boshrelease/tree/master/manifests/all-in-one

```bash
docker run -d --name jaeger \
  -e COLLECTOR_ZIPKIN_HTTP_PORT=9411 \
  -p 5775:5775/udp \
  -p 6831:6831/udp \
  -p 6832:6832/udp \
  -p 5778:5778 \
  -p 16686:16686 \
  -p 14268:14268 \
  -p 14250:14250 \
  -p 9411:9411 \
  jaegertracing/all-in-one:1.21
```

open http://localhost:16686/

### build and run app
#### jaeger sample
- https://github.com/jaegertracing/jaeger-client-java/tree/master/jaeger-core
- java client sample: https://github.com/jaegertracing/jaeger-client-java
#### zipkin sample

to minimize app coding, we will use zipkin client rather than jagger client

```bash

git clone https://github.com/myminseok/opentracing

cd greeter-messages
gradle clean assemble
gradle bootRun


cd greeting
gradle clean assemble
gradle bootRun
```

### call app and check jaeger ui
http://localhost:9000/hello-webclient
```
{"message":"Hello, Bob!"}
```