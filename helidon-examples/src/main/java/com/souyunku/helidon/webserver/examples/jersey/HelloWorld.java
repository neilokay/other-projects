package com.souyunku.helidon.webserver.examples.jersey;

import io.helidon.webserver.ServerRequest;
import io.helidon.webserver.ServerResponse;
import io.helidon.webserver.jersey.JerseySupport;
import io.opentracing.SpanContext;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.stream.Collectors;

@Path("/")
public class HelloWorld {

    @Inject
    private ServerRequest request;

    @Inject
    private ServerResponse response;


    @Inject
    @Named(JerseySupport.REQUEST_SPAN_CONTEXT)
    private SpanContext spanContext;


    @GET
    @Path("hello")
    public Response hello() {
        return Response.ok("Hello World !").build();
    }


    @POST
    @Path("hello")
    public Response hello(String content) {
        return Response.accepted("Hello: " + content + "!").build();
    }

    @POST
    @Path("content")
    public Response content(String content) {
        return Response.accepted(content).build();
    }

    @GET
    @Path("injection")
    public Response webServerInjection() {
        return Response.ok("request=" + request.getClass().getName()
                + "\nresponse=" + response.getClass().getName()
                + "\nspanContext=" + spanContext.getClass().getName()).build();
    }

    @GET
    @Path("headers")
    public Response headers(@Context HttpHeaders headers, @QueryParam("header") String header) {
        return Response.ok("headers=" + headers.getRequestHeader(header).stream().collect(Collectors.joining(",")))
                .build();
    }

    @GET
    @Path("query")
    public Response query(@QueryParam("a") String a, @QueryParam("b") String b) {
        return Response.accepted("a='" + a + "';b='" + b + "'").build();
    }

    @GET
    @Path("path/{num}")
    public Response path(@PathParam("num") String num) {
        return Response.accepted("num=" + num).build();
    }

    @GET
    @Path("requestUri")
    public String getRequestUri(@Context UriInfo uriInfo) {
        return uriInfo.getRequestUri().getPath();
    }
}
