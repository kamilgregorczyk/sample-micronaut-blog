package com.kgregorczyk.controller;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;

@Controller()
public class IndexController {

  @Get
  @Secured("isAnonymous()")
  public String index() {
    return "Hello World!";
  }
}
