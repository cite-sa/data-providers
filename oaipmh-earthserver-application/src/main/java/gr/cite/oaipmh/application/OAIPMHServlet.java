/*package gr.cite.oaipmh.application;

import gr.uoa.di.madgik.basexLibrary.basexconnector.DBConnector.NoConnectionAvailableException;
import gr.uoa.di.madgik.basexLibrary.collections.Collection.CollectionException;
import gr.cite.oaipmh.application.femme.WCSCatalogRepository;
import gr.cite.oaipmh.repository.RepositoryConnectionFactory;
import gr.cite.oaipmh.repository.RepositoryRegistrationException;
import gr.cite.oaipmh.verbs.Verb;
import gr.cite.oaipmh.verbs.VerbFactory;
import gr.cite.oaipmh.verbs.errors.BadArgumentError;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

*//**
 * 
 * @author Ioannis Kavvouras
 * 
 *//*
@WebServlet("/OAIPMHServlet")
public class OAIPMHServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	*//**
	 * @see HttpServlet#HttpServlet()
	 *//*
	public OAIPMHServlet() {
		try {
			RepositoryConnectionFactory
					.registerRepository(new WCSCatalogRepository());
		} catch (NoConnectionAvailableException e) {
			e.printStackTrace();
		} catch (CollectionException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	*//**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 *//*
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	*//**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 *//*
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			Verb verb = VerbFactory.getVerbFactoryMethod(request
					.getParameter("verb"));
			verb.setAttributes(request);
			response.getWriter().print(verb.response());
			try {
				RepositoryConnectionFactory.getRepository().closeConnection();
			} catch (RepositoryRegistrationException e) {
				e.printStackTrace();
			}
		} catch (BadArgumentError e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,
					"Bad argument error, verb is not supported or it is not specified.");
		}
	}

}
*/