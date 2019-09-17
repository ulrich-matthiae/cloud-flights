# cloud-flights
An example app using spring cloud and mongo-db to show how one could start to build an online flight booking API.

## Testing the app
To get the solution running, the different microservices need to be fired up in this order:
1. Eureka Server
2. Zuul Server
3. Config Server
4. Cost service
5. Flight service
6. Booking service

## Usefull URLs
* Eureka dashboard: http://localhost:8761/
* Hystrix dashboard: http://localhost:8201/hystrix
* Hystrix stream: http://localhost:8201/hystrix.stream

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

