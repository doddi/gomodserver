package com.doddi.gomodserver.controller.github;

import java.io.ByteArrayInputStream;
import java.net.URI;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.client.Client;

import com.doddi.gomodserver.controller.Controller;
import com.doddi.gomodserver.controller.ReleaseInfo;
import com.doddi.gomodserver.controller.Tag;
import com.doddi.gomodserver.controller.github.entity.GithubContent;
import com.doddi.gomodserver.controller.github.entity.GithubReleaseInfo;
import com.doddi.gomodserver.controller.github.entity.GithubTag;

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
  public List<Tag> getTags(final URI url) {
    String[] split = url.getPath().split("/");
    String repository = split[2];
    String owner = split[1];

    List<GithubTag> response = service.getTags(repository, owner);

    return response.stream()
        .map(tag -> new Tag(tag.getName(), tag.getCommit().getSha()))
        .collect(Collectors.toList());
  }

  @Override
  public ReleaseInfo getRelease(final URI url, final String version) {
    String[] split = url.getPath().split("/");
    String repository = split[2];
    String owner = split[1];

    GithubReleaseInfo gitHubReleaseInfo = service.getReleaseInfo(repository, owner, version);

    ReleaseInfo releaseInfo = new ReleaseInfo();
    releaseInfo.setDate(gitHubReleaseInfo.getPublished_at());
    releaseInfo.setVersion(gitHubReleaseInfo.getTag_name());
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
  public ByteArrayInputStream getZip(final URI url, final String version) {
    String[] split = url.getPath().split("/");
    String repository = split[2];
    String owner = split[1];

    return service.getZip(repository, owner, version);
  }
}
