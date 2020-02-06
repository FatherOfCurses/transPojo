import static org.assertj.core.api.Assertions.assertThat;

import com.google.gson.Gson;
import com.humberco.transpojo.Application;
import com.humberco.transpojo.model.Transaction;
import com.humberco.transpojo.helper.TestHelper;
import com.mongodb.*;

import com.mongodb.client.MongoCollection;
import com.mongodb.util.JSON;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes= Application.class)
@DataMongoTest
@ExtendWith(SpringExtension.class)
@DirtiesContext
public class CrudTest {

    @DisplayName("given object to save"
            + " when save object using MongoDB template"
            + " then object is saved")
    @Test
    public void itShouldWriteAndRetrieveAnObject (@Autowired MongoTemplate mongoTemplate) {

        TestHelper testHelper = new TestHelper();
        Transaction oneTransaction = testHelper.createOneTransaction();
        MongoCollection<Document> collection = mongoTemplate.createCollection("transaction");
        Gson gson = new Gson();
        // given
        BasicDBObject objectToSave = (BasicDBObject) JSON.parse(gson.toJson(oneTransaction));

        // when
        mongoTemplate.save(objectToSave, "transaction");
        ObjectId id = (ObjectId)objectToSave.get("_id");
        Transaction actualTransaction = mongoTemplate.findById(id,Transaction.class, "transaction");

        // then
        assertThat(actualTransaction == oneTransaction);
    }
}

