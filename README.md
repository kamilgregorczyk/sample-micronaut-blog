# Micronaut Blog

Sample blog application written with the new and shiny micronaut framework. 

It utilizes multiple addons like: 
* micronaut-jwt for authentication
* micronaut-tracing for distributed tracing with jaeger
* micronaut-metrics for prometheus metrics registry & reporter
* micronaut-mongo for reactive persistance

The whole server is build with jib (`./gradlew jibDockerBuild`) and used on kubernetes cluster (config files are provided)
