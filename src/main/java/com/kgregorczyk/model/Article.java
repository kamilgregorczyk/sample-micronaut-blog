package com.kgregorczyk.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class Article {

  @JsonSerialize(using = ToStringSerializer.class)
  @JsonProperty("_id")
  @JsonInclude(Include.NON_NULL)
  private ObjectId id;

  private String title;
  private String description;
  private String shortDescription;
  private State state = State.NOT_PUBLISHED;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
  private Date createdAtDate = new Date();

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
  private Date lastUpdatedAt = new Date();

  public enum State {
    PUBLISHED,
    NOT_PUBLISHED
  }
}
