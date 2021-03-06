import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ReadPreference;
import com.mongodb.client.*;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.lang.System.getProperty;

/**
 * @see com.mongodb.client.MongoClients
 * @see com.mongodb.client.MongoIterable
 * @see com.mongodb.ReadPreference
 * @see org.bson.codecs.Codec
 */

@SpringBootTest
public class BasicMongoValidation extends BaseTestProperties{

  String uri = getProperty("spring.mongodb.uri");
  MongoClient mongoClient = MongoClients.create(uri);
  MongoDatabase database = mongoClient.getDatabase(getProperty("spring.mongodb.database"));
  MongoCollection<Document> collection = database.getCollection(getProperty("spring.mongodb.collection"));
  private Document document;

  public BasicMongoValidation() throws IOException {
  }

  @Test
  public void MongoClientInstance() {

    ConnectionString connectionString = new ConnectionString(uri);
    MongoClientSettings clientSettings =
        MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            .applicationName("transpojo")
            .applyToConnectionPoolSettings(
                builder -> builder.maxWaitTime(1000, TimeUnit.MILLISECONDS))
            .build();

    mongoClient = MongoClients.create(clientSettings);

    Assert.assertNotNull(mongoClient);
  }

  @Test
  public void MongoDatabaseInstance() {

    MongoIterable<String> databaseIterable = mongoClient.listDatabaseNames();
    List<String> dbnames = new ArrayList<>();
    for (String name : databaseIterable) {
      System.out.println(name);
      dbnames.add(name);
    }

    Assert.assertTrue(dbnames.contains("trans"));
    ReadPreference readPreference = database.getReadPreference();
    Assert.assertEquals("primary", readPreference.getName());
  }
  @Test
  public void MongoCollectionInstance() {

    MongoIterable<Document> cursor = collection.find().skip(10).limit(20);

    List<Document> documents = new ArrayList<>();
    Assert.assertEquals(20, cursor.into(documents).size());
  }

  @Test
  public void DocumentInstance() {
    document = new Document("name", new Document("first", "Norberto").append("last", "Leite"));
    collection.insertOne(document);

    Assert.assertTrue(document instanceof Bson);

  }
}
