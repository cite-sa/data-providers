package gr.cite.oaipmh.test;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.core.UriInfo;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import gr.cite.oaipmh.repository.RepositoryResponse;

public class RepositoryResponseTest {

	@Test
	public void testRepositoryResponse() throws URISyntaxException {
		UriInfo mockUriInfo1 = Mockito.mock(UriInfo.class);
		Mockito.when(mockUriInfo1.getRequestUri()).thenReturn(new URI("http://www.test.com/go1?test=mpla1"));
		/*String response1 = RepositoryResponse.request(mockUriInfo1).response().build();*/
		
		UriInfo mockUriInfo2 = Mockito.mock(UriInfo.class);
		Mockito.when(mockUriInfo2.getRequestUri()).thenReturn(new URI("http://www.test.com/go2?test=mpla2"));
		/*String response2 = RepositoryResponse.request(mockUriInfo2).response().build();*/
		
		/*System.out.println(response1);
		System.out.println(response2);*/
	}
}
