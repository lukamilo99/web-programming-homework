package raf.rs;

import com.mongodb.client.*;
import org.bson.Document;

public class Database {

    private static MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");

    private MongoDatabase mongoDatabase;

    public Database(){
        mongoDatabase = mongoClient.getDatabase("Messages");
    }

    public void addMessage(String message){
        String[] parts = message.split(" ");

        StringBuilder content = new StringBuilder();

        for (int i = 1; i < parts.length - 1; i++){
            content.append(parts[i]).append(" ");
        }

        Document document = new Document();
        document.put("client", parts[0].substring(0, parts[0].length() - 1));
        document.put("content", content.toString().trim());
        document.put("time", parts[parts.length - 1]);

        mongoDatabase.getCollection("messages").insertOne(document);
    }

    public String getMessages(){
        FindIterable<Document> messages = mongoDatabase.getCollection("messages").find();//.sort(new BasicDBObject("_id",-1)).limit(100);
        StringBuilder stringBuilder = new StringBuilder();

        for (Document message: messages){
            stringBuilder.append(message.get("client")).append(": ")
                    .append(message.get("content")).append(" ")
                    .append(message.get("time")).append("\n");
        }

        return stringBuilder.deleteCharAt(stringBuilder.length() - 1).toString();
    }
}
