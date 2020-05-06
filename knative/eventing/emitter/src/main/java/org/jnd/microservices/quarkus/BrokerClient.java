package org.jnd.microservices.quarkus;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;


@Path("/")
@RegisterRestClient
public interface BrokerClient {

    @POST
    @Produces("text/plain")
    String send(String data);
}