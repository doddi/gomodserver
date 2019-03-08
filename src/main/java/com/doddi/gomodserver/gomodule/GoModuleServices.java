package com.doddi.gomodserver.gomodule;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.doddi.gomodserver.Providers.Provider;
import com.doddi.gomodserver.Providers.ReleaseInfo;
import com.doddi.gomodserver.Providers.Tag;

@Path("/")
public class GoModuleServices
{
  private final Provider provider;

  public GoModuleServices(final Provider provider) {
    this.provider = provider;
  }

  @GET
  @Path("{module: .*}/@v/list")
  @Produces(MediaType.TEXT_PLAIN)
  public String getList(@PathParam("module") final String module) {
    String name = maybeRemoveGithubUrl(maybeRemoveLeadingSlash(module));

    String[] path = name.split("/");
    String owner = path[0];
    String repository = path[1];

    List<Tag> tags = provider.getTags(repository, owner);

    return tags.stream()
        .map(Tag::getName)
        .collect(Collectors.joining("\n"));
  }

  @GET
  @Path("{module: .*}/@v/{version}.info")
  @Produces(MediaType.APPLICATION_JSON)
  public VersionInfo getVersionInfo(@PathParam("module") final String module,
                                    @PathParam("version") final String version) {
    String name = maybeRemoveGithubUrl(maybeRemoveLeadingSlash(module));

    String[] path = name.split("/");
    String owner = path[0];
    String repository = path[1];

    ReleaseInfo release = provider.getRelease(repository, owner, version);

    VersionInfo versionInfo = new VersionInfo();
    versionInfo.setVersion(release.getVersion());
    versionInfo.setTime(release.getDate());
    return versionInfo;
  }

  @GET
  @Path("{module: .*}/@v/{version}.mod")
  public ByteArrayInputStream getMod(@PathParam("module") final String module,
                                     @PathParam("version") final String version) {
    String name = maybeRemoveGithubUrl(maybeRemoveLeadingSlash(module));

    String[] path = name.split("/");
    String owner = path[0];
    String repository = path[1];

    String content = provider.getContent(repository, owner, version, "go.mod");
    return new ByteArrayInputStream(content.getBytes());
  }

  @GET
  @Path("{module: .*}/@v/{version}.zip")
  public ByteArrayInputStream getZip(@PathParam("module") final String module,
                                     @PathParam("version") final String version) {
    String name = maybeRemoveGithubUrl(maybeRemoveLeadingSlash(module));

    String[] path = name.split("/");
    String owner = path[0];
    String repository = path[1];

    return provider.getZip(repository, owner, version);
  }

  private String maybeRemoveLeadingSlash(final String module) {
    String name = module;
    if (module.startsWith("/")) {
      name = module.replaceFirst("/", "");
    }
    return name;
  }

  private String maybeRemoveGithubUrl(final String module) {
    String name = module;
    if (module.startsWith("github.com")) {
      name = module.replaceFirst("github.com", "");
    }
    return name;
  }
}
