/*
 * JBoss, Home of Professional Open Source
 * Copyright 2015, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package rshelloworld;

import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.stream.Collectors;

/**
 * A simple REST service which is able to say hello to someone using HelloService Please take a look at the web.xml where JAX-RS
 * is enabled
 *
 * @author gbrey@redhat.com
 *
 */
@Tag(name = "OpenAPI Example", description = "Get a text in various formats")
@Path("/hello")
public class HelloWorld {
    @Inject
    HelloService helloService;

//    @PostConstruct
//    private static void postConstruct() {
//        ConfigProvider.getConfig().getConfigSources().forEach(configSource -> {
//            System.out.println("=================================");
//            System.out.println(configSource.getName());
//            System.out.println(configSource.getOrdinal());
//            System.out.println("Property names: " + configSource.getPropertyNames().stream().collect(Collectors.joining(", ")));
//        });
//    }

    @Operation(description = "Getting Hello Text")
    @APIResponse(responseCode = "200", description = "Successful, Text")
    @GET
    public String get() {
        ConfigProvider.getConfig().getConfigSources().forEach(configSource -> {
            System.out.println("=================================");
            System.out.println(configSource.getName());
            System.out.println(configSource.getOrdinal());
            System.out.println("Property names: " + configSource.getPropertyNames().stream().collect(Collectors.joining(", ")));
        });
        return "Hello";
    }

    @GET
    @Path("/json")
    @Produces({ "application/json" })
    public String getHelloWorldJSON() {
        ConfigProvider.getConfig().getConfigSources().forEach(configSource -> {
            System.out.println("=================================");
            System.out.println(configSource.getName());
            System.out.println(configSource.getOrdinal());
            System.out.println("Property names: " + configSource.getPropertyNames().stream().collect(Collectors.joining(", ")));
        });
        System.out.println("-------------------------------------");
        return "{\"result\":\"" + helloService.createHelloMessage("World") + "\"}";
    }

    @GET
    @Path("/xml")
    @Produces({ "application/xml" })
    public String getHelloWorldXML() {
        return "<xml><result>" + helloService.createHelloMessage("World") + "</result></xml>";
    }

}
