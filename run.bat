echo start eureka-server
cd eureka-server
start cmd.exe /c mvn spring-boot:run
echo zuul-server
cd ../zuul-server
start cmd.exe /c mvn spring-boot:run
echo config-service
cd ../config-service
start cmd.exe /c mvn spring-boot:run
echo booking-service
cd ../booking-service
start cmd.exe /c mvn spring-boot:run
echo cost-service
cd ../cost-service
start cmd.exe /c mvn spring-boot:run
echo flight-service
cd ../flight-service
start cmd.exe /c mvn spring-boot:run
cd ..
