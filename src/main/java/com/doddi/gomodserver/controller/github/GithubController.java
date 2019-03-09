package com.doddi.gomodserver.controller.github;

import java.io.InputStream;
import java.net.URI;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.client.Client;

import com.doddi.gomodserver.controller.Controller;
import com.doddi.gomodserver.controller.ReleaseInfo;
import com.doddi.gomodserver.controller.github.entity.GithubContent;
import com.doddi.gomodserver.controller.github.entity.GithubRelease;
import com.doddi.gomodserver.controller.github.entity.GithubReleaseInfo;

public class GithubController
    implements Controller
{
  public static final String GITHUB = "github.com";

  private GithubServices service;

  public GithubController(final Client client, final String authToken) {
    service = new GithubServices(client, authToken);
  }

  @Override
  public String name() {
    return GITHUB;
  }

  @Override
  public List<ReleaseInfo> getReleases(final URI url) {
    String[] split = url.getPath().split("/");
    String repository = split[2];
    String owner = split[1];

    List<GithubRelease> response = service.getReleases(repository, owner);

    return response.stream()
        .map(release -> new ReleaseInfo(release.getTag_name(), release.getPublished_at()))
        .collect(Collectors.toList());
  }

  @Override
  public ReleaseInfo getRelease(final URI url, final String version) {
    String[] split = url.getPath().split("/");
    String repository = split[2];
    String owner = split[1];

    GithubReleaseInfo gitHubReleaseInfo = service.getReleaseInfo(repository, owner, version);

    ReleaseInfo releaseInfo = new ReleaseInfo(gitHubReleaseInfo.getTag_name(), gitHubReleaseInfo.getPublished_at());
    return releaseInfo;
  }

  @Override
  public String getContent(final URI url, final String ref, final String filename) {
    String[] split = url.getPath().split("/");
    String repository = split[2];
    String owner = split[1];

    GithubContent content = service.getContent(repository, owner, filename, ref);

    String encoded = content.getContent().replaceAll("\n", "");
    Decoder decoder = Base64.getDecoder();
    return new String(decoder.decode(encoded));
  }

  @Override
  public InputStream getZip(final URI url, final String version) {
    String[] split = url.getPath().split("/");
    String repository = split[2];
    String owner = split[1];

    return service.getZip(repository, owner, version);
  }
}
