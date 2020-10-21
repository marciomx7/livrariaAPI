package br.com.metrocamp.livraria.view;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.metrocamp.livraria.util.url.URLUtils;

@WebServlet("/login/")
public class Login extends HttpServlet{
	
	public Login() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		
		String URI = URLUtils.getContextURL(request.getRequestURI());

		switch (URI) {
		case "login/admin":
			
			break;

		default:
			break;
		}
	}

	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		
		
		
		
		
	}
}
