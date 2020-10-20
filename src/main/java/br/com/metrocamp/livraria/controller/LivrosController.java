package br.com.metrocamp.livraria.controller;

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

import br.com.metrocamp.livraria.model.Livros;



public interface LivrosController {
	
    // Fetching all documents from the mongo collection.
	static List<Livros> getAllDocuments(MongoCollection<Document> col) {
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
    static List<Livros> getSelectiveDocument(MongoCollection<Document> col, Bson query) {
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
	
    static List<Livros> listDocToListModel(List<Document> listDoc) {
    	List<Livros> ret = new ArrayList<Livros>();
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
	
	static Livros docToModel(Document doc) {
		System.out.println(doc.toJson());
		Morphia morphia = new Morphia();		
		morphia.mapPackage("br.com.metrocamp.livraria.model");

		final Datastore datastore = morphia.createDatastore(new MongoClient(), "Livraria");
		datastore.ensureIndexes();
		
		JSONObject obj = new JSONObject(doc.toJson());
		
		Livros livro = new Livros(obj.getInt("_id"),obj.getString("titulo"));

		datastore.save(livro);
		
		return livro;
	}
}


