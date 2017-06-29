package okeanos.restApp.resources;

import org.junit.*;
import spark.Spark;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import java.net.URI;

import static org.junit.Assert.*;

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

    @Test
    public void getAnAccount() throws Exception {
        Client client = ClientBuilder.newBuilder().build();
        Response response = client.target(URI.create("http://localhost:4321"+AccountResource.ressourcePath))
                .request()
                .get();
        assertEquals(200, response.getStatus());



    }

}
