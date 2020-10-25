package com.bbtutorials.echo;

import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.bbtutorials.echo.model.Livros;
import com.bbtutorials.echo.morphia.LivrosController;
import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

@RestController
public class EchoController {
	
	@GetMapping("/app/list")
    public String ListAll() {
		
		String json = "";	
		
		MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
		MongoDatabase database = mongoClient.getDatabase("Livraria");

		Morphia morphia = new Morphia();
		morphia.mapPackage("com.bbtutorials.echo.model");

		final Datastore datastore = morphia.createDatastore(new MongoClient(), "Livraria");
		datastore.ensureIndexes();

		MongoCollection<Document> livros = database.getCollection("Livros");

		List<Livros> list = LivrosController.getAllDocuments(livros);

		mongoClient.close();

		json = new Gson().toJson(list);			
		
		 return json;
    }
	
	@GetMapping("/app/livro/{name}")
    public String ListOne(@PathVariable String name) {
		
		String json = "";	
		
		MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
		MongoDatabase database = mongoClient.getDatabase("Livraria");

		Morphia morphia = new Morphia();
		morphia.mapPackage("br.com.botcity.screenshotupload.model");

		final Datastore datastore = morphia.createDatastore(new MongoClient(), "Livraria");
		datastore.ensureIndexes();

		MongoCollection<Document> livros = database.getCollection("Livros");
		
		Bson query = Filters.eq("categoria", name);
		
		List<Livros> list = LivrosController.getSelectiveDocument(livros, query);

		mongoClient.close();

		json = new Gson().toJson(list);
		
		return json;
		
	}

}
