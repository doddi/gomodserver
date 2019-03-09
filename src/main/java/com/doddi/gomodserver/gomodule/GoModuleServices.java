package com.doddi.gomodserver.gomodule;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.doddi.gomodserver.controller.MappingController;
import com.doddi.gomodserver.controller.ReleaseInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/")
public class GoModuleServices
{
  private static Logger logger = LoggerFactory.getLogger(GoModuleServices.class);

  private final MappingController controller;

  public GoModuleServices(final MappingController controller) {
    this.controller = controller;
  }

  @GET
  @Path("{module: .*}/@v/list")
  @Produces(MediaType.TEXT_PLAIN)
  public String getList(@PathParam("module") final String module) {
    logger.info("getting list for {}", module);

    return  controller.getReleases(module);
  }

  @GET
  @Path("{module: .*}/@latest")
  @Produces(MediaType.APPLICATION_JSON)
  public VersionInfo getLatestVersionInfo(@PathParam("module") final String module) {
    logger.info("getting info for 'latest' {}", module);

    return controller.getLatestRelease(module);
  }

  @GET
  @Path("{module: .*}/@v/{version}.info")
  @Produces(MediaType.APPLICATION_JSON)
  public VersionInfo getVersionInfo(@PathParam("module") final String module,
                                    @PathParam("version") final String version) {
    logger.info("getting info for {} version {}", module, version);

    ReleaseInfo release = controller.getRelease(module, version);

    return new VersionInfo(release.getVersion(), release.getDate());
  }

  @GET
  @Path("{module: .*}/@v/{version}.mod")
  public ByteArrayInputStream getMod(@PathParam("module") final String module,
                                     @PathParam("version") final String version) {
    logger.info("getting go.mod for {} version {}", module, version);

    String content = controller.getContent(module, version, "go.mod");
    return new ByteArrayInputStream(content.getBytes());
  }

  @GET
  @Path("{module: .*}/@v/{version}.zip")
  @Produces("application/zip")
  public InputStream getZip(@PathParam("module") final String module,
                            @PathParam("version") final String version) {
    logger.info("getting zipball for {} version {}", module, version);

    return controller.getZip(module, version);
  }
}
