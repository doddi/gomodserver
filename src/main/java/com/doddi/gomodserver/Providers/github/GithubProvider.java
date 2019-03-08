package com.doddi.gomodserver.Providers.github;

import java.io.ByteArrayInputStream;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.core.UriBuilder;

import com.doddi.gomodserver.Providers.Provider;
import com.doddi.gomodserver.Providers.Tag;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

public class GithubProvider
    implements Provider
{
  private static final String ENDPOINT = "https://api.github.com";

  private static final String GITHUB_ACCEPT_HEADER = "application/vnd.github.v3+json";

  private final GithubServices service;

  private final ResteasyWebTarget target;

  private String authToken;

  public GithubProvider(final String authToken) {
    this.authToken = authToken;
    ResteasyClient client = new ResteasyClientBuilder().build();
    target = client.target(UriBuilder.fromPath(ENDPOINT));
    service = target.proxy(GithubServices.class);
  }

  public List<Tag> getTags(final String repository, final String owner) {
    List<GithubTag> response = service.getTags(repository, owner, GITHUB_ACCEPT_HEADER, authToken);

    return response.stream()
        .map(tag -> new Tag(tag.getName(), tag.getCommit().getSha()))
        .collect(Collectors.toList());
  }

  public String getContent(final String repository, final String owner, final String ref, final String filename) {
    GithubContent content = service.getContent(repository, owner, filename, ref, GITHUB_ACCEPT_HEADER, authToken);

    String encoded = content.getContent().replaceAll("\n", "");
    Decoder decoder = Base64.getDecoder();
    return new String(decoder.decode(encoded));
  }

  @Override
  public ByteArrayInputStream getZip(final String repository, final String owner, final String version) {
    return service.getZip(repository, owner, version, GITHUB_ACCEPT_HEADER, authToken);
  }
}
