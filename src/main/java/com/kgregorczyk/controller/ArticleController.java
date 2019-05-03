package com.kgregorczyk.controller;

import static io.micronaut.security.rules.SecurityRule.IS_ANONYMOUS;

import com.kgregorczyk.dto.ArticleDTO;
import com.kgregorczyk.model.Article;
import com.kgregorczyk.service.ArticleRepository;
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
import io.micronaut.security.rules.SecurityRule;
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
  private final ArticleRepository articleRepository;
  private final ModelMapper modelMapper;

  @Inject
  ArticleController(ArticleRepository articleRepository) {
    this.articleRepository = articleRepository;
    this.modelMapper = new ModelMapper();
    this.modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
  }

  @Get
  @Secured(IS_ANONYMOUS)
  Flowable<Article> getArticles() {
    return articleRepository.findAllArticles();
  }

  @Post
  @Status(HttpStatus.CREATED)
  @Secured(IS_ANONYMOUS)
  Single<Success> createArticle(@Body @Valid ArticleDTO articleDTO) {
    return articleRepository.save(modelMapper.map(articleDTO, Article.class));
  }

  @Get("/{id}")
  @Secured("isAnonymous()")
  Maybe<Article> getArticle(String id) {
    return articleRepository.findById(id);
  }

  @Put("/{id}")
  @Secured(SecurityRule.IS_ANONYMOUS)
  Maybe<Single<UpdateResult>> updateArticle(String id, @Body ArticleDTO articleDTO) {
    return articleRepository
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

  @Delete("/{id}")
  @Secured(SecurityRule.IS_ANONYMOUS)
  Maybe<DeleteResult> deleteArticle(String id) {
    return articleRepository
        .deleteById(id)
        .flatMap(
            deleteResult ->
                deleteResult.getDeletedCount() >= 1 ? Maybe.just(deleteResult) : Maybe.empty());
  }

  private Maybe<Single<UpdateResult>> mergeAndUpdateArticle(String id, ArticleDTO articleDTO) {
    return articleRepository
        .findById(id)
        .map(
            articleFromDb -> {
              modelMapper.map(articleDTO, articleFromDb);
              return articleRepository.update(new ObjectId(id), articleFromDb);
            });
  }
}
