package com.kgregorczyk.authentication;

import io.micronaut.security.authentication.UserDetails;
import java.util.Collection;
import java.util.Objects;

public class BlogUserDetails extends UserDetails {

  private String id;

  /**
   * @param id e.g. UUID
   * @param email e.g. admin
   * @param roles e.g. ['ROLE_ADMIN', 'ROLE_USER']
   */
  public BlogUserDetails(String id, String email, Collection<String> roles) {
    super(email, roles);
    this.id = id;
  }

  public String getId() {
    return id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    BlogUserDetails that = (BlogUserDetails) o;
    return id.equals(that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), id);
  }
}
