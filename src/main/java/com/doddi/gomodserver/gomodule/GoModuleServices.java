package com.doddi.gomodserver.gomodule;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.doddi.gomodserver.controller.MappingController;
import com.doddi.gomodserver.controller.ReleaseInfo;
import com.doddi.gomodserver.controller.Tag;

@Path("/")
public class GoModuleServices
{
  private final MappingController controller;

  public GoModuleServices(final MappingController controller) {
    this.controller = controller;
  }

  @GET
  @Path("{module: .*}/@v/list")
  @Produces(MediaType.TEXT_PLAIN)
  public String getList(@PathParam("module") final String module) {
    List<Tag> tags = controller.getTags(module);

    return tags.stream()
        .map(Tag::getName)
        .collect(Collectors.joining("\n"));
  }

  @GET
  @Path("{module: .*}/@v/{version}.info")
  @Produces(MediaType.APPLICATION_JSON)
  public VersionInfo getVersionInfo(@PathParam("module") final String module,
                                    @PathParam("version") final String version) {
    ReleaseInfo release = controller.getRelease(module, version);

    VersionInfo versionInfo = new VersionInfo();
    versionInfo.setVersion(release.getVersion());
    versionInfo.setTime(release.getDate());
    return versionInfo;
  }

  @GET
  @Path("{module: .*}/@v/{version}.mod")
  public ByteArrayInputStream getMod(@PathParam("module") final String module,
                                     @PathParam("version") final String version) {
    String content = controller.getContent(module, version, "go.mod");
    return new ByteArrayInputStream(content.getBytes());
  }

  @GET
  @Path("{module: .*}/@v/{version}.zip")
  public ByteArrayInputStream getZip(@PathParam("module") final String module,
                                     @PathParam("version") final String version) {
    return controller.getZip(module, version);
  }
}
