package br.com.botcity.screenshotupload.view;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;


/**
 * Servlet implementation class ListError
 */
@WebServlet("/listError")
public class ListAgentError extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    // one instance, reuse
    private final CloseableHttpClient httpClient = HttpClients.createDefault();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListAgentError() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		Integer taskID = Integer.parseInt(request.getParameter("task"));
		
		MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
		MongoDatabase database = mongoClient.getDatabase("botcity_platform");
				
		MongoCollection<Document> errorCollection = database.getCollection("agentError");
		
		Bson query = Filters.eq("taskID", taskID);
		
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE html>");
		out.println("<html xmlns='http://www.w3.org/1999/xhtml'>");
	
		List<AgentError> listError = AgentController.getSelectiveDocument(errorCollection, query);	
		out.println("TaskID: " + taskID);
		for (int a=0; a<listError.size(); a++) {			
			AgentError err = listError.get(a);
			out.print("<br>");
			out.println("\n ID: " + err.getId()+ "\n");
			try {
				//out.append((getImage(err.getId());
				//getImage(err.getId());
				out.println("<img src=\""
						+ "http://localhost:8080/ScreenshotUpload/agentError?id=" +err.getId()+"\" width=\"300\" height=\"300\"/>" 
						+ "\n");

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			out.print("<br>");
		}		
		
	    mongoClient.close();
	    		
	}
	
	   private void getImage(Integer id) throws Exception {

	        HttpGet request = new HttpGet("http://localhost:8080/ScreenshotUpload/agentError?id=" + id);

	        // add request headers
	        request.addHeader("custom-key", "mkyong");
	        request.addHeader(HttpHeaders.USER_AGENT, "Googlebot");

	        try (CloseableHttpResponse response = httpClient.execute(request)) {

	            // Get HttpResponse Status
	            System.out.println(response.getStatusLine().toString());

	            HttpEntity entity = response.getEntity();
	            Header headers = entity.getContentType();
	            System.out.println(headers);

	            if (entity != null) {
	                // return it as a String
	                String result = EntityUtils.toString(entity);
	                System.out.println(result);
	            }

	        }

	    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
