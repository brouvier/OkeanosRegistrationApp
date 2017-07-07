package okeanos.restApp.resources;

import static org.junit.Assert.assertEquals;

import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Before;

import spark.Spark;

public class AccountResourceTest {

	@Before
	public void init() throws Exception {
		Spark.ipAddress("localhost");
		Spark.port(4321);
		Spark.init();

		new AccountResource();
		Spark.awaitInitialization();
	}

	@After
	public void tearDown() throws Exception {
		Spark.stop();
	}

	// @Test
	public void getAnAccount() throws Exception {
		Client client = ClientBuilder.newBuilder().build();
		Response response = client.target(URI.create("http://localhost:4321" + AccountResource.ressourcePath)).request()
				.get();
		assertEquals(200, response.getStatus());

	}

}
