package com.kgregorczyk.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

  @JsonSerialize(using = ToStringSerializer.class)
  private ObjectId id;

  private String firstName;
  private String lastName;
  private State state = State.ACTIVE;

  private String email;
  private String password;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
  private Date createdAtDate = new Date();

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
  private Date lastUpdatedAt = new Date();

  public enum State {
    ACTIVE,
    NOT_ACTIVE,
    BANNED
  }
}
