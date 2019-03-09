package com.doddi.gomodserver.controller.github;

import java.io.ByteArrayInputStream;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import com.doddi.gomodserver.controller.github.entity.GithubContent;
import com.doddi.gomodserver.controller.github.entity.GithubReleaseInfo;
import com.doddi.gomodserver.controller.github.entity.GithubTag;

import static java.lang.String.format;

public class GithubServices
{
  private static final String ENDPOINT = "https://api.github.com";

  private static final String GITHUB_ACCEPT_HEADER = "application/vnd.github.v3+json";

  private WebTarget target;

  private String authToken;

  public GithubServices(final Client client, final String authToken) {
    this.target = client.target(UriBuilder.fromPath(ENDPOINT));
    this.authToken = authToken;
  }

  public List<GithubTag> getTags(final String repository,
                                 final String owner)
  {
    Response response = target
        .path(format("/repos/%s/%s/tags", owner, repository))
        .request()
        .accept(GITHUB_ACCEPT_HEADER)
        //.header("Authorization", "Bearer " + authToken)
        .buildGet()
        .invoke();

    return response.readEntity(new GenericType<List<GithubTag>>() { });
  }

  public GithubContent getContent(final String repository,
                                  final String owner,
                                  final String filename,
                                  final String version) {
    Response response = target
        .path(format("/repos/%s/%s/contents/%s", owner, repository, filename))
        .queryParam("ref", version)
        .request()
        .accept(GITHUB_ACCEPT_HEADER)
        //.header("Authorization", "Bearer " + authToken)
        .buildGet()
        .invoke();

    return response.readEntity(GithubContent.class);
  }

  public GithubReleaseInfo getReleaseInfo(final String repository,
                                          final String owner,
                                          final String version) {
    Response response = target
        .path(format("/repos/%s/%s/releases/tags/%s", owner, repository, version))
        .request()
        .accept(GITHUB_ACCEPT_HEADER)
        //.header("Authorization", "Bearer " + authToken)
        .buildGet()
        .invoke();

    return response.readEntity(GithubReleaseInfo.class);
  }

  public ByteArrayInputStream getZip(final String repository,
                                     final String owner,
                                     final String version) {
    Response response = target
        .path(format("/repos/%s/%s/zipball/%s", owner, repository, version))
        .request()
        .accept(GITHUB_ACCEPT_HEADER)
        //.header("Authorization", "Bearer " + authToken)
        .buildGet()
        .invoke();

    return response.readEntity(ByteArrayInputStream.class);
  }
}
