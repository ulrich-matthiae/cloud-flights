# cloud-flights

An example app using spring cloud and mongo-db to show how one could start to build an online flight booking API.

## Testing the app

### Dependencies

The following required services need to be started up

1. Zipkin:
   * Using Docker: `docker run -p 9411:9411 openzipkin/zipkin`
   * Directly:
     `curl -sSL https://zipkin.io/quickstart.sh | bash -s
     java -jar zipkin.jar`
2. RabbitMQ
   * `docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3.10-management`
   * Download and install from https://www.rabbitmq.com/download.html
3. Prometheus
   * In the prometheus directory: `docker run -p 9090:9090 -v <full-path>\cloud-flights\prometheus\prometheus.yml:/etc/prometheus/prometheus.yml  prom/prometheus`

### Microservices

To get the solution running, the different microservices need to be fired up in this order:

1. Eureka Server
2. Config Server
3. Cost service
4. Flight service
5. Booking service
6. Cloud Gateway

Each of these can be started up by running `mvn spring-boot:run` in the appropriate directory

## Usefull URLs

* Eureka dashboard: http://localhost:8761/
* Hystrix stream: http://localhost:8201/hystrix.stream
* Hystrix turbine dashboard: http://localhost:9090/hystrix/monitor?stream=http://localhost:9090/turbine.stream

## Creating and querying bookings

**POST** http://localhost:8201/flights/

```json
{
	"id":"1",
	"flightDate":"2019-01-01",
	"origin":"JOHANNESBURG",
	"destination":"CAPE_TOWN",
	"seatsAvailable":"20"
}
```

**GET** http://localhost:8201/flights/1

