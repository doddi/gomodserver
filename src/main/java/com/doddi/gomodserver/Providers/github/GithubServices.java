package com.doddi.gomodserver.Providers.github;

import java.io.ByteArrayInputStream;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/repos")
public interface GithubServices
{
  @GET
  @Path("/{owner}/{repository}/tags")
  @Produces({MediaType.APPLICATION_JSON})
  List<GithubTag> getTags(@PathParam("repository") final String repository,
                          @PathParam("owner") final String owner,
                          @HeaderParam("Accept") final String acceptHeader,
                          @HeaderParam("Authorization") final String authorization);

  @GET
  @QueryParam("ref")
  @Path("/{owner}/{repository}/contents/{filename}")
  @Produces({MediaType.APPLICATION_JSON})
  GithubContent getContent(@PathParam("repository") final String repository,
                           @PathParam("owner") final String owner,
                           @PathParam("filename") final String filename,
                           @QueryParam("ref") final String ref,
                           @HeaderParam("Accept") final String acceptHeader,
                           @HeaderParam("Authorization") final String authorization);

  @GET
  @Path("/{owner}/{repository}/releases/tags/{version}")
  @Produces({MediaType.APPLICATION_JSON})
  GithubReleaseInfo getReleaseInfo(@PathParam("repository") final String repository,
                                   @PathParam("owner") final String owner,
                                   @PathParam("version") final String version,
                                   @HeaderParam("Accept") final String acceptHeader,
                                   @HeaderParam("Authorization") final String authorization);

  @GET
  @Path("/{owner}/{repository}/zipball/{version}")
  @Produces({MediaType.APPLICATION_OCTET_STREAM})
  ByteArrayInputStream getZip(@PathParam("repository") final String repository,
                              @PathParam("owner") final String owner,
                              @PathParam("version") final String version,
                              @HeaderParam("Accept") final String acceptHeader,
                              @HeaderParam("Authorization") final String authorization);
}
