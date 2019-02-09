package com.kgregorczyk;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller("/")
class HelloController {
  @Get()
  String index() {
    return "Hello World";
  }
}
