package com.kgregorczyk.authentication;

import io.micronaut.security.authentication.AuthenticationFailed;
import io.micronaut.security.authentication.AuthenticationProvider;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.reactivex.Flowable;
import java.util.Arrays;
import javax.inject.Singleton;
import org.reactivestreams.Publisher;

@Singleton
public class BlogUserAuthenticationProvider implements AuthenticationProvider {

  @Override
  public Publisher<AuthenticationResponse> authenticate(
      AuthenticationRequest authenticationRequest) {
    if (authenticationRequest.getIdentity().equals("sherlock")
        && authenticationRequest.getSecret().equals("password")) {
      return Flowable.just(
          new BlogUserDetails(
              "1",
              (String) authenticationRequest.getIdentity(),
              Arrays.asList("ROLE_USER", "ROLE_ADMIN")));
    }
    return Flowable.just(new AuthenticationFailed());
  }
}
