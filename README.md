# spring-cloud-microservices
A set of microservices using Spring Cloud, WebFlux and reactive OpenFeign.

* **catalog-service**: service to return/search productss existing in a catalog.
* **inventory-service**: service to return the stock for a given product or list of products.
* **products-service**: service that implements reactive feign clients to fecth data from both catalog and inventory services and consolidate the data in a product search endpoint.
* **config-service**: spring cloud config server to hold the configuration for the three microservices mentioned above.
* **edge-service**: spring cloud gateway service to route the three microservices mentioned above.
* **register-service**: eureka server to register the three microservices mentioned above.
