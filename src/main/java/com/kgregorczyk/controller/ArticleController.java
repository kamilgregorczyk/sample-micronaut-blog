package com.kgregorczyk.controller;

import com.kgregorczyk.dto.ArticleDTO;
import com.kgregorczyk.model.Article;
import com.kgregorczyk.service.ArticleService;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.reactivestreams.client.Success;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.annotation.Status;
import io.micronaut.security.annotation.Secured;
import io.micronaut.validation.Validated;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import javax.inject.Inject;
import javax.validation.Valid;
import org.bson.types.ObjectId;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;

@Validated
@Controller("/articles")
class ArticleController {
  private final ArticleService articleService;
  private final ModelMapper modelMapper;

  @Inject
  ArticleController(ArticleService articleService) {
    this.articleService = articleService;
    this.modelMapper = new ModelMapper();
    this.modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
  }

  @Secured("isAnonymous()")
  @Get
  Flowable<Article> getArticles() {
    return articleService.findAllArticles();
  }

  @Secured("isAnonymous()")
  @Post
  @Status(HttpStatus.CREATED)
  Single<Success> createArticle(@Body @Valid ArticleDTO articleDTO) {
    return articleService.save(modelMapper.map(articleDTO, Article.class));
  }

  @Secured("isAnonymous()")
  @Get("/{id}")
  Maybe<Article> getArticle(String id) {
    return articleService.findById(id);
  }

  @Secured("isAnonymous()")
  @Put("/{id}")
  Maybe<Single<UpdateResult>> updateArticle(String id, @Body ArticleDTO articleDTO) {
    return articleService
        .findById(id)
        .isEmpty()
        .flatMapMaybe(
            isEmpty -> {
              if (isEmpty) {
                return Maybe.empty();
              } else {
                return mergeAndUpdateArticle(id, articleDTO);
              }
            });
  }

  @Secured("isAnonymous()")
  @Delete("/{id}")
  Maybe<DeleteResult> deleteArticle(String id) {
    return articleService
        .deleteById(id)
        .flatMap(
            deleteResult ->
                deleteResult.getDeletedCount() >= 1 ? Maybe.just(deleteResult) : Maybe.empty());
  }

  private Maybe<Single<UpdateResult>> mergeAndUpdateArticle(String id, ArticleDTO articleDTO) {
    return articleService
        .findById(id)
        .map(
            articleFromDb -> {
              modelMapper.map(articleDTO, articleFromDb);
              return articleService.update(new ObjectId(id), articleFromDb);
            });
  }
}
