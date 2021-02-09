package com.webservices.json;


import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.lang.annotation.Documented;
import java.net.UnknownHostException;

public class NamesMain {
    public static void main(String[] args) throws UnknownHostException {

//        MongoClient mongoClient = new MongoClient("localhost");
//        DB database = mongoClient.getDB("myDB");
////       / database.createCollection("customers", null);
////        database.getCollectionNames().forEach(System.out::println);
//
//        DBCollection collection = database.getCollection("names");
//
//        BasicDBObject document = new BasicDBObject();
//        document.put("FirstName", "Snurre");
////        document.put("LastName", "Pig");
////        collection.insert(document);
//
//        DBCursor cursor = collection.find();
//        while (cursor.hasNext()) {
//            DBObject obj = cursor.next();
//            System.out.println(obj);

            MongoClient mongoClient = new MongoClient();
            MongoDatabase db = mongoClient.getDatabase("myDB");
            MongoCollection collection = db.getCollection("names");
            BasicDBObject query = new BasicDBObject("FirstName", "Kalle");
            try (MongoCursor<Document> cursor = collection.find(query).iterator()) {
                while(cursor.hasNext()) {
                    System.out.println(cursor.next());
                }
            }
        }


//        BasicDBObject searchQuery = new BasicDBObject();
//        searchQuery.put("FirstName", "Kalle");
//        DBCursor cursor = collection.find(searchQuery);
//
//        while (cursor.hasNext()) {
//            System.out.println(cursor.next());
//        }



}
