package com.sebastian_daschner.maven_test.a;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("a")
public class AResource {

    @Inject
    ABean a;

    @GET
    public String get() {
        return a.get() + " bez parametru";
    }

    @GET
    @Path("{id}")
    public String get(@PathParam("id") String id) {
        return a.get(id).getA() + " s parametrem";
    }

}
