package com.humberco.transpojo.dao;

import com.humberco.transpojo.model.Transaction;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TransDAO extends AbstractDao {

    public static String TRANS_COLLECTION = "transaction";

    private MongoCollection<Document> transactionCollection;

    @Autowired
    public TransDAO(MongoClient mongoClient, @Value("${spring.mongodb.database}") String databaseName) {
        super(mongoClient, databaseName);
        transactionCollection = db.getCollection(TRANS_COLLECTION);
    }

    private Bson buildLookupStage(){
        return null;
    }

    public void addTransaction(Transaction trans){
        Document thisTransactionDocument = trans.bsonFromPojo();
        transactionCollection.insertOne(thisTransactionDocument);
    }
}
