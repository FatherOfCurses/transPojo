package com.humberco.transpojo.model;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class Transaction implements Serializable {

    @Id
    String transid;
    String starttime;
    String endtime;
    int translength;
    String result;
    int exceptionreason;
    int client;

    public Document bsonFromPojo() {
        Document document = new Document();
        document.put("transid", this.transid);
        document.put("starttime", this.starttime);
        document.put("endtime", this.endtime);
        document.put("translength", this.translength);
        document.put("result", this.result);
        document.put("exceptionreason", this.exceptionreason);
        document.put("client", this.client);
        return document;
    }

    public void makePojoFromBson(DBObject bson) {
        BasicDBObject b = (BasicDBObject) bson;
        this.starttime = (String) b.get("starttime");
        this.endtime = (String) b.get("endtime");
        this.translength = (Integer) b.get("translength");
        this.result = (String) b.get("result");
        this.exceptionreason = (Integer) b.get("exceptionreason");
        this.client = (Integer) b.get("client");

    }
}
