package br.com.botcity.screenshotupload.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONObject;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import br.com.botcity.screenshotupload.model.AgentError;
import br.com.botcity.screenshotupload.control.AgentController;


@MultipartConfig
@WebServlet("/agentError")
public class AgentErrorServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("teste");
		Integer id = Integer.parseInt(request.getParameter("id"));
	    
		getImage(response, request, id);
		
    }

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		Collection<Part> parts = request.getParts();

		MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
		MongoDatabase database = mongoClient.getDatabase("botcity_platform");
		
		Morphia morphia = new Morphia();		
		morphia.mapPackage("br.com.botcity.screenshotupload.model");
		
		final Datastore datastore = morphia.createDatastore(new MongoClient(), "botcity_platform");

		datastore.ensureIndexes();

		for (Part p : parts) {
			
			MongoCollection<Document> seqCollection = database.getCollection("counter");
			Integer id = getId(seqCollection, new Document());
			
			Integer taskId = Integer.parseInt(request.getParameter("taskID"));
			String message = request.getParameter("message");
			String ext = request.getParameter("ext");
			
			if (p.getContentType() != null) {
		
				// Write file on disk

				InputStream fileContent = p.getInputStream();
				FileOutputStream out = new FileOutputStream(new File("/botcity/error/" + id + ext));
				int read = 0;
				byte[] bytes = new byte[1024];

				while ((read = fileContent.read(bytes)) != -1) {
					out.write(bytes, 0, read);
				}
				
				out.close();
			}
			
			AgentError err = new AgentError(id,taskId, message);
			datastore.save(err);

		}

		mongoClient.close();

	}
	
	public HttpServletResponse getImage(HttpServletResponse response, HttpServletRequest request, Integer id) throws IOException {

        File file = new File("c:/botcity/error/" + id + ".jpeg");
        FileInputStream in = new FileInputStream(file);
        OutputStream out = response.getOutputStream();

        byte[] buf = new byte[1024];
        int count = 0;

        while ((count = in.read(buf)) >= 0) {
            out.write(buf, 0, count);
        }
        out.flush();
        in.close();
        return response;       

	}
	
	public int getId(MongoCollection<Document> seqCollection, Document doc) {
		
		doc = seqCollection.findOneAndUpdate(new Document("_id", "error"), new Document("$inc", new Document("seq", 1)));
		
		return doc.getDouble("seq").intValue();
		
		
	}

}
