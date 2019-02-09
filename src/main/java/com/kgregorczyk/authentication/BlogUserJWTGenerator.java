package com.kgregorczyk.authentication;

import com.nimbusds.jwt.JWTClaimsSet;
import io.micrometer.core.lang.Nullable;
import io.micronaut.context.annotation.Replaces;
import io.micronaut.runtime.ApplicationConfiguration;
import io.micronaut.security.authentication.UserDetails;
import io.micronaut.security.token.config.TokenConfiguration;
import io.micronaut.security.token.jwt.generator.claims.JWTClaimsSetGenerator;
import javax.inject.Singleton;

@Singleton
@Replaces(bean = JWTClaimsSetGenerator.class)
public class BlogUserJWTGenerator extends JWTClaimsSetGenerator {

  public BlogUserJWTGenerator(
      TokenConfiguration tokenConfiguration,
      @Nullable ApplicationConfiguration applicationConfiguration) {
    super(tokenConfiguration, null, null, applicationConfiguration);
  }

  @Override
  protected void populateWithUserDetails(JWTClaimsSet.Builder builder, UserDetails userDetails) {
    super.populateWithUserDetails(builder, userDetails);
    if (userDetails instanceof BlogUserDetails) {
      builder.claim("id", ((BlogUserDetails) userDetails).getId());
    }
  }
}
