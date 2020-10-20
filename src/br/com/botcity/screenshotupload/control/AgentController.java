package br.com.botcity.screenshotupload.control;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONObject;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import br.com.botcity.screenshotupload.model.AgentError;



public interface AgentController {
	
    // Fetching all documents from the mongo collection.
	@SuppressWarnings("unused")
	static List<AgentError> getAllDocuments(MongoCollection<Document> col) {
        System.out.println(("Fetching all documents from the collection"));
 
    	// Performing a read operation on the collection.    	
        FindIterable<Document> fi = col.find();      
        MongoCursor<Document> cursor = fi.iterator();
        try {
			return listDocToListModel(ToListDoc(cursor));
		}
		finally {
			cursor.close();
		}
    }
 
	
    // Fetch a selective document from the mongo collection.
    static List<AgentError> getSelectiveDocument(MongoCollection<Document> col, Bson query) {
    	System.out.println(("Fetching a particular document from the collection"));
    	
    	// Performing a read operation on the collection.    	
        FindIterable<Document> fi = col.find(query);      
        MongoCursor<Document> cursor = fi.iterator();
        try {
			return listDocToListModel(ToListDoc(cursor));
		}
		finally {
			cursor.close();
		}
    }
	
    static List<AgentError> listDocToListModel(List<Document> listDoc) {
    	List<AgentError> ret = new ArrayList<AgentError>();
		for(Document doc:listDoc) {
			ret.add(docToModel(doc));
		}
		return ret;
	}
    	

	static List<Document> ToListDoc(MongoCursor<Document> cursor) {
		List<Document> listErrors = new ArrayList<Document>();
		while(cursor.hasNext()) {
			Document doc = cursor.next();
			listErrors.add(doc);	
			if (cursor.hasNext()) {
				continue;
			}else {
				break;
			}
		}		
		return listErrors;
	}    
	
	static AgentError docToModel(Document doc) {
		System.out.println(doc.toJson());
		Morphia morphia = new Morphia();		
		morphia.mapPackage("br.com.botcity.screenshotuplyoad.model");

		final Datastore datastore = morphia.createDatastore(new MongoClient(), "botcity_platform");
		datastore.ensureIndexes();
		
		JSONObject obj = new JSONObject(doc.toJson());
		
		AgentError err = new AgentError(obj.getInt("_id"), obj.getInt("taskID"), obj.getString("message"));
		
		datastore.save(err);
		
		return err;
	}
}


