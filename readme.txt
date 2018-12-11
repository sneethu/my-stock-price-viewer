h2 db

db-service(port : 8300)
GET : http://localhost:8300/rest/db/Peter
POST : http://localhost:8300/rest/db/add 
       {"username":"Peter","quotes": ["GOOG", "Apple"]}
GET : http://localhost:8300/rest/db/Peter

stock-service(port : 8301)
GET : http://localhost:8301/rest/stock/Peter makes a call to http://localhost:8300/rest/db/Peter as db-service and stock-service are registered on eureka server(service registry)
      restTemplate is used to talk between micro services here
POST : not supported. Add a user to the db only through db-service

eureka-server(port : 8302)
http://localhost:8302   - To view the dashboard of eureka server

db-service and stock-service are eureka clients(Look at the ymls)

Now, API gateway(Zuul) is embedded within the Eureka-server in this case. Look for spring-cloud-starter-zuul in the pom of eureka-server and the app is annotated with @EnableZuulProxy
So the api-gateway is running on 8302. 
GET : http://localhost:8302/api/stock-service/rest/stock/Peter
		api :is the prefix for all calls
		stock-service : is referred in the yml so that zuul routes it.
		/rest/stock/Peter : is the actual web end point exposed by stocl-service 

In the example I saw, UI makes a call to the api-gateway like this - http://localhost:8302/api/stock-service/rest/stock/Peter

Youtube link - https://www.youtube.com/watch?v=rlS9eH5tEnY

	 
	   