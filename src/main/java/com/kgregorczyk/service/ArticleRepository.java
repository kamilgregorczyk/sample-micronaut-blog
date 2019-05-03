package com.kgregorczyk.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kgregorczyk.model.Article;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.Success;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import java.util.Date;
import javax.inject.Singleton;
import org.bson.types.ObjectId;
import org.reactivestreams.Publisher;

@Singleton
public class ArticleRepository {
  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
  private static final String DATABASE = "blog";
  private static final String COLLECTION = "articles";

  private final MongoClient mongoClient;

  public ArticleRepository(MongoClient mongoClient) {
    this.mongoClient = mongoClient;
  }

  private static <T> Flowable<T> publisherToFlowable(Publisher<T> publisher) {
    return Flowable.fromPublisher(publisher);
  }

  private static <T> Single<T> publisherToSingle(Publisher<T> publisher) {
    return Single.fromPublisher(publisher);
  }

  public Flowable<Article> findAllArticles() {
    return publisherToFlowable(getCollection().find());
  }

  public Single<Success> save(Article article) {
    return publisherToSingle(getCollection().insertOne(article));
  }

  public Maybe<Article> findById(String id) {
    return publisherToFlowable(getCollection().find(Filters.eq("_id", new ObjectId(id))).first())
        .firstElement();
  }

  private MongoCollection<Article> getCollection() {
    return mongoClient.getDatabase(DATABASE).getCollection(COLLECTION, Article.class);
  }

  public Single<UpdateResult> update(ObjectId objectId, Article article) {
    article.setLastUpdatedAt(new Date());
    return publisherToSingle(getCollection().replaceOne(Filters.eq("_id", objectId), article));
  }

  public Maybe<DeleteResult> deleteById(String id) {
    return publisherToFlowable(getCollection().deleteOne(Filters.eq("_id", new ObjectId(id))))
        .firstElement();
  }
}
