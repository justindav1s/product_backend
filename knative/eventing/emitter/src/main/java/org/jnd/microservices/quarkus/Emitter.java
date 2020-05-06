package org.jnd.microservices.quarkus;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Request;

import io.quarkus.runtime.StartupEvent;
import io.quarkus.runtime.configuration.ProfileManager;

@Path("/emit")
public class Emitter {

    private static final Logger log = Logger.getLogger(Emitter.class.getName());

    @Inject
    @RestClient
    BrokerClient brokerClient;

    void onStart(@Observes StartupEvent ev) {
        log.info("The application is starting with profile : " + ProfileManager.getActiveProfile());
    }

    @GET
    @Path("/health")
    @Produces(MediaType.TEXT_PLAIN)
    public String health() {
        return "OK";
    }

    @GET
    @Path("/broker/{data}")
    @Produces(MediaType.APPLICATION_JSON)
    public String echo(@PathParam("data") String data, @Context UriInfo uriInfo, @Context Request req) {

        log.debug("Attempting to send to Broker");
        log.info(req.getMethod()+" "+uriInfo.getRequestUri());

        String response = brokerClient.send(data);

        return response;
    }



}