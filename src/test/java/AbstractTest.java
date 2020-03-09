import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.io.IOException;

public abstract class AbstractTest extends BaseTestProperties {
  protected MongoDatabase db;
  protected MongoDatabase testDb;
  protected MongoCollection<Document> moviesCollection;

  public AbstractTest() {
    try {
      String mongoUri = getProperty("spring.mongodb.uri");
      String databaseName = getProperty("spring.mongodb.database");
      db = MongoClients.create(mongoUri).getDatabase(databaseName);

    } catch (IOException e) {
      this.db = null;
    }
  }
}
