package com.kgregorczyk.controller;

import io.micrometer.prometheus.PrometheusMeterRegistry;
import io.micronaut.configuration.metrics.annotation.RequiresMetrics;
import io.micronaut.configuration.metrics.management.endpoint.MetricsEndpoint;
import io.micronaut.context.annotation.Replaces;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.management.endpoint.annotation.Endpoint;
import io.micronaut.security.annotation.Secured;
import javax.inject.Inject;

@Endpoint(value = "metrics", defaultSensitive = false)
@Controller("/metrics")
@RequiresMetrics
@Replaces(MetricsEndpoint.class)
public class PrometheusMetricsEndpoint {

  private final PrometheusMeterRegistry registry;

  @Inject
  PrometheusMetricsEndpoint(PrometheusMeterRegistry registry) {
    this.registry = registry;
  }

  @Secured("isAnonymous()")
  @Get
  public String metrics() {
    return registry.scrape();
  }
}
