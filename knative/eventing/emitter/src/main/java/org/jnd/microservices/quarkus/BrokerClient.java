package org.jnd.microservices.quarkus;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.POST;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;


@Path("/")
@RegisterRestClient
public interface BrokerClient {

    // -H "content-type: application/json"
    // -H "ce-specversion: 1.0"
    // -H "ce-source: curl-command"
    // -H "ce-type: curl.demo"
    // -H "ce-id: 123-abc"
    
    @POST
    @Produces("application/json")
    String send(String data,
            @HeaderParam("content-type") String contenttype,
            @HeaderParam("ce-specversion") String specversion,
            @HeaderParam("ce-source") String source,
            @HeaderParam("ce-type") String type,
            @HeaderParam("ce-id") String id);
}