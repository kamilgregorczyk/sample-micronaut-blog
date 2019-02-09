package com.kgregorczyk.dto;

import com.kgregorczyk.model.Article.State;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDTO {
  @NotEmpty private String title;

  @NotEmpty
  @Length(max = 4000)
  private String description;

  @NotEmpty
  @Length(max = 200)
  private String shortDescription;

  private State state;
}
