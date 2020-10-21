package br.com.metrocamp.livraria.view;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import br.com.metrocamp.livraria.controller.LivrosController;
import br.com.metrocamp.livraria.model.Livros;
import br.com.metrocamp.livraria.util.server.ServerUtils;
import br.com.metrocamp.livraria.util.url.URLUtils;

@WebServlet("/livraria/*")
public class LivrariaServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("teste");

		String URI = URLUtils.getContextURL(request.getRequestURI());

		switch (URI) {
		case "/livraria/list":
			list(response);
			break;
		case "/livraria/livro":
			String categoria = request.getParameter("cat");			
			listOne(categoria, response);		
			break;
		}
	}
	
	@Override
    protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
 
        res.setHeader("Access-Control-Allow-Origin", req.getHeader("origin"));
        res.setHeader("Access-Control-Allow-Headers", "Authorization"); 

        super.service(req, res);
    }

	private void listOne(String categoria, HttpServletResponse response) {
		System.out.println("Categoria : " +categoria);
	    
		MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
		MongoDatabase database = mongoClient.getDatabase("Livraria");

		Morphia morphia = new Morphia();
		morphia.mapPackage("br.com.botcity.screenshotupload.model");

		final Datastore datastore = morphia.createDatastore(new MongoClient(), "Livraria");
		datastore.ensureIndexes();

		MongoCollection<Document> livros = database.getCollection("Livros");
		
		Bson query = Filters.eq("categoria", categoria);
		
		List<Livros> list = LivrosController.getSelectiveDocument(livros, query);

		mongoClient.close();

		String json = new Gson().toJson(list);

		//ServerUtils.responseText(json, response, HttpServletResponse.SC_OK);
		ServerUtils.responseJson(json, response, HttpServletResponse.SC_OK);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Post errado");
		
		Collection<Part> parts = request.getParts();		

		String URI = URLUtils.getContextURL(request.getRequestURI());

		switch (URI) {
		case "/livraria/login":
			System.out.println("Login realizado com sucesso");
		}
	}
	
	    


	private void adicionaLivro(HttpServletResponse response) {
//		
//		MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
//		MongoDatabase database = mongoClient.getDatabase("Livraria");
//	
//		Morphia morphia = new Morphia();		
//		morphia.mapPackage("br.com.botcity.screenshotupload.model");
//	
//		final Datastore datastore = morphia.createDatastore(new MongoClient(), "Livraria");
//		datastore.ensureIndexes();				
//		
//		Livros livro = new Livros("");
//		 
//		datastore.save(livro);
	}
	

	private void removerLivro(HttpServletResponse response) {
		
		MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
		MongoDatabase database = mongoClient.getDatabase("Livraria");
	
		Morphia morphia = new Morphia();		
		morphia.mapPackage("br.com.botcity.screenshotupload.model");
	
		final Datastore datastore = morphia.createDatastore(new MongoClient(), "Livraria");
		datastore.ensureIndexes();		
				
		Query<Livros> query = datastore.createQuery(Livros.class)
				  .field("titulo")
				  .contains("A RAINHA DE NEVE");
				 
	    datastore.delete(query);
				 
	}

	private void list(HttpServletResponse response) {

		MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
		MongoDatabase database = mongoClient.getDatabase("Livraria");

		Morphia morphia = new Morphia();
		morphia.mapPackage("br.com.botcity.screenshotupload.model");

		final Datastore datastore = morphia.createDatastore(new MongoClient(), "Livraria");
		datastore.ensureIndexes();

		MongoCollection<Document> livros = database.getCollection("Livros");

		List<Livros> list = LivrosController.getAllDocuments(livros);

		mongoClient.close();

		String json = new Gson().toJson(list);

		ServerUtils.responseText(json, response, HttpServletResponse.SC_OK);
	}

	public int getId(MongoCollection<Document> seqCollection, Document doc) {

		doc = seqCollection.findOneAndUpdate(new Document("_id", "product_id"),
				new Document("$inc", new Document("seq", 1)));

		return doc.getDouble("seq").intValue();

	}

	public int diminuiEstoque(MongoCollection<Document> seqCollection, Document doc) {

		doc = seqCollection.findOneAndUpdate(new Document("_id", "productid"),
				new Document("$inc", new Document("seq", 1)));

		return doc.getDouble("seq").intValue();

	}

	public int adicionaLivro(MongoCollection<Document> seqCollection, Document doc) {

		doc = seqCollection.findOneAndUpdate(new Document("_id", "productid"),
				new Document("$inc", new Document("seq", 1)));

		return doc.getDouble("seq").intValue();

	}

	public int removeLivro(MongoCollection<Document> seqCollection, Document doc) {

		doc = seqCollection.findOneAndUpdate(new Document("_id", "productid"),
				new Document("$inc", new Document("seq", 1)));

		return doc.getDouble("seq").intValue();

	}

}
